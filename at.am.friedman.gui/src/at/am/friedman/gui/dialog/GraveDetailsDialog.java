package at.am.friedman.gui.dialog;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.customComposites.GraveDetailsComposite;
import at.am.friedman.shared.GraveInterface;

public class GraveDetailsDialog extends TitleAreaDialog {

	protected Object result;
	protected Shell shell;

	private final int style;
	private GraveDetailsComposite graveDetails;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public GraveDetailsDialog(Shell parent, int style) {
		super(parent);

		this.style = style;

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Grab Details");
		setMessage("hier werden die Details eines einzelnen Grabes angezeigt");
		Composite area = (Composite) super.createDialogArea(parent);
		area.setLayout(new GridLayout(1, true));
		graveDetails = new GraveDetailsComposite(area, style);

		return area;
	}

	public void setData(GraveInterface grave) {
		graveDetails.setInitalGrave(grave);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

}
