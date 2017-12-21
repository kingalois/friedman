package at.am.friedman.gui.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.gui.utils.WidgetFactory;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveInterface;
import at.am.common.logging.LogFactory;

public class DoubleGraveDialog extends TitleAreaDialog {

	private static final Logger log = LogFactory.makeLogger(DoubleGraveDialog.class.getName());

	private final List<GraveInterface> graves;
	private final List<Text> textFields = new ArrayList<Text>();

	Composite inputComposite;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @param graves
	 */
	public DoubleGraveDialog(Shell parentShell, List<GraveInterface> graves) {
		super(parentShell);
		this.graves = graves;

	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("hier kann dein Doppelgrab/Mehrfachgrab erzeugt bzw bearbeitet werden");
		setTitle("Doppelgrab");
		Composite area = (Composite) super.createDialogArea(parent);
		area.setLayout(new GridLayout(1, true));
		createNrOfGraves(area);
		inputComposite = new Composite(area, SWT.BORDER);
		inputComposite.setLayout(new GridLayout(1, true));

		refreshNumberOfGraves("2");
		return area;
	}

	private void createNrOfGraves(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new GridLayout(2, false));
		WidgetFactory.createLabel(c, "Anzahl Gräber");
		final Text nrOfGraves = WidgetFactory.createText(c, SWT.BORDER, "2", true);
		nrOfGraves.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				refreshNumberOfGraves(nrOfGraves.getText());

			}
		});
	}

	protected void refreshNumberOfGraves(String text) {

		if (!text.isEmpty()) {
			try {
				int nr = Integer.parseInt(text);
				int place = Integer.parseInt(graves.get(0).getPlace());
				for (Control c : inputComposite.getChildren()) {
					c.dispose();
				}
				textFields.clear();
				for (int i = 1; i <= nr; i++) {

					createComponentForGrave(inputComposite, i, place + i - 1);
				}
				inputComposite.getParent().layout(true);

			} catch (NumberFormatException e) {
				log.log(Level.SEVERE, "cannot refresh number of Graves", e);
			}
		}
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void createComponentForGrave(Composite parent, int graveNr, int gravePlace) {
		GraveInterface grave = graves.get(0);
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(6, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		GridData data = new GridData();
		data.horizontalSpan = 6;
		WidgetFactory.createLabel(container, graveNr + ".Grab:").setLayoutData(data);

		WidgetFactory.createLabel(container, "Zone:");
		Text textZone = new Text(container, SWT.BORDER);
		textZone.setEditable(false);
		textZone.setText(grave.getZone().name());

		WidgetFactory.createLabel(container, "Reihe:");
		Text textReihe = new Text(container, SWT.BORDER);
		textReihe.setEditable(false);
		textReihe.setText(grave.getRow());

		WidgetFactory.createLabel(container, "Platz:");
		Text textPlatz = new Text(container, SWT.BORDER);
		textPlatz.setEditable(true);
		textPlatz.setText(gravePlace + "");

		textFields.add(textPlatz);

		GridData horizontalSeparatorData = new GridData(GridData.FILL_HORIZONTAL);
		horizontalSeparatorData.horizontalSpan = 6;
		WidgetFactory.createHorizontalSeparator(container, horizontalSeparatorData);
	}

	@Override
	protected void okPressed() {
		GraveInterface grave = graves.get(0);

		graves.clear();

		for (Text t : textFields) {
			GraveInterface g = DataProviderFactory.createDataProvider().getGrave(grave.getZone(), grave.getRow(), t.getText());
			if (g != null) {
				graves.add(g);
			}
		}

		super.okPressed();
	}

	public List<GraveInterface> getGraves() {
		return graves;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 460);
	}

}
