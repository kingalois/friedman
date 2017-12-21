package at.am.friedman.gui.customComposites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.data.enums.GraveZone;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveInterface;

public class GraveChooserComposite extends Composite {

	private final Label lblZone;
	private final Combo comboZone;
	private final Label lblRow;
	private final Text txtRow;
	private final Spinner spinnerPlace;

	
	public GraveChooserComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));

		lblZone = new Label(this, SWT.NONE);
		lblZone.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblZone.setText("Zone");

		comboZone = new Combo(this, SWT.NONE);
		comboZone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblRow = new Label(this, SWT.NONE);
		lblRow.setText("Reihe");

		txtRow = new Text(this, SWT.READ_ONLY);

		Label lblPlace = new Label(this, SWT.NONE);
		lblPlace.setText("Platz");

		spinnerPlace = new Spinner(this, SWT.BORDER);

	}

	public void setGraveData(GraveInterface grave) {
		for (GraveZone zone : GraveZone.values()) {
			comboZone.add(zone.name());
		}
		comboZone.select(grave.getZone().ordinal());
		txtRow.setText(grave.getRow());
		spinnerPlace.setSelection(Integer.parseInt(grave.getPlace()));
	}

	public GraveInterface getGraveData() {
		return DataProviderFactory.createDataProvider().getGrave(GraveZone.values()[comboZone.getSelectionIndex()], txtRow.getText(), spinnerPlace.getText());
	}

	public void addModifyListener(ModifyListener listener) {
		spinnerPlace.addModifyListener(listener);
		
	}

	@Override
	public void addKeyListener(KeyListener listener) {
		spinnerPlace.addKeyListener(listener);
		
	}

	public void addSelectionListener(SelectionListener listener) {
		comboZone.addSelectionListener(listener);
	}

}
