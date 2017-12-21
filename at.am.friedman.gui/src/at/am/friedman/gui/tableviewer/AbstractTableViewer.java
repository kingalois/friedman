package at.am.friedman.gui.tableviewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

import at.am.common.logging.LogFactory;
import at.am.friedman.commons.utils.Constants;
import at.am.friedman.gui.tableviewer.comparator.AbstractTableViewerComparator;
import at.am.friedman.gui.tableviewer.filter.DefaultViewerFilter;
import at.am.friedman.gui.tableviewer.search.SearchComposite;
import at.am.friedman.gui.utils.WidgetFactory;
import at.am.friedman.shared.CemeteryDataProviderInterface;
import at.am.friedman.shared.DataProviderFactory;

public abstract class AbstractTableViewer extends Composite {

	private static final Logger log = LogFactory.makeLogger();
	private static final String TABLEPROPERTIESFOLDER = "tableproperties/";

	private final HashSet<String> hideColumns = new HashSet<String>();
	private final HashSet<String> hideSearchFields = new HashSet<>();

	protected final TableViewer viewer;

	protected final CemeteryDataProviderInterface provider;

	protected final DefaultViewerFilter filter;

	protected final AbstractTableViewerComparator comparator;
	private final Composite searchComp;

	public AbstractTableViewer(Composite parent, int style) {
		super(parent, style);
		loadTableProperties();
		GridLayout layout = new GridLayout(1, false);
		this.setLayout(layout);
		filter = getFilter();
		searchComp = createSearchComposite(this);
		comparator = getComparator();
		
		viewer = createTableViewer(this);
		provider = getDataProvider();
		
		
		configTableViewer(parent);

		viewer.addFilter(filter);
		viewer.setComparator(comparator);
		ColumnViewerToolTipSupport.enableFor(viewer);
		setUserdefinedLayout();
	}

	@Override
	public boolean setFocus() {
		viewer.setInput(getViewerInput());
		return true;
	}

	public void setUserdefinedLayout() {
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		setLayoutData(gridData);
	}

	public DefaultViewerFilter getFilter() {
		return new DefaultViewerFilter() {

			@Override
			public boolean matches(Map<Integer, String> searchStringMapping, Object element) {
				boolean found = true;
				for(Entry<Integer, String> entry : searchStringMapping.entrySet()){
					String text = getTextForColumn(entry.getKey(), element);
					found = found && text.toLowerCase().contains(entry.getValue().toLowerCase());
				}
				return found;
			}
		};
	}

	public AbstractTableViewerComparator getComparator() {
		return new AbstractTableViewerComparator() {

			@Override
			public int compareObjects(Object e1, Object e2, int column) {
				return compareColumn(column, e1, e2);
			}
		};
	}

	protected int compareColumn(int column, Object e1, Object e2) {
		return getTextForColumn(column, e1).compareTo(getTextForColumn(column, e2));
	}

	private void configTableViewer(Composite parent) {
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(getViewerInput());

		createColumns(parent);

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 1;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		Menu popupMenu = createPopUpMenu(parent, table);
		addEnableHideColumn(popupMenu, table);
		table.setMenu(popupMenu);
	}

