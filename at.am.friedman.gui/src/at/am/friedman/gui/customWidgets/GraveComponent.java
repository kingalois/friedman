package at.am.friedman.gui.customWidgets;

import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import at.am.friedman.data.enums.GraveType;
import at.am.friedman.data.enums.GraveZone;
import at.am.friedman.gui.utils.GravePosition;
import at.am.friedman.shared.GraveInterface;
import at.am.common.logging.LogFactory;

/**
 * 
 * @author Alois
 * 
 *         Graphical Component which draws one grave
 */
public class GraveComponent extends DefaultGraveComponent {
	
	private static final Logger log = LogFactory.makeLogger(GraveComponent.class.getName());
	

	
	public GraveComponent(Composite parent, int style, int width, int height, GraveInterface grave) {
		super(parent, style, grave);

		createGrave(style, width, height, grave);

	}

	private void createGrave(int style, int width, int height, GraveInterface grave) {

		// create Formlayout
		FormLayout layout = new FormLayout();
		layout.spacing = 0;

		layout.marginHeight = 0;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;

		setLayout(layout);

		GridData layoutData = new GridData(width, height);
		setLayoutData(layoutData);

		setGraveLayoutData();
		setMetaData();

	}
	
	public void setGraveLayoutData(){
		FormData data = new FormData();
		data.top = new FormAttachment(0);
		data.bottom = new FormAttachment(90);
		if (getGrave().getType().equals(GraveType.Urnengrab) && !getGrave().getRow().equals("27") && !getGrave().getRow().equals("27I")) {
			data.bottom = new FormAttachment(40);
		}
		
		
		switch (getGravePosition(getGrave())) {
		case GravePosition.LEFT:
			
			data.left = new FormAttachment(0);
			data.right = new FormAttachment(80);
			
			break;
		case GravePosition.CENTER:
			data.left = new FormAttachment(20);
			data.right = new FormAttachment(80);

			break;
		case GravePosition.RIGHT:
			data.left = new FormAttachment(20);
			data.right = new FormAttachment(100);
			
			break;
		default:
			data.left = new FormAttachment(0);
			data.right = new FormAttachment(100);
			break;
		}
		getComposite().setLayoutData(data);
	}

	@Override
	public int getGravePosition(GraveInterface grave) {
		int gravePosition = super.getGravePosition(grave);
		if(grave.getZone().equals(GraveZone.Rechts)){
			if(gravePosition == GravePosition.LEFT){
				return GravePosition.RIGHT;
			}
			if(gravePosition == GravePosition.RIGHT){
				return GravePosition.LEFT;
			}
		}
		return gravePosition;
	}
	
	

}
