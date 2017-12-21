package at.am.friedman.gui.provider;

import org.eclipse.jface.viewers.LabelProvider;

public class GraveOverviewTreeLabelProvider extends LabelProvider {

	@Override
	public String getText ( Object element ) {
		return element.toString();
	}

}
