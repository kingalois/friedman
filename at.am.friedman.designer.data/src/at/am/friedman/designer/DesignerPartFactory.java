package at.am.friedman.designer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DesignerPartFactory {
	
	
	public static Composite createDesignerPart(Composite parent){
		return new Composite(parent, SWT.BORDER);
	}

}
