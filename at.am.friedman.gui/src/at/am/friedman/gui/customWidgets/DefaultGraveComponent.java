package at.am.friedman.gui.customWidgets;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.data.enums.GraveType;
import at.am.friedman.data.enums.MultiGravePosition;
import at.am.friedman.gui.handlers.AddDiedPersonHandler;
import at.am.friedman.gui.handlers.AddGraveHandler;
import at.am.friedman.gui.handlers.CreateEditDoubleGraveHandler;
import at.am.friedman.gui.handlers.DeleteDiedPersonHandler;
import at.am.friedman.gui.handlers.DeleteMultiGraveHandler;
import at.am.friedman.gui.handlers.EditDiedPersonHandler;
import at.am.friedman.gui.handlers.EditGraveOwnerHandler;
import at.am.friedman.gui.handlers.GraveDetailsHandler;
import at.am.friedman.gui.utils.GravePosition;
import at.am.friedman.shared.CemeteryDataProviderInterface;
import at.am.friedman.shared.DataChangeListener;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.DiedPersonInterface;
import at.am.friedman.shared.GraveInterface;
import at.am.friedman.shared.GraveOwnerInterface;
import at.am.friedman.shared.MultiGraveInterface;
import at.am.common.logging.LogFactory;

public abstract class DefaultGraveComponent extends Composite implements DataChangeListener {

	private final CemeteryDataProviderInterface dataProvider;

	private static final Logger logger = LogFactory.makeLogger(DefaultGraveComponent.class.getName());

	private final Composite composite;
	private final GraveInterface grave;

	private Label label;

	public DefaultGraveComponent(Composite parent, int style, GraveInterface grave) {
		super(parent, style);
		composite = new Composite(this, SWT.BORDER);
		dataProvider = DataProviderFactory.createDataProvider();
		dataProvider.addDataChangeListener(this);
		this.grave = grave;
	}

	@Override
	public void onDataChange(Collection<GraveInterface> graves) {
		if (graves.contains(grave)) {
			setGraveLayoutData();
			setMetaData();
		}

	}
	
	public Label getGraveLabel(){
		return label;
	}

	public Composite getComposite() {
		return this.composite;
	}

	public CemeteryDataProviderInterface getDataProvider() {
		return dataProvider;
	}

	public GraveInterface getGrave() {
		return this.grave;
	}

	protected void setToolTiptext(Control composite, GraveInterface grave) {
		if (!grave.getType().equals(GraveType.Leerplatz)) {
			GraveOwnerInterface owner = dataProvider.getOwnerFromGrave(grave);
			List<DiedPersonInterface> diedPersons = dataProvider.getDiedPersonsForGrave(grave);
			StringBuilder builder = new StringBuilder();
			builder.append("Verstorbene:").append(Constants.NEWLINE);
			for (DiedPersonInterface diedPerson : diedPersons) {
				builder.append(diedPerson.getDetailText());
				builder.append("\n");
			}

			composite.setToolTipText(owner != null ? grave.getDetailText() + "\n" + owner.getDetailText() + "\n" + builder.toString() : grave.getDetailText() + builder.toString());
			// this.setToolTipText(owner != null ? grave.getDetailText() + "\n"
			// + owner.getDetailText() + "\n" + builder.toString() :
			// grave.getDetailText() + builder.toString());
		}
	}

	public void setMetaData() {
		setGraveColor(composite, grave);
		createPopUpMenue(composite, grave);
		setToolTiptext(composite, grave);
		setGraveText(composite, grave);
		layout();
	}

	protected void setGraveColor(Control composite, GraveInterface grave) {
		Display display = Display.getCurrent();
		Color background;
		if (grave == null || grave.getOwnerId() == 0) {
			background = display.getSystemColor(SWT.COLOR_GREEN);
		} else if (dataProvider.getDiedPersonsForGrave(grave).size() == 0) {
			background = display.getSystemColor(SWT.COLOR_DARK_MAGENTA);
		} else {
			background = display.getSystemColor(SWT.COLOR_RED);
		}
		composite.setBackground(background);

	}