	private void addEnableHideColumn(Menu popupMenu, Table table) {
		new MenuItem(popupMenu, SWT.SEPARATOR);
		MenuItem rootEnableHideMenuItem = new MenuItem(popupMenu, SWT.CASCADE);
		rootEnableHideMenuItem.setText("Spalten ein/ausblenden");
		Menu menu = new Menu(rootEnableHideMenuItem);
		for (final TableColumn col : table.getColumns()) {
			final MenuItem item = new MenuItem(menu, SWT.CHECK);
			item.setText(col.getText());
			item.setSelection(!hideColumns.contains(col.getText()));
			item.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (item.getSelection()) {
						col.setWidth(getColumnWidths().get(getColumnNames().indexOf(col.getText())));
						col.setResizable(true);
						hideColumns.remove(col.getText());
						saveTableProperties();
					} else {
						col.setWidth(0);
						col.setResizable(false);
						hideColumns.add(col.getText());
						saveTableProperties();
					}
				}

			});
		}
		rootEnableHideMenuItem.setMenu(menu);

	}
	
	private void addEnableHideSearchFieldPopupMenu(Composite comp, final  Composite parent) {
		Menu popupMenu = new Menu(comp);
		MenuItem rootEnableHideMenuItem = new MenuItem(popupMenu, SWT.CASCADE);
		rootEnableHideMenuItem.setText("Suchfeld ein/ausblenden");
		Menu menu = new Menu(rootEnableHideMenuItem);
		for (final String col : getColumnNames()) {
			final MenuItem item = new MenuItem(menu, SWT.CHECK);
			item.setText(col);
			item.setSelection(!hideSearchFields.contains(col));
			item.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (item.getSelection()) {
						hideSearchFields.remove(col);
						saveTableProperties();
					} else {
						hideSearchFields.add(col);
						saveTableProperties();
					}
					searchComp.redraw();
					parent.layout(true);
				}

			});
		}
		rootEnableHideMenuItem.setMenu(menu);
		comp.setMenu(popupMenu);

	}

	private void loadTableProperties() {
		Reader reader = null;
		IMemento memento = null;
		try {
			reader = new FileReader(TABLEPROPERTIESFOLDER + getId());
			memento = XMLMemento.createReadRoot(reader);
			IMemento child = memento.getChild("hiddencolumns");
			if (child != null) {
				for (IMemento hidenColumnMemento : child.getChildren("column")) {
					hideColumns.add(hidenColumnMemento.getTextData());
				}
			}
			child = memento.getChild("hiddensearchfields");
			if (memento != null) {
				for (IMemento hidenFieldMemento : child.getChildren("field")) {
					hideSearchFields.add(hidenFieldMemento.getTextData());
				}
			}
		} catch (FileNotFoundException | WorkbenchException e) {
			log.log(Level.WARNING, "cannot load table properties for " + getId() + Constants.SPACE + e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.log(Level.SEVERE, "cannot close reader", e);
				}
			}
		}

	}

	private void saveTableProperties() {
		FileWriter fw = null;
		File rootFile = new File(TABLEPROPERTIESFOLDER);
		if (!rootFile.exists()) {
			rootFile.mkdir();
		}
		try {
			fw = new FileWriter(TABLEPROPERTIESFOLDER + getId());
		} catch (IOException e) {
			log.log(Level.SEVERE, "cannot write table properties file", e);
			return;
		}
		XMLMemento memento = XMLMemento.createWriteRoot("tableproperties");
		IMemento hiddenColumnsMemento = memento.createChild("hiddencolumns");
		for (String column : hideColumns) {
			hiddenColumnsMemento.createChild("column").putTextData(column);
		}
		IMemento hiddenSearchFields = memento.createChild("hiddensearchfields");
		for(String field : hideSearchFields){
			hiddenSearchFields.createChild("field").putTextData(field);
		}
		try {
			memento.save(fw);
		} catch (IOException e) {
			log.log(Level.SEVERE, "cannot save table properties", e);
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				log.log(Level.SEVERE, "cannot close writer", e);
			}
		}

	}

	public abstract Object getViewerInput();

	private void createColumns(Composite parent) {
		if (withNrColumn()) {
			TableViewerColumn c = WidgetFactory.createTableViewerColumn(viewer, "Nr", 50);
			c.setLabelProvider(new ColumnLabelProvider() {

				@Override
				public void update(ViewerCell cell) {
					int nr = viewer.getTable().indexOf((TableItem) cell.getItem()) + 1;
					cell.setText(nr + "");

				}

			});
		}
		List<String> columnNames = getColumnNames();
		List<Integer> columnWidths = getColumnWidths();
		for (int i = 0; i < columnNames.size(); i++) {
			final int columnIndex = i;
			int size = getDefaultColumnWidth();
			if (i < columnWidths.size()) {
				size = columnWidths.get(i);
			}
			if (hideColumns.contains(columnNames.get(i))) {
				size = 0;
			}

			TableViewerColumn col = WidgetFactory.createTableViewerColumn(viewer, columnNames.get(i), size);
			col.setLabelProvider(new ColumnLabelProvider() {

				@Override
				public String getText(Object element) {
					return getTextForColumn(columnIndex, element);
				}

				@Override
				public Image getImage(Object element) {
					return getImageForColumn(columnIndex, element);
				}

				@Override
				public Image getToolTipImage(Object element) {
					return getImageToolTip(columnIndex, element);
				}

			});
			if (getDefaultSortIndex() == columnIndex) {
				setSortColumn(col.getColumn(), columnIndex);
			}
			col.getColumn().addSelectionListener(getColumnSelectionListener(col.getColumn(), columnIndex));
		}
	}

	private SelectionListener getColumnSelectionListener(final TableColumn column, final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setSortColumn(column, index);
			}

		};
		return selectionAdapter;
	}

	private void setSortColumn(TableColumn column, int index) {
		comparator.setColumn(index);
		int dir = comparator.getDirection();
		viewer.getTable().setSortColumn(column);
		viewer.getTable().setSortDirection(dir);
		viewer.refresh();
	}

	public int getDefaultColumnWidth() {
		return 100;
	}

	protected CemeteryDataProviderInterface getDataProvider() {
		return DataProviderFactory.createDataProvider();
	}

	protected TableViewer createTableViewer(Composite parent) {
		return new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
	}

	public TableViewer getTableViewer(){
		return viewer;
	}
	
	public abstract List<String> getColumnNames();

	public Composite createSearchComposite(final Composite parent) {
		SearchComposite comp = new SearchComposite(parent, SWT.BORDER, this, filter, hideSearchFields);
		addEnableHideSearchFieldPopupMenu(comp, parent);
		return comp;
	}

	public void refresh() {
		viewer.setInput(getViewerInput());
		viewer.refresh();
	}

	public Image getImageToolTip(int columnIndex, Object element) {
		return null;
	}

	public int getDefaultSortIndex() {
		return -1;
	}

	public boolean withNrColumn() {
		return true;
	}

	public abstract List<Integer> getColumnWidths();

	public abstract String getTextForColumn(int columnIndex, Object element);

	public abstract Image getImageForColumn(int columnIndex, Object element);

	public abstract Menu createPopUpMenu(final Composite parent, final Table table);

	public abstract String getId();

}
