package at.am.friedman.gui.tableviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;

import at.am.common.logging.LogFactory;
import at.am.friedman.gui.handlers.AddDiedPersonHandler;
import at.am.friedman.gui.handlers.EditGraveHandler;
import at.am.friedman.gui.handlers.GraveDetailsHandler;
import at.am.friedman.shared.DiedPersonInterface;
import at.am.friedman.shared.GraveInterface;
import at.am.friedman.shared.GraveOwnerInterface;

public class GraveTableViewer extends AbstractTableViewer {

	private static final Logger log = LogFactory.makeLogger();

	public GraveTableViewer(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public Object getViewerInput() {
		return provider.getAllGraves();
	}

	@Override
	public boolean withNrColumn() {
		return false;
	}

	@Override
	public List<String> getColumnNames() {
		List<String> names = new ArrayList<String>();
		names.add("Zone");
		names.add("Reihe");
		names.add("Platz");
		names.add("Besitzer");
		names.add("letzter Verstorbener");
		return names;
	}

	@Override
	public List<Integer> getColumnWidths() {
		List<Integer> widths = new ArrayList<Integer>();
		widths.add(200);
		widths.add(60);
		widths.add(60);
		widths.add(200);
		widths.add(350);
		return widths;
	}

	@Override
	public String getTextForColumn(int columnIndex, Object element) {
		GraveInterface grave = (GraveInterface) element;
		switch (columnIndex) {
		case 0:
			return grave.getZone().toString();
		case 1:
			return grave.getRow();
		case 2:
			return grave.getPlace();
		case 3:
			GraveOwnerInterface owner = provider.getOwnerFromGrave(grave);
			if (owner != null) {
				return owner.getCellText();
			}
			return "kein Besitzer";
		case 4:
			List<DiedPersonInterface> diedPersons = provider.getDiedPersonsForGrave(grave);
			if (!diedPersons.isEmpty()) {
				return diedPersons.get(0).getCellText();
			}

			return "kein Verstorbener";
		default:
			log.severe("no text fond for column indecx: " + columnIndex);
			return null;
		}
	}

	@Override
	protected int compareColumn(int column, Object e1, Object e2) {
		if (column == 1) {
			GraveInterface grave1 = (GraveInterface) e1;
			GraveInterface grave2 = (GraveInterface) e2;
			return new Integer(grave1.getRow()).compareTo(new Integer(grave2.getRow()));
		}
		if (column == 2) {
			GraveInterface grave1 = (GraveInterface) e1;
			GraveInterface grave2 = (GraveInterface) e2;
			return new Integer(grave1.getPlace()).compareTo(new Integer(grave2.getPlace()));
		}
		return super.compareColumn(column, e1, e2);
	}

	@Override
	public Image getImageForColumn(int columnIndex, Object element) {

		return null;
	}

	@Override
	public Menu createPopUpMenu(final Composite parent, final Table table) {
		Menu popupMenu = new Menu(table);
		MenuItem menuItem = new MenuItem(popupMenu, SWT.CASCADE);
		menuItem.setText("zeige Details...");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				GraveInterface selectedGrave = (GraveInterface) table.getSelection()[0].getData();
				GraveDetailsHandler.execute(parent.getShell(), selectedGrave);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		menuItem = new MenuItem(popupMenu, SWT.CASCADE);
		menuItem.setText("Grabdaten bearbeiten...");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				GraveInterface selectedGrave = (GraveInterface) table.getSelection()[0].getData();
				EditGraveHandler.execute(parent.getShell(), selectedGrave);
				viewer.refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		menuItem = new MenuItem(popupMenu, SWT.CASCADE);
		menuItem.setText("Verstorbenen hinzufügen....");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				GraveInterface selectedGrave = (GraveInterface) table.getSelection()[0].getData();
				AddDiedPersonHandler.execute(parent.getShell(), selectedGrave);
				viewer.refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		return popupMenu;
	}


	@Override
	public String getId() {
		return "gravestable";
	}

}
