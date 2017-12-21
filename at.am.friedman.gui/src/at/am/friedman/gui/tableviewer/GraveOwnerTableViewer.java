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

import at.am.friedman.gui.handlers.AddGraveOwnerHandler;
import at.am.friedman.gui.handlers.EditGraveOwnerHandler;
import at.am.friedman.shared.GraveOwnerInterface;
import at.am.common.logging.LogFactory;

public class GraveOwnerTableViewer extends AbstractTableViewer {

	private static final Logger log = LogFactory.makeLogger();

	public GraveOwnerTableViewer(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public Object getViewerInput() {
		return provider.getAllOwner();
	}

	@Override
	public List<String> getColumnNames() {
		List<String> names = new ArrayList<String>();
		names.add("Vorname");
		names.add("Nachname");
		names.add("Adresse");
		names.add("Plz");
		names.add("Ort");
		names.add("Telefon");
		return names;
	}

	@Override
	public List<Integer> getColumnWidths() {
		List<Integer> widths = new ArrayList<Integer>();
		widths.add(100);
		widths.add(100);
		widths.add(150);
		widths.add(50);
		widths.add(100);
		widths.add(150);
		return widths;
	}

	@Override
	public String getTextForColumn(int columnIndex, Object element) {
		GraveOwnerInterface owner = (GraveOwnerInterface) element;
		switch (columnIndex) {
		case 0:
			return owner.getFirstName();
		case 1:
			return owner.getSurname();
		case 2:
			return owner.getStreet() + " " + owner.getHouseNr();
		case 3:
			return owner.getPostalCode();
		case 4:
			return owner.getTown();
		case 5:
			return owner.getTelephon();

		default:
			log.severe("no text fond for column indecx: " + columnIndex);
			return null;
		}

	}

	@Override
	public Image getImageForColumn(int columnIndex, Object element) {

		return null;
	}

	@Override
	public Menu createPopUpMenu(final Composite parent, final Table table) {
		Menu popupMenu = new Menu(table);
		MenuItem menuItem = new MenuItem(popupMenu, SWT.CASCADE);
		menuItem.setText("Grabbesitzer bearbeiten...");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				EditGraveOwnerHandler.execute(parent.getShell(), (GraveOwnerInterface) table.getSelection()[0].getData());
				viewer.refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		menuItem = new MenuItem(popupMenu, SWT.CASCADE);
		menuItem.setText("Grabbesitzer hinzufügen...");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				AddGraveOwnerHandler.execute(parent.getShell());
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
		return "graveownertable";
	}

}
