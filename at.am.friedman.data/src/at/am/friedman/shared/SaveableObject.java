package at.am.friedman.shared;

import org.eclipse.ui.IMemento;

public interface SaveableObject {

	public void saveState(IMemento memento);

	public void restoreState(IMemento memento);

}
