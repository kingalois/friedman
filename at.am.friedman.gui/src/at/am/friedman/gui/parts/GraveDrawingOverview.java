package at.am.friedman.gui.parts;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.customWidgets.GraphicalMainOverviewComposite;
import at.am.friedman.gui.dialog.UpdateEntryDialog;
import at.am.friedman.gui.handlers.UpdateHandler;
import at.am.friedman.shared.CemeteryDataProviderInterface;
import at.am.friedman.shared.DataChangeListener;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.update.shared.UpdateEntryManagerFactory;
import at.am.friedman.update.shared.UpdateEntryManagerInterface;
import at.am.common.logging.LogFactory;

public class GraveDrawingOverview  {

	private final Logger log = LogFactory.makeLogger();
	
	@Inject
	IEventBroker broker;

	private CemeteryDataProviderInterface provider;
	private GraphicalMainOverviewComposite overview;
	Composite parent;

	@Inject
	public GraveDrawingOverview() {
		// TODO Your code here
	}

	@PostConstruct
	public void postConstruct(Composite parent, final IProvisioningAgent agent,  final UISynchronize sync, final IWorkbench workbench) {
		checkForNonConformedUpdates(parent.getShell(), sync);
		new UpdateHandler().execute(broker, agent, parent.getShell(), sync, workbench);
		
		this.parent = parent;
		
		provider = DataProviderFactory.createDataProvider();
		parent.setLayout(new FillLayout());
		overview = new GraphicalMainOverviewComposite(parent, SWT.NONE);
		
		
	}

	private void checkForNonConformedUpdates(final Shell shell, final UISynchronize sync) {
		UpdateEntryManagerInterface updateManager = UpdateEntryManagerFactory.getUpdateEntryManagerSingleton();
		if(updateManager.haveNotConfirmedUpdate()){
			sync.asyncExec(new Runnable() {
				
				@Override
				public void run() {
					UpdateEntryDialog dialog = new UpdateEntryDialog(shell);
					dialog.create();
					dialog.setTitle("Updates wurden installiert");
					dialog.setMessage("Hallo Hannes es wurden wieder Updates installiert, \n Informationen dazu siehst du in der folgenden Tabelle." );
					dialog.open();
				}
			});
			
		}
	}
	

}