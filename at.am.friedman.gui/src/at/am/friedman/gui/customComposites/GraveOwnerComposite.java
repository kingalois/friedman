package at.am.friedman.gui.customComposites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.shared.GraveOwnerInterface;

public class GraveOwnerComposite extends AbstractCemeteryComposite {
	private Text textVorname;
	private Text textNachname;
	private Text textStrasse;
	private Text textOrt;
	private Text textTelefon;
	private Text textPlz;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public GraveOwnerComposite(Composite parent, int style) {
		super(parent, style);
	}


	private void setNoOwner() {
		textVorname.setText("kein Besitzer vorhanden");
		textNachname.setText("");
		textStrasse.setText("");
		textOrt.setText("");
		textPlz.setText("");
		textTelefon.setText("");
	}

	@Override
	public void createContent() {
		setLayout(new GridLayout(2, false));

		Label lblBesitzer = new Label(this, SWT.NONE);
		lblBesitzer.setText("Besitzer:");
		new Label(this, SWT.NONE);

		Label lblVorname = new Label(this, SWT.NONE);
		lblVorname.setText("Vorname:");

		textVorname = new Text(this, SWT.BORDER);
		textVorname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNachname = new Label(this, SWT.NONE);
		lblNachname.setText("Nachname:");

		textNachname = new Text(this, SWT.BORDER);
		textNachname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblStrasse = new Label(this, SWT.NONE);
		lblStrasse.setText("Stra\u00DFe:");

		textStrasse = new Text(this, SWT.BORDER);
		textStrasse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblOrt = new Label(this, SWT.NONE);
		lblOrt.setText("Ort:");

		textOrt = new Text(this, SWT.BORDER);
		textOrt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblPlz = new Label(this, SWT.NONE);
		lblPlz.setText("Plz:");

		textPlz = new Text(this, SWT.BORDER);
		textPlz.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblTelefon = new Label(this, SWT.NONE);
		lblTelefon.setText("Telefon:");

		textTelefon = new Text(this, SWT.BORDER);
		textTelefon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
	}

	@Override
	public void dataChanged() {
		GraveOwnerInterface owner = (GraveOwnerInterface) getData();
		if (owner == null) {
			setNoOwner();
			layout();
			return;
		}
		textVorname.setText(owner.getFirstName());
		textNachname.setText(owner.getSurname());
		textStrasse.setText(owner.getStreet() + Constants.SPACE + owner.getHouseNr());
		textOrt.setText(owner.getTown());
		textPlz.setText(owner.getPostalCode());
		textTelefon.setText(owner.getTelephon());
		
	}

}
