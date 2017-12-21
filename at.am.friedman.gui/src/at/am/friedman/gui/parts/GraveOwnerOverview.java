package at.am.friedman.gui.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import at.am.friedman.gui.tableviewer.GraveOwnerTableViewer;

public class GraveOwnerOverview {

	private GraveOwnerTableViewer viewer;

	@PostConstruct
	public void postConstruct(Composite parent) {
		viewer = new GraveOwnerTableViewer(parent, SWT.NONE);
	}

	@Focus
	public void onFocus() {
		viewer.setFocus();
	}

}