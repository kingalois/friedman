package at.am.friedman.update.shared;

import java.util.Collection;
import java.util.List;

import at.am.friedman.update.UpdateEntry;

public interface UpdateEntryManagerInterface {

	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#haveNotConfirmedUpdate()
	 */
	boolean haveNotConfirmedUpdate();

	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#getAllNonConfirmedUpadteEntries()
	 */
	Collection<UpdateEntryInterface> getAllNonConfirmedUpdateEntries();

	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#getAllUpdateEntries()
	 */
	Collection<UpdateEntryInterface> getAllUpdateEntries();

	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#confirmAll()
	 */
	void confirmAll();

	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#loadState()
	 */
	void loadState();

	/* (non-Javadoc)
	 * @see at.am.friedman.update.UpdateEntryInterface#saveState()
	 */
	void saveState();

}