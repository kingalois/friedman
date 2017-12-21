package at.am.friedman.gui.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.dialog.DoubleGraveDialog;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.GraveInterface;
import at.am.friedman.shared.GraveOwnerInterface;
import at.am.friedman.shared.MultiGraveInterface;

public class CreateEditDoubleGraveHandler {
	@Execute
	public static void execute(Shell shell, GraveInterface grave) {
		List<GraveInterface> graves = new ArrayList<GraveInterface>();
		graves.add(grave);

		if (DataProviderFactory.createDataProvider().isGraveInMulitGrave(grave)) {
			// multigrave Handling
			graves.clear();
			MultiGraveInterface mGrave = DataProviderFactory.createDataProvider().getMultiGraveForGrave(grave);
			for (Integer graveId : mGrave.getGraveIds()) {
				graves.add(DataProviderFactory.createDataProvider().getGraveById(graveId));
			}
		}

		DoubleGraveDialog dialog = new DoubleGraveDialog(shell, graves);
		if (dialog.open() == Window.OK) {
			graves = dialog.getGraves();
			MultiGraveInterface multiGrave = DataProviderFactory.createDataProvider().getMultiGraveForGraves(graves);
			if (multiGrave == null) {
				multiGrave = DataProviderFactory.createDataProvider().getNewMultiGrave();
			}
			multiGrave.clearGraves();
			// Sort the Graves
			Collections.sort(graves, new Comparator<GraveInterface>() {

				@Override
				public int compare(GraveInterface grave1, GraveInterface grave2) {
					if (Integer.parseInt(grave1.getPlace()) < Integer.parseInt(grave2.getPlace())) {
						return -1;
					} else if (Integer.parseInt(grave1.getPlace()) > Integer.parseInt(grave2.getPlace())) {
						return 1;
					}
					return 0;
				}

			});

			GraveOwnerInterface owner = null;
			for (GraveInterface graveToAdd : graves) {
				if(owner == null){
					owner = DataProviderFactory.createDataProvider().getOwnerFromGrave(graveToAdd);
				}
				
				multiGrave.addGraveId(graveToAdd.getId());
			}
			if(owner != null){
				for (GraveInterface graveToAdd : graves) {
					graveToAdd.setOwnerId(owner.getId());
				}
			}
			DataProviderFactory.createDataProvider().addOrUpdateMultiGrave(multiGrave);
			shell.layout();
		}
	}
}