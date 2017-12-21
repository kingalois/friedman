package at.am.friedman.gui.customWidgets;

import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import at.am.friedman.data.enums.GraveZone;
import at.am.friedman.data.enums.MultiGravePosition;
import at.am.friedman.gui.utils.GravePosition;
import at.am.friedman.gui.utils.GraveRotation;
import at.am.friedman.shared.GraveInterface;
import at.am.friedman.shared.MultiGraveInterface;
import at.am.common.logging.LogFactory;

public class WallGraveComposite extends DefaultGraveComponent {

	private static final Logger log = LogFactory.makeLogger();
	
	private final int rotation;
	

	public WallGraveComposite(Composite parent, int style, GraveInterface grave,  int rotation, int width, int height) {
		super(parent, style, grave);
		this.rotation = rotation;
		createContent(rotation, width, height);

	}

	private void createContent( int rotation, int width, int height) {
		setLayout(new FormLayout());
		GridData data = new GridData();
		switch (rotation) {
		case GraveRotation.leftWallGraveRotation:
			
			
			data.widthHint = height;
			data.heightHint = width;
			break;
		case GraveRotation.rightWallGraveRotation:
			
			data.widthHint = height;
			data.heightHint = width;
			break;
		case GraveRotation.normalGraveRotation:
			
			
			data.widthHint = width;
			data.heightHint = height;
			break;

		default:
			break;
		}
		setLayoutData(data);
		
		setGraveLayoutData();
		setMetaData();
		

	}

	private void createTopWallGrave(int position, FormData graveAlignment) {
		switch (position) {
		case GravePosition.CENTER:
			graveAlignment.top = new FormAttachment(0);
			graveAlignment.left = new FormAttachment(25);
			graveAlignment.right = new FormAttachment(75);
			graveAlignment.bottom = new FormAttachment(100);
			break;
		case GravePosition.LEFT:
			graveAlignment.top = new FormAttachment(0);
			graveAlignment.left = new FormAttachment(0);
			graveAlignment.right = new FormAttachment(50);
			graveAlignment.bottom = new FormAttachment(100);
			break;
		case GravePosition.RIGHT:
			graveAlignment.top = new FormAttachment(0);
			graveAlignment.left = new FormAttachment(50);
			graveAlignment.right = new FormAttachment(100);
			graveAlignment.bottom = new FormAttachment(100);
			break;
		case GravePosition.FILL:
			graveAlignment.top = new FormAttachment(0);
			graveAlignment.left = new FormAttachment(0);
			graveAlignment.right = new FormAttachment(100);
			graveAlignment.bottom = new FormAttachment(100);
			break;

		default:
			log.severe("unknown grave position for wall grave");
			break;
		}

	}

	private void createWallGrave(int position, FormData graveAlignment) {
		switch (position) {
		case GravePosition.CENTER:
			graveAlignment.top = new FormAttachment(25);
			graveAlignment.left = new FormAttachment(0);
			graveAlignment.right = new FormAttachment(100);
			graveAlignment.bottom = new FormAttachment(75);
			break;
		case GravePosition.LEFT:
			graveAlignment.top = new FormAttachment(50);
			graveAlignment.left = new FormAttachment(0);
			graveAlignment.right = new FormAttachment(100);
			graveAlignment.bottom = new FormAttachment(100);
			break;
		case GravePosition.RIGHT:
			graveAlignment.top = new FormAttachment(0);
			graveAlignment.left = new FormAttachment(0);
			graveAlignment.right = new FormAttachment(100);
			graveAlignment.bottom = new FormAttachment(50);
			break;
		case GravePosition.FILL:
			graveAlignment.top = new FormAttachment(0);
			graveAlignment.left = new FormAttachment(0);
			graveAlignment.right = new FormAttachment(100);
			graveAlignment.bottom = new FormAttachment(100);
			break;

		default:
			log.severe("unknown grave position for wall grave");
			break;
		}
	}

	@Override
	public void setGraveLayoutData() {

		FormData graveAlignment = new FormData();
		int gravePosition = getGravePosition(getGrave());
		if (rotation == GraveRotation.normalGraveRotation) {
			if (getGrave().getZone().equals(GraveZone.Wandgrab_Rechts)) {
				gravePosition = invertGravePosition(gravePosition);

			}
			createTopWallGrave(gravePosition, graveAlignment);
		} else {
			createWallGrave(gravePosition, graveAlignment);
		}
		getComposite().setLayoutData(graveAlignment);

	}

	@Override
	public int getGravePosition(GraveInterface grave) {
		int gravePosition = super.getGravePosition(grave);
		if(grave.getZone().equals(GraveZone.Wandgrab_Rechts) || grave.getZone().equals(GraveZone.Wandgrab_Links)){
			return invertGravePosition(gravePosition);
		}
		
		return gravePosition;
	}
	
	private int invertGravePosition(int gravePosition){
		if(gravePosition == GravePosition.LEFT){
			return GravePosition.RIGHT;
		}
		if(gravePosition == GravePosition.RIGHT){
			return GravePosition.LEFT;
		}
		return gravePosition;
	}
}
