package at.am.friedman.gui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.dialog.GraveDetailsDialog;
import at.am.friedman.shared.GraveInterface;

public class GraveDetailsHandler {
	@Execute
	public static void execute(Shell parent, GraveInterface grave) {
		GraveDetailsDialog dialog = new GraveDetailsDialog(parent, SWT.NONE);
		dialog.create();
		dialog.setData(grave);
		dialog.open();

	}
}