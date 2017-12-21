package at.am.friedman.gui.handlers;

import java.util.logging.Logger;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.dialog.CreateOrEditGraveDialog;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveInterface;
import at.am.common.logging.LogFactory;

public class AddGraveHandler {

	private static final Logger	log	= LogFactory.makeLogger();

	@Execute
	public static void execute ( Shell shell ) {
		CreateOrEditGraveDialog dialog = new CreateOrEditGraveDialog(shell);
		dialog.create();
		if ( dialog.open() == Window.OK ) {
			log.info("New Grave: " + dialog.getGrave().toString());
			DataProviderFactory.createDataProvider().addOrUpdateGrave(dialog.getGrave());
		}
	}

	public static void execute ( Shell shell, GraveInterface grave ) {
		CreateOrEditGraveDialog dialog = new CreateOrEditGraveDialog(shell);
		dialog.setGrave(grave);
		dialog.create();
		if ( dialog.open() == Window.OK ) {
			log.info("New Grave: " + dialog.getGrave().toString());
			DataProviderFactory.createDataProvider().addOrUpdateGrave(dialog.getGrave());
		}
	}
}