package at.am.friedman.gui.tableviewer.search;

import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.gui.export.ExcelExporter;
import at.am.friedman.gui.tableviewer.AbstractTableViewer;
import at.am.friedman.gui.tableviewer.filter.DefaultViewerFilter;

public class SearchComposite extends Composite {
	
	private final Composite parent;
	private final AbstractTableViewer viewer;
	private final DefaultViewerFilter filter;
	private final HashSet<String> hideFields;

	public SearchComposite(final Composite parent, int style, final AbstractTableViewer viewer,final  DefaultViewerFilter filter, HashSet<String> hideSearchFields) {
		super(parent, style);
		this.parent = parent;
		this.viewer = viewer;
		this.filter = filter;
		this.hideFields = hideSearchFields;
		GridData d = new GridData();
		d.widthHint = 500;
		this.setLayoutData(d);
		GridLayout gridLayout = new GridLayout(2, false);
		this.setLayout(gridLayout);
		
		createSearchFields(parent, viewer, filter, hideSearchFields);


	}
	
	

	@Override
	public void redraw() {
		for(Control c : this.getChildren()){
			c.dispose();
		}
		createSearchFields(parent, viewer, filter, hideFields);
		super.redraw();
	}



	private void createSearchFields(final Composite parent, final AbstractTableViewer viewer,
			final DefaultViewerFilter filter, HashSet<String> hideSearchFields) {
		
		Label search = new Label(this, SWT.NONE);
		search.setText("Suche : (ein/ausblenden)");
		
		GridData data = new GridData();
		data.horizontalSpan = 2;
		search.setLayoutData(data);
		for(int i = 0; i < viewer.getColumnNames().size(); i++){
			if(hideSearchFields.contains(viewer.getColumnNames().get(i))){
				continue;
			}
			final int idx = i;
			Label searchLabel = new Label(this, SWT.NONE);
			searchLabel.setText(viewer.getColumnNames().get(i));
			final Text searchText = new Text(this, SWT.BORDER | SWT.SEARCH );
			searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

			searchText.addKeyListener(new KeyAdapter() {

				@Override
				public void keyReleased(KeyEvent e) {
					filter.setSearchString(idx, searchText.getText());
					viewer.refresh();
				}

			});
		}
		
		
		Button export = new Button(this, SWT.PUSH);
		export.setText("Export");
		export.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(parent.getShell(), SWT.SAVE);
				fd.setFilterExtensions(new String[] { "*.xls" });
				fd.setText("Neue Datei erzeugen...");
				String filePath = fd.open();
				if (filePath != null) {
					ExcelExporter exporter = new ExcelExporter();
					exporter.setFilePath(filePath);
					exporter.write(viewer.getTableViewer().getTable());
				}

			}

		});
	}

}
