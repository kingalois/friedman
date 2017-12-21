package at.am.friedman.gui.toolcontrol;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import at.am.common.logging.LogFactory;
import at.am.friedman.commons.utils.BrokerEvents;

public class StatusToolControl {

	@Inject
	IEventBroker broker;

	private Label label;

	private final static Logger log = LogFactory.makeLogger(StatusToolControl.class.getName());

	@PostConstruct
	public void createGui(Composite parent) {
		label = new Label(parent, SWT.NONE);
		log.info("JUHU");
	}

	@Inject
	@Optional
	public void getEvent(@UIEventTopic(BrokerEvents.UPDATE_PROGRESS) String text) {
		label.setText(text);
		log.info(text);
	}

}