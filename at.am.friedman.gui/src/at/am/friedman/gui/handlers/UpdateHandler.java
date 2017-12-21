package at.am.friedman.gui.handlers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.operations.ProvisioningJob;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.Update;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.commons.utils.BrokerEvents;
import at.am.common.logging.LogFactory;

public class UpdateHandler {
	private static final String REPOSITORY_LOC = System.getProperty("UpdateHandler.Repo", "http://www.muehleder.org/updatesites/friedman/target/repository");

	private static final Logger logger = LogFactory.makeLogger(UpdateHandler.class.getName());

	@Inject
	IEventBroker broker;

	@Execute
	public void execute(IEventBroker broker, final IProvisioningAgent agent, final Shell shell, final UISynchronize sync, final IWorkbench workbench) {
		this.broker = broker;
		Job j = new Job("Update Job") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				return checkForUpdates(agent, shell, sync, workbench, monitor);
			}
		};

		j.schedule();
	}

	private void showMessage(final Shell parent, final UISynchronize sync, final String t) {
		sync.syncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openWarning(parent, "No update", t);
			}
		});
	}

	private IStatus checkForUpdates(final IProvisioningAgent agent, final Shell shell, final UISynchronize sync, final IWorkbench workbench, IProgressMonitor monitor) {
		logger.info("checkUpdates for URL: " + REPOSITORY_LOC);
		broker.send(BrokerEvents.UPDATE_PROGRESS, "pr�fe ob neue Updates vorhanden sind");
		// configure update operation
		final ProvisioningSession session = new ProvisioningSession(agent);
		final UpdateOperation operation = new UpdateOperation(session);
		configureUpdate(operation, logger);
		// check for updates, this causes I/O
		final IStatus status = operation.resolveModal(monitor);

		logger.info("Status: " + status.getMessage() + " " + status.getCode());
		// failed to find updates (inform user and exit)
		if (status.getCode() == UpdateOperation.STATUS_NOTHING_TO_UPDATE) {
			// showMessage(shell, sync);
			broker.send(BrokerEvents.UPDATE_PROGRESS, "keine neuen Updates vorhanden");
			return Status.CANCEL_STATUS;
		}

		for (Update u : operation.getPossibleUpdates()) {
			logger.info("Update: " + u.toString());
		}

		// run installation
		final ProvisioningJob provisioningJob = operation.getProvisioningJob(monitor);
		operation.getPossibleUpdates();

		// updates cannot run from within Eclipse IDE!!!
		if (provisioningJob == null) {
			showMessage(shell, sync, "Trying to update from the Eclipse IDE? This won't work!");
			return Status.CANCEL_STATUS;
		}
		broker.send(BrokerEvents.UPDATE_PROGRESS, "Verbindung mit Update Seite wird hergestellt....");
		configureProvisioningJob(provisioningJob, shell, sync, workbench);
		logger.info("schedule");
		provisioningJob.schedule();
		broker.send(BrokerEvents.UPDATE_PROGRESS, "Updates wurden heruntegeladen");
		return Status.OK_STATUS;

	}

	private void configureProvisioningJob(final ProvisioningJob provisioningJob, final Shell shell, final UISynchronize sync, final IWorkbench workbench) {

		// register a job change listener to track
		// installation progress and notify user upon success
		provisioningJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				logger.log(Level.SEVERE, "done with error " + provisioningJob.getResult().toString());

				logger.info("done " + event.getResult().getMessage());
				if (event.getResult().isOK()) {
					sync.syncExec(new Runnable() {

						@Override
						public void run() {
							boolean restart = MessageDialog.openQuestion(shell, "Updates installed, restart?", "Updates have been installe. Do you want to restart?");
							if (restart) {
								workbench.restart();
							}
						}
					});

				}
				super.done(event);
			}
		});

	}

	private void showMessage(final Shell parent, final UISynchronize sync) {
		sync.syncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openWarning(parent, "Update", "Kein Update vorhanden.");
			}
		});
	}

	private UpdateOperation configureUpdate(final UpdateOperation operation, Logger logger) {
		// create uri and check for validity
		URI uri = null;
		try {
			uri = new URI(REPOSITORY_LOC);
		} catch (final URISyntaxException e) {
			logger.severe(e.getMessage());
			return null;
		}
		logger.info("configureUpdate");
		// set location of artifact and metadata repo
		operation.getProvisioningContext().setArtifactRepositories(new URI[] { uri });
		operation.getProvisioningContext().setMetadataRepositories(new URI[] { uri });
		return operation;
	}

}