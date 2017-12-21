package at.am.friedman.gui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.dialog.OptionsDialog;
import at.am.friedman.shared.DataProviderFactory;

public class OpenOptionsHandler {
	@Execute
	public void execute(Shell shell) {
		OptionsDialog dialog = new OptionsDialog(shell, DataProviderFactory.createDataProvider().getCemeteryOptions());
		dialog.create();
		if (dialog.open() == Window.OK) {
			DataProviderFactory.createDataProvider().notifyOptionsChanged();
		}

	}
}