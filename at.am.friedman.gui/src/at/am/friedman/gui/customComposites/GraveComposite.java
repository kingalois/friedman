package at.am.friedman.gui.customComposites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.commons.utils.StringUtils;
import at.am.friedman.shared.GraveInterface;

public class GraveComposite extends AbstractCemeteryComposite {
	private Text textZone;
	private Text textReihe;
	private Text textPlatz;
	private Text textGrabtyp;
	private Text textVersatz;
	private Text textBezahlt;
	private Text textLaufzeit;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public GraveComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));

	}

	@Override
	public void createContent() {
		Label lblBesitzer = new Label(this, SWT.NONE);
		lblBesitzer.setText("Grabdaten:");
		new Label(this, SWT.NONE);

		Label lblZone = new Label(this, SWT.NONE);
		lblZone.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblZone.setText("Zone");

		textZone = new Text(this, SWT.BORDER);
		textZone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblReihe = new Label(this, SWT.NONE);
		lblReihe.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblReihe.setText("Reihe");

		textReihe = new Text(this, SWT.BORDER);
		textReihe.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblPlatz = new Label(this, SWT.NONE);
		lblPlatz.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPlatz.setText("Platz");

		textPlatz = new Text(this, SWT.BORDER);
		textPlatz.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblGrabtyp = new Label(this, SWT.NONE);
		lblGrabtyp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblGrabtyp.setText("Grabtyp");

		textGrabtyp = new Text(this, SWT.BORDER);
		textGrabtyp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblVersatz = new Label(this, SWT.NONE);
		lblVersatz.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVersatz.setText("Versatz");

		textVersatz = new Text(this, SWT.BORDER);
		textVersatz.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblBezahlt = new Label(this, SWT.NONE);
		lblBezahlt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBezahlt.setText("bezahlt");

		textBezahlt = new Text(this, SWT.BORDER);
		textBezahlt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblLaufzeit = new Label(this, SWT.NONE);
		lblLaufzeit.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLaufzeit.setText("Laufzeit");

		textLaufzeit = new Text(this, SWT.BORDER);
		textLaufzeit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}

	@Override
	public void dataChanged() {
		GraveInterface grave = (GraveInterface) getData();
		if (grave != null) {
			textZone.setText(grave.getZone().name());
			textReihe.setText(grave.getRow());
			textPlatz.setText(grave.getPlace());
			textGrabtyp.setText(grave.getType().name());
			textVersatz.setText(Integer.toString(grave.getOffset()));
			textBezahlt.setText(grave.getStarttime() == 0 ? "" : StringUtils.stringifyDateLong(grave.getStarttime()));
			textLaufzeit.setText(grave.getStarttime() == 0 ? "" : Integer.toString(grave.getRuntime()));
		} else {
			clearAll();
		}
	}

	private void clearAll() {
		textZone.setText("");
		textReihe.setText("");
		textPlatz.setText("");
		textGrabtyp.setText("");
		textVersatz.setText("");
		textBezahlt.setText("");
		textLaufzeit.setText("");
	}

}
