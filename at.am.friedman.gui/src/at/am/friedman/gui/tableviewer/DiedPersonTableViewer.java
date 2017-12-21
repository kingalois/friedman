package at.am.friedman.gui.tableviewer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import at.am.friedman.commons.utils.Constants;
import at.am.friedman.data.enums.InternalPosition;
import at.am.friedman.gui.handlers.AddDiedPersonHandler;
import at.am.friedman.gui.handlers.DeleteDiedPersonHandler;
import at.am.friedman.gui.handlers.EditDiedPersonHandler;
import at.am.friedman.gui.utils.ImageUtils;
import at.am.friedman.gui.utils.WidgetFactory;
import at.am.friedman.shared.DiedPersonInterface;
import at.am.friedman.shared.GraveInterface;

public class DiedPersonTableViewer extends AbstractTableViewer {

	private static final Logger log = LogFactory.makeLogger();

	public DiedPersonTableViewer(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public List<String> getColumnNames() {
		List<String> names = new ArrayList<String>();
		names.add("Vorname");
		names.add("Nachname");
		names.add("Sterbedatum");
		names.add("im Alter");
		names.add("Begräbnistag");
		names.add("Grab");
		names.add("Art");
		names.add("Beschreibung");
		names.add("Foto");
		names.add("Adresse");
		names.add("Plz");
		names.add("Ort");
		return names;
	}

	@Override
	public List<Integer> getColumnWidths() {
		List<Integer> widths = new ArrayList<Integer>();
		widths.add(80);
		widths.add(100);
		widths.add(100);
		widths.add(60);
		widths.add(90);
		widths.add(250);
		widths.add(80);
		widths.add(200);
		widths.add(50);
		widths.add(150);
		widths.add(50);
		widths.add(100);
		return widths;
	}

	@Override
	public String getTextForColumn(int columnIndex, Object element) {
		DiedPersonInterface diedPerson = (DiedPersonInterface) element;
		switch (columnIndex) {
		case 0:
			return diedPerson.getFirstName();
		case 1:
			return diedPerson.getSurname();
		case 2:
			return WidgetFactory.formatLongTimestamp(diedPerson.getDeathday());
		case 3:
			return Integer.toString(diedPerson.getAge());
		case 4:
			return WidgetFactory.formatLongTimestamp(diedPerson.getDayOfInterment());
		case 5:
			return provider.getGraveByDiedPerson(diedPerson).toString();
		case 6:
			return InternalPosition.values()[diedPerson.getInternalPosition()].toString();
		case 7:
			return diedPerson.getDescription();
		case 8:
			return null;
		case 9:
			return diedPerson.getStreet() + Constants.SPACE + diedPerson.getHouseNr();
		case 10:
			return diedPerson.getPostalCode();
		case 11:
			return diedPerson.getTown();
		default:
			log.severe("no text fond for column indecx: " + columnIndex);
			return null;

		}
	}

	@Override
	public Image getImageForColumn(int columnIndex, Object element) {
		DiedPersonInterface diedPerson = (DiedPersonInterface) element;
		if (columnIndex == 8) {
			try {
				if (diedPerson.havePictures()) {
					URL url = new URL(Constants.IMAGEICONPATH);
					Image image = new Image(this.getDisplay(), url.openStream());
					return image;
				}
			} catch (MalformedURLException e) {
				log.severe(e.getMessage());
			} catch (IOException e) {
				log.severe(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public Image getImageToolTip(int columnIndex, Object element) {
		DiedPersonInterface diedPerson = (DiedPersonInterface) element;
		if (columnIndex == 8) {
			if (diedPerson.havePictures()) {
				Image image = new Image(this.getDisplay(), diedPerson.getPictures().iterator().next());
				return ImageUtils.scaleImage(image, provider.getCemeteryOptions().getMaxImageWidthForTooltip());
			}
		}
		return null;
	}

	@Override
	protected int compareColumn(int column, Object e1, Object e2) {
		if (column == 2) {
			DiedPersonInterface p1 = (DiedPersonInterface) e1;
			DiedPersonInterface p2 = (DiedPersonInterface) e2;
			return new Long(p1.getDeathday()).compareTo(p2.getDeathday());
		}
		if (column == 3) {
			DiedPersonInterface p1 = (DiedPersonInterface) e1;
			DiedPersonInterface p2 = (DiedPersonInterface) e2;
			return new Integer(p1.getAge()).compareTo(p2.getAge());
		}
		if (column == 4) {
			DiedPersonInterface p1 = (DiedPersonInterface) e1;
			DiedPersonInterface p2 = (DiedPersonInterface) e2;
			return new Long(p1.getDayOfInterment()).compareTo(p2.getDayOfInterment());
		}
		return super.compareColumn(column, e1, e2);
	}

	@Override
	public Object getViewerInput() {
		return provider.getAllDiedPersons();
	}

	@Override
	public Menu createPopUpMenu(final Composite parent, final Table table) {

		Menu popupMenu = new Menu(table);
		MenuItem menuItem = new MenuItem(popupMenu, SWT.CASCADE);
		menuItem.setText("Verstorbenen bearbeiten...");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				DiedPersonInterface diedPerson = (DiedPersonInterface) table.getSelection()[0].getData();
				EditDiedPersonHandler.execute(parent.getShell(), diedPerson);
				viewer.refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		menuItem = new MenuItem(popupMenu, SWT.CASCADE);
		menuItem.setText("Verstorbenen hinzufügen...");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				AddDiedPersonHandler.execute(parent.getShell(), getActualGrave());
				refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		menuItem = new MenuItem(popupMenu, SWT.CASCADE);
		menuItem.setText("Verstorbenen löschen");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				DiedPersonInterface diedPerson = (DiedPersonInterface) table.getSelection()[0].getData();
				DeleteDiedPersonHandler.execute(parent.getShell(), diedPerson);
				viewer.refresh();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		return popupMenu;

	}

	protected GraveInterface getActualGrave() {
		return null;
	}

	
	@Override
	public String getId() {
		return "diedpersonstable";
	}

	@Override
	public int getDefaultSortIndex() {
		return 4;
	}

}
