package at.am.friedman.gui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveInterface;
import at.am.friedman.shared.MultiGraveInterface;

public class DeleteMultiGraveHandler {
	@Execute
	public static void execute(Shell parent, GraveInterface grave) {
		MultiGraveInterface mGrave = DataProviderFactory.createDataProvider().getMultiGraveForGrave(grave);
		if (mGrave == null) {
			MessageDialog.openError(parent, "Fehler in der Software", "kein Doppelgrab gefunden");
			return;
		}
		if (MessageDialog.openConfirm(parent, "Doppelgrab löschen", "Doppelgrab wirklich löschen")) {
			DataProviderFactory.createDataProvider().deleteMultiGrave(mGrave);
		}

	}

}