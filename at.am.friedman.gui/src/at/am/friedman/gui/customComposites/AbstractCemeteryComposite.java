package at.am.friedman.gui.customComposites;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class AbstractCemeteryComposite extends Composite {
	
	private Object data;

	public AbstractCemeteryComposite(Composite parent, int style) {
		super(parent, style);
		createContent();
		
	}

	public abstract void createContent();
	
	public abstract void dataChanged();
	
	public void setData(Object data){
		this.data = data;
		dataChanged();
	}
	
	public Object getData(){
		return this.data;
	}
	
	public void setEnabledForChildren(boolean enabled){
		for(Control c : this.getChildren()){
			c.setEnabled(enabled);
		}
	}
	

}