	protected void setGraveText(Composite composite, GraveInterface grave) {

		composite.setLayout(new FillLayout());
		if (label == null) {
			label = new Label(composite, SWT.CENTER);
		}
		label.setFont(new Font(composite.getDisplay(), "Arial", 6, SWT.BOLD));
		label.setForeground(composite.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		String text = (grave.getOffset() > 0) ? grave.getPlace() + Constants.NEWLINE + grave.getOffset() : grave.getPlace();
		label.setText(text);
		label.setBackground(composite.getBackground());
		setToolTiptext(label, grave);
		createPopUpMenue(label, grave);
	}

	protected void createPopUpMenue(Control composite, final GraveInterface grave) {
		if (grave.getType().equals(GraveType.Leerplatz)) {
			composite.setVisible(false);
			createPopUpMenuForEmptyPlace(this, grave);
		} else {
			composite.setVisible(true);
			createPopUpMenuForGrave(composite, grave);
		}

	}

	private void createPopUpMenuForEmptyPlace(Composite graveComponent, final GraveInterface grave) {
		Menu popupMenu = new Menu(graveComponent);
		MenuItem menuItem = new MenuItem(popupMenu, SWT.CASCADE);
		menuItem.setText("Grab anlegen/bearbeiten");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				AddGraveHandler.execute(getParent().getShell(), grave);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing

			}
		});
		graveComponent.setMenu(popupMenu);

	}

	private void createPopUpMenuForGrave(Control composite, final GraveInterface grave) {
		Menu popupMenu = new Menu(composite);
		createDetailMenuItem(grave, popupMenu);
		createSeparatorForMenu(popupMenu);
		createEditGraveMenuItem(grave, popupMenu);
		createEditGraveOwnerItem(grave, popupMenu);
		createSeparatorForMenu(popupMenu);
		createAddDiedPersonMenuItem(grave, popupMenu);
		createEditDiedPerson(grave, popupMenu);
		createDeleteDiedPerson(grave, popupMenu);
		createSeparatorForMenu(popupMenu);
		createCreateMultiGraveMenuItem(grave, popupMenu);
		createDeleteMultiGraveMenuItem(grave, popupMenu);

		composite.setMenu(popupMenu);

	}

	private MenuItem createEditGraveOwnerItem(final GraveInterface grave, Menu parent) {
		MenuItem menuItem = new MenuItem(parent, SWT.CASCADE);
		menuItem.setText("Besitzer bearbeiten...");
		menuItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				EditGraveOwnerHandler.execute(getParent().getShell(), getDataProvider().getOwnerFromGrave(grave));
				setMetaData();
			}

		});

		menuItem.setEnabled(getDataProvider().getOwnerFromGrave(grave) != null);

		return menuItem;

	}

	private MenuItem createEditDiedPerson(GraveInterface grave, Menu parent) {
		MenuItem menuItem = new MenuItem(parent, SWT.CASCADE);
		menuItem.setText("Verstorbenen bearbeiten...");
		Menu subMenu = null;
		for (final DiedPersonInterface diedPerson : dataProvider.getDiedPersonsForGrave(grave)) {
			if (subMenu == null) {
				subMenu = new Menu(parent);
			}
			MenuItem childMenu = new MenuItem(subMenu, SWT.CASCADE);
			childMenu.setText(diedPerson.getFullName());
			childMenu.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					EditDiedPersonHandler.execute(getParent().getShell(), diedPerson);
					setMetaData();
				}
			});
		}
		menuItem.setMenu(subMenu);
		menuItem.setEnabled(subMenu != null);
		return menuItem;
	}

	private MenuItem createDeleteDiedPerson(GraveInterface grave, Menu parent) {
		MenuItem menuItem = new MenuItem(parent, SWT.CASCADE);
		menuItem.setText("Verstorbenen löschen...");
		Menu subMenu = null;
		for (final DiedPersonInterface diedPerson : dataProvider.getDiedPersonsForGrave(grave)) {
			if (subMenu == null) {
				subMenu = new Menu(parent);
			}
			MenuItem childMenu = new MenuItem(subMenu, SWT.CASCADE);
			childMenu.setText(diedPerson.getFullName());
			childMenu.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					DeleteDiedPersonHandler.execute(getParent().getShell(), diedPerson);
					setMetaData();
				}
			});
		}
		menuItem.setMenu(subMenu);
		menuItem.setEnabled(subMenu != null);
		return menuItem;
	}

	private MenuItem createAddDiedPersonMenuItem(final GraveInterface grave, Menu parent) {
		MenuItem itemAddDiedPerson = new MenuItem(parent, SWT.CASCADE);
		itemAddDiedPerson.setText("füge Verstorbenen hinzu...");
		itemAddDiedPerson.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				AddDiedPersonHandler.execute(getParent().getShell(), grave);
				setMetaData();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing

			}
		});
		itemAddDiedPerson.setEnabled(grave != null);
		return itemAddDiedPerson;
	}

	private MenuItem createDetailMenuItem(final GraveInterface grave, Menu parent) {
		MenuItem menuItem = new MenuItem(parent, SWT.CASCADE);
		menuItem.setText("zeige Details...");
		menuItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				GraveDetailsHandler.execute(getParent().getShell(), grave);
				setMetaData();
			}

		});
		return menuItem;
	}

	private MenuItem createSeparatorForMenu(Menu parent) {
		return new MenuItem(parent, SWT.SEPARATOR);
	}

	private MenuItem createEditGraveMenuItem(final GraveInterface grave, Menu parent) {
		MenuItem menuItem = new MenuItem(parent, SWT.CASCADE);
		menuItem.setText("Grab bearbeiten(Besitzer hinzufügen/freigeben)");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				AddGraveHandler.execute(getParent().getShell(), grave);
				setMetaData();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing

			}
		});
		return menuItem;
	}

	private MenuItem createCreateMultiGraveMenuItem(final GraveInterface grave, Menu parent) {
		MenuItem menuItem = new MenuItem(parent, SWT.CASCADE);
		menuItem.setText("Mehrfachgrab anlegen/bearbeiten");
		menuItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				CreateEditDoubleGraveHandler.execute(getParent().getShell(), grave);
				setGraveLayoutData();
				setMetaData();
			}

		});
		return menuItem;
	}

	private MenuItem createDeleteMultiGraveMenuItem(final GraveInterface grave, Menu parent) {
		MenuItem menuItem = new MenuItem(parent, SWT.CASCADE);
		menuItem.setText("Mehrfachgrab auflösen");
		menuItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				DeleteMultiGraveHandler.execute(getParent().getShell(), grave);
				setGraveLayoutData();
				setMetaData();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		return menuItem;
	}

	public int getGravePosition(GraveInterface grave) {
		int gravePosition = GravePosition.CENTER;
		MultiGraveInterface mGrave = getDataProvider().getMultiGraveForGrave(grave);
		if (mGrave != null) {
			MultiGravePosition pos = mGrave.getGravePosition(grave.getId());

			if (pos == MultiGravePosition.LEFT) {
				gravePosition = GravePosition.LEFT;
			} else if (pos == MultiGravePosition.RIGHT) {
				gravePosition = GravePosition.RIGHT;
			} else if (pos == MultiGravePosition.CENTER) {
				gravePosition = GravePosition.FILL;
			}

		}
		return gravePosition;
	}

	public abstract void setGraveLayoutData();

}
