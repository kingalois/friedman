package at.am.friedman.gui.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.dialog.CreateOrEditGraveDialog;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveInterface;

public class EditGraveHandler {
	@Execute
	public static void execute(Shell shell) {
		CreateOrEditGraveDialog dialog = new CreateOrEditGraveDialog(shell);
		dialog.setGrave(DataProviderFactory.createDataProvider().getActualGrave());
		dialog.create();

		if (dialog.open() == Window.OK) {

			DataProviderFactory.createDataProvider().addOrUpdateGrave(dialog.getGrave());
		}
	}

	public static void execute(Shell shell, GraveInterface grave) {
		CreateOrEditGraveDialog dialog = new CreateOrEditGraveDialog(shell);
		dialog.setGrave(grave);
		dialog.create();

		if (dialog.open() == Window.OK) {

			DataProviderFactory.createDataProvider().addOrUpdateGrave(dialog.getGrave());
		}
	}

	@CanExecute
	public boolean canExecute() {
		return DataProviderFactory.createDataProvider().getActualGrave() != null;
	}

}