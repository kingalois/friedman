package at.am.friedman.gui.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import at.am.friedman.gui.tableviewer.GraveTableViewer;

public class GraveOverview {

	private GraveTableViewer viewer;

	@PostConstruct
	public void createComposite(Composite parent) {
		viewer = new GraveTableViewer(parent, SWT.NONE);
	}

	@Focus
	public void setFocus() {
		viewer.setFocus();
	}

}