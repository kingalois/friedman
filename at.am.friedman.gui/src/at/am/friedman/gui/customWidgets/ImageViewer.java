package at.am.friedman.gui.customWidgets;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import at.am.friedman.gui.utils.ImageUtils;
import at.am.friedman.shared.DataProviderFactory;


public class ImageViewer extends Dialog {

	private String image;
	
	public ImageViewer(Shell parentShell) {
		super(parentShell);
	}
	
	public void setImagePath(String imagePath){
		this.image = imagePath;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite c = (Composite) super.createDialogArea(parent);
		c.setLayout(new GridLayout());
		
        Composite imageComposite = new Composite(c, SWT.BORDER);
        Image i = new Image(parent.getDisplay(), image);
        Image scaledImage = ImageUtils.scaleImage(i, DataProviderFactory.createDataProvider().getCemeteryOptions().getMaxImageWidthForDetail());
        imageComposite.setLayoutData(new GridData(scaledImage.getBounds().width, scaledImage.getBounds().height));
        imageComposite.setBackgroundImage(scaledImage);
        return c;
   
	}
	
	
	
	
	
	
}
