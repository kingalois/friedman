package at.am.friedman.data;

import org.eclipse.ui.IMemento;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.shared.GraveOwnerInterface;

public class GraveOwnerImpl extends AbstractPerson implements GraveOwnerInterface {
	@Override
	public String getDetailText() {
		StringBuilder builder = new StringBuilder();
		builder.append("Grabbesitzer:");
		builder.append(Constants.NEWLINE);
		builder.append(super.getDetailText());
		builder.append(Constants.SEPARATOR);
		builder.append(Constants.NEWLINE);
		return builder.toString();
	}

	@Override
	public void saveState(IMemento memento) {
		super.saveState(memento.createChild("owner"));
	}

	@Override
	public String getCellText() {
		return getFullName();
	}

}
