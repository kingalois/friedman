package at.am.friedman.update.shared;

import org.eclipse.ui.IMemento;

public interface UpdateEntryInterface {

	String getTitle();

	void setTitle(String title);

	String getDescription();

	void setDescription(String description);

	boolean isConfirmed();

	void setConfirmed(boolean confirmed);

	void loadState(IMemento memento);

	void saveState(IMemento memento);

}