package at.am.friedman.gui.customComposites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.commons.utils.StringUtils;
import at.am.friedman.data.enums.InternalPosition;
import at.am.friedman.shared.DiedPersonInterface;

public class DiedPersonComposite extends AbstractCemeteryComposite {
	private Text textVorname;
	private Text textNachname;
	private Text textTodestag;
	private Text textAlter;
	private Text textBegraebnistag;
	private Text textLage;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DiedPersonComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void createContent() {
		setLayout(new GridLayout(2, false));

		Label lblVerstorbener = new Label(this, SWT.NONE);
		lblVerstorbener.setText("Verstorbener:");
		new Label(this, SWT.NONE);

		Label lblLage = new Label(this, SWT.NONE);
		lblLage.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLage.setText("Lage:");

		textLage = new Text(this, SWT.BORDER);
		textLage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblVorname = new Label(this, SWT.NONE);
		lblVorname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVorname.setText("Vorname:");

		textVorname = new Text(this, SWT.BORDER);
		textVorname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNachname = new Label(this, SWT.NONE);
		lblNachname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNachname.setText("Nachname:");

		textNachname = new Text(this, SWT.BORDER);
		textNachname.setText("");
		textNachname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblTodestag = new Label(this, SWT.NONE);
		lblTodestag.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTodestag.setText("Todestag:");

		textTodestag = new Text(this, SWT.BORDER);
		textTodestag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblInAlter = new Label(this, SWT.NONE);
		lblInAlter.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInAlter.setText("in Alter:");

		textAlter = new Text(this, SWT.BORDER);
		textAlter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblBegraebnistag = new Label(this, SWT.NONE);
		lblBegraebnistag.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBegraebnistag.setText("Begr\u00E4bnistag:");

		textBegraebnistag = new Text(this, SWT.BORDER);
		textBegraebnistag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}

	@Override
	public void dataChanged() {
		DiedPersonInterface diedPerson = (DiedPersonInterface) getData();
		textLage.setText(InternalPosition.values()[diedPerson.getInternalPosition()].toString());
		textVorname.setText(diedPerson.getFirstName());
		textNachname.setText(diedPerson.getSurname());
		textTodestag.setText(StringUtils.stringifyDateLong(diedPerson.getDeathday()));
		textAlter.setText(Integer.toString(diedPerson.getAge()));
		textBegraebnistag.setText(StringUtils.stringifyDateLong(diedPerson.getDayOfInterment()));

	}
}
