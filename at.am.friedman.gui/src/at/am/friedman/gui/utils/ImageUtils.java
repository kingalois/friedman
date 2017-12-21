package at.am.friedman.gui.utils;

import org.eclipse.swt.graphics.Image;

public class ImageUtils {
	
	public static Image scaleImage(Image origin, int maxWidth){
		if(origin.getBounds().width > maxWidth){
			float ratio = (float) origin.getBounds().width / (float) maxWidth;
			int newWidth = (int) (origin.getBounds().width / ratio);
			int newHeigh = (int) (origin.getBounds().height / ratio);
			return new Image(origin.getDevice(),  origin.getImageData().scaledTo(newWidth, newHeigh));
		}
		return origin;
	}

}
