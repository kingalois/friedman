package at.am.friedman.gui.customWidgets;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class CemeteryZoneComponent extends Composite {

	public CemeteryZoneComponent(Composite parent, int style, int numOfColumns, int verticalSpacing) {
		super(parent, style);
		GridLayout layout = new GridLayout(numOfColumns, true);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = verticalSpacing;
		layout.marginHeight = 0;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		this.setLayout(layout);
	}

}
