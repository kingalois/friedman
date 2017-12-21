package at.am.friedman.update;


import org.eclipse.ui.IMemento;

import at.am.friedman.update.shared.UpdateEntryInterface;



public class UpdateEntry implements UpdateEntryInterface {
	
	private String title;
	private String description;
	private boolean confirmed;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateEntry other = (UpdateEntry) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}
	
	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}
	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#isConfirmed()
	 */
	@Override
	public boolean isConfirmed() {
		return confirmed;
	}
	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#setConfirmed(boolean)
	 */
	@Override
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#loadState(org.eclipse.ui.IMemento)
	 */
	@Override
	public void loadState(IMemento memento){
		this.title = memento.getChild("title").getTextData();
		this.description = memento.getChild("description").getTextData();
		this.confirmed = memento.getChild("confirmed").getBoolean("value");
	}
	
	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#saveState(org.eclipse.ui.IMemento)
	 */
	@Override
	public void saveState(IMemento memento){
		memento.createChild("title").putTextData(this.title);
		memento.createChild("description").putTextData(this.description);
		memento.createChild("confirmed").putBoolean("value", this.confirmed);
	}
	

}
