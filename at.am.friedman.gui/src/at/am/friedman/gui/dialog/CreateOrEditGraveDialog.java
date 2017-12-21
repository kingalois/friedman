package at.am.friedman.gui.dialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.data.enums.GraveType;
import at.am.friedman.data.enums.GraveZone;
import at.am.friedman.gui.handlers.AddGraveOwnerHandler;
import at.am.friedman.gui.utils.WidgetFactory;
import at.am.friedman.shared.ComboLabelProvider;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveInterface;
import at.am.friedman.shared.GraveOwnerInterface;
import at.am.friedman.shared.MultiGraveInterface;

public class CreateOrEditGraveDialog extends TitleAreaDialog {

	GraveInterface grave;

	Text txtRow;
	Text txtPlace;
	Combo comboZone;
	Combo comboTyp;
	ComboViewer comboOwner;
	Label lbPlace;

	Text txtOffset;

	DateTime startTime;
	Combo comboRuntime;

	Composite container;

	List<Integer> possibleRuntimes = new ArrayList<Integer>();

	public CreateOrEditGraveDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		if (grave != null) {
			setTitle("Grab bearbeiten..");
			setMessage("Hier können die Daten des Grabes ändern", IMessageProvider.INFORMATION);
		} else {
			setTitle("Neues Grab anlegen..");
			setMessage("Hier können die Daten des Grabes ändern", IMessageProvider.INFORMATION);

		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(layout);
		createTyp(container);
		createRow(container);
		createPlace(container);
		createZone(container);
		createOwner(container);
		createGraveOffset(container);
		createGraveStartTime(container);
		createGraveRuntime(container);
		return area;
	}

	private void createGraveRuntime(Composite parent) {
		WidgetFactory.createLabel(parent, "Laufzeit");

		possibleRuntimes.add(5);
		possibleRuntimes.add(10);
		comboRuntime = WidgetFactory.createCombo(parent, SWT.READ_ONLY, possibleRuntimes);

		if (grave != null) {
			comboRuntime.select(possibleRuntimes.indexOf(grave.getRuntime()));
		}

	}

	private void createGraveStartTime(Composite parent) {
		WidgetFactory.createLabel(parent, "Startzeit:");
		Calendar cal = Calendar.getInstance();
		if (grave != null) {
			cal.setTime(new Date(grave.getStarttime()));
		}
		startTime = WidgetFactory.createDateTime(parent, SWT.DATE | SWT.DROP_DOWN, cal);

	}

	private void createGraveOffset(Composite container) {
		WidgetFactory.createLabel(container, "Versatz (in cm)");
		txtOffset = WidgetFactory.createText(container, SWT.BORDER, "");

		GridData dataRow = new GridData();
		dataRow.grabExcessHorizontalSpace = true;
		dataRow.horizontalAlignment = GridData.FILL;

		if (grave != null) {
			txtOffset.setText(Integer.toString(grave.getOffset()));
		}

	}

	private void createRow(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
		lbtFirstName.setText("Reihe");

		GridData dataRow = new GridData();
		dataRow.grabExcessHorizontalSpace = true;
		dataRow.horizontalAlignment = GridData.FILL;

		txtRow = WidgetFactory.createText(container, SWT.BORDER, "", grave == null);

		if (grave != null) {
			txtRow.setText(grave.getRow());
		}
		txtRow.setLayoutData(dataRow);
	}

	private void createPlace(Composite container) {
		lbPlace = new Label(container, SWT.NONE);
		lbPlace.setText("Platz");

		GridData dataPlace = new GridData();
		dataPlace.grabExcessHorizontalSpace = true;
		dataPlace.horizontalAlignment = GridData.FILL;

		txtPlace = WidgetFactory.createText(container, SWT.BORDER, "", grave == null);

		if (grave != null) {
			txtPlace.setText(grave.getPlace());
		}
		txtPlace.setLayoutData(dataPlace);
	}

	private void createZone(Composite container) {
		Label lbZone = new Label(container, SWT.NONE);
		lbZone.setText("Zone");

		GridData dataZone = new GridData();
		dataZone.grabExcessHorizontalSpace = true;
		dataZone.horizontalAlignment = GridData.FILL;

		comboZone = WidgetFactory.createCombo(container, SWT.READ_ONLY, GraveZone.values(), grave == null);
		for (GraveZone zone : GraveZone.values()) {
			comboZone.add(zone.name());
		}

		if (grave != null) {
			comboZone.select(grave.getZone().ordinal());
		}
		comboZone.setLayoutData(dataZone);
	}

	private void createTyp(final Composite container) {
		Label lbType = new Label(container, SWT.NONE);
		lbType.setText("Typ");

		GridData dataTyp = new GridData();
		dataTyp.grabExcessHorizontalSpace = true;
		dataTyp.horizontalAlignment = GridData.FILL;

		comboTyp = new Combo(container, SWT.READ_ONLY);
		for (GraveType zone : GraveType.values()) {
			comboTyp.add(zone.name());
		}

		if (grave != null) {
			comboTyp.select(grave.getType().ordinal());
		}
		comboTyp.setLayoutData(dataTyp);

	}

	private void createOwner(final Composite container) {
		Label lbOwner = new Label(container, SWT.NONE);
		lbOwner.setText("Grabbesitzer");
		Composite comp = new Composite(container, SWT.NONE);
		comp.setLayout(new GridLayout(3, false));

		comboOwner = new ComboViewer(comp, SWT.READ_ONLY);
		comboOwner.setContentProvider(ArrayContentProvider.getInstance());
		comboOwner.setInput(DataProviderFactory.createDataProvider().getAllOwner());
		comboOwner.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof GraveOwnerInterface) {
					return ((ComboLabelProvider) element).getText();
				}
				return "no ComboLabelProvider";
			}
		});

		if (grave != null) {
			GraveOwnerInterface owner = DataProviderFactory.createDataProvider().getOwnerFromGrave(grave);
			if (owner != null) {
				comboOwner.setSelection(new StructuredSelection(owner));
			}
		}

		Button createOwner = new Button(comp, SWT.PUSH);
		createOwner.setText("neuen Besitzer erzeugen...");
		createOwner.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				AddGraveOwnerHandler.execute(container.getShell());
				comboOwner.refresh();
			}

		});

		Button removeOwner = new Button(comp, SWT.PUSH);
		removeOwner.setText("Benutzer entfernen(Grab freigeben)");
		removeOwner.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				comboOwner.setSelection(new StructuredSelection(), true);
			}

		});

	}

	public void setGrave(GraveInterface grave) {
		this.grave = grave;
	}

	public GraveInterface getGrave() {
		return grave;
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	private void saveInput() {
		if (grave == null) {
			grave = DataProviderFactory.createDataProvider().getNewGrave();
		}
		grave.setPlace(txtPlace.getText());
		grave.setRow(txtRow.getText());
		grave.setType(GraveType.values()[comboTyp.getSelectionIndex()]);
		grave.setZone(GraveZone.values()[comboZone.getSelectionIndex()]);
		IStructuredSelection selection = (IStructuredSelection) comboOwner.getSelection();
		int ownerId = 0;
		if (!selection.isEmpty()) {
			GraveOwnerInterface owner = (GraveOwnerInterface) selection.getFirstElement();
			grave.setOwnerId(owner.getId());
			ownerId = owner.getId();
		} else {
			grave.setOwnerId(0);
		}
		grave.setOffset(Integer.parseInt(txtOffset.getText()));
		Calendar cal = Calendar.getInstance();
		cal.set(startTime.getYear(), startTime.getMonth(), startTime.getDay());
		grave.setStarttime(cal.getTimeInMillis());
		grave.setRuntime(possibleRuntimes.get(comboRuntime.getSelectionIndex()));

		// Multigrave handling
		if (DataProviderFactory.createDataProvider().isGraveInMulitGrave(grave)) {
			MultiGraveInterface multiGrave = DataProviderFactory.createDataProvider().getMultiGraveForGrave(grave);
			for (int id : multiGrave.getGraveIds()) {
				DataProviderFactory.createDataProvider().getGraveById(id).setOwnerId(ownerId);
			}
			DataProviderFactory.createDataProvider().addOrUpdateMultiGrave(multiGrave);
		}

	}
}
