package at.am.friedman.gui.customWidgets;

import java.util.logging.Logger;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import at.am.friedman.commons.utils.Constants;
import at.am.common.logging.LogFactory;

public class IntegerText {

	private static final Logger log = LogFactory.makeLogger();

	private final Text text;

	public IntegerText(Composite parent, int style) {
		text = new Text(parent, style);
		text.setText(Constants.DEFAULT_INT_TEXT_VALUE);
	}

	public int getTextAsInt() {
		String result = text.getText();
		try {
			return Integer.parseInt(result);
		} catch (Exception e) {
			log.severe("error during getTextAs String: " + e.getMessage());
		}

		return 0;
	}

	public void setInteger(int value) {
		text.setText(Integer.toString(value));
	}

}
