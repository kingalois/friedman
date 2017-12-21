package at.am.friedman.gui.dialog;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.gui.utils.WidgetFactory;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveOwnerInterface;

public class CreateOrEditGraveOwnerDialog extends TitleAreaDialog {

	private GraveOwnerInterface owner;

	private Text txtFirstName;
	private Text lastNameText;
	private Text txtStreet;
	private Text txtHouseNr;
	private Text txtPostalCode;
	private Text txtTown;
	private Text txtTelephon;

	public CreateOrEditGraveOwnerDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		if (owner != null) {
			setTitle("Besitzer bearbeiten..");
			setMessage("Hier können die Daten des Besitzers ändern", IMessageProvider.INFORMATION);
		} else {
			setTitle("Neuen Besitzer anlegen..");
			setMessage("Hier können die Daten des Besitzers ändern", IMessageProvider.INFORMATION);

		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(layout);
		createFirstName(container);
		createLastName(container);
		createStreet(container);
		createHouseNr(container);
		createPostalCode(container);
		createTown(container);
		createTelephonenr(container);
		return area;
	}

	private void createTelephonenr(Composite container) {
		WidgetFactory.createLabel(container, "Telefon");
		txtTelephon = WidgetFactory.createText(container, SWT.BORDER, owner != null ? owner.getTelephon() : "");

	}

	private void createTown(Composite container) {
		WidgetFactory.createLabel(container, "Ort");
		txtTown = WidgetFactory.createText(container, SWT.BORDER, owner != null ? owner.getTown() : "");

	}

	private void createPostalCode(Composite container) {
		WidgetFactory.createLabel(container, "PLZ");
		txtPostalCode = WidgetFactory.createText(container, SWT.BORDER, owner != null ? owner.getPostalCode() : "");

	}

	private void createHouseNr(Composite container) {
		WidgetFactory.createLabel(container, "Hausnr.");
		txtHouseNr = WidgetFactory.createText(container, SWT.BORDER, owner != null ? owner.getHouseNr() : "");

	}

	private void createStreet(Composite container) {
		WidgetFactory.createLabel(container, "Straße");
		txtStreet = WidgetFactory.createText(container, SWT.BORDER, owner != null ? owner.getStreet() : "");

	}

	private void createFirstName(Composite container) {
		WidgetFactory.createLabel(container, "Vorname");
		txtFirstName = WidgetFactory.createText(container, SWT.BORDER, owner != null ? owner.getFirstName() : "");

	}

	private void createLastName(Composite container) {
		WidgetFactory.createLabel(container, "Nachname");
		lastNameText = WidgetFactory.createText(container, SWT.BORDER, owner != null ? owner.getSurname() : "");
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	private void saveInput() {
		if (owner == null) {
			owner = DataProviderFactory.createDataProvider().getNewGraveOwner();
		}
		owner.setFirstName(txtFirstName.getText());
		owner.setSurname(lastNameText.getText());
		owner.setStreet(txtStreet.getText());
		owner.setHouseNr(txtHouseNr.getText());
		owner.setPostalCode(txtPostalCode.getText());
		owner.setTown(txtTown.getText());
		owner.setTelephon(txtTelephon.getText());
	}

	public GraveOwnerInterface getGraveOwner() {
		if (this.owner != null) {
			return owner;
		}
		return null;
	}

	public void setGraveOwner(GraveOwnerInterface owner) {
		this.owner = owner;
	}

}
