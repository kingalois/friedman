package at.am.friedman.gui.provider;

import org.eclipse.jface.viewers.LabelProvider;

public class GraveOwnerOverviewTreeLabelProvider extends LabelProvider {

	@Override
	public String getText ( Object element ) {
		return element.toString();
	}

}
