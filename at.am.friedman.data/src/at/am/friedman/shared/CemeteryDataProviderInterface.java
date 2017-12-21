package at.am.friedman.shared;

import java.util.ArrayList;
import java.util.List;

import at.am.friedman.data.enums.GraveZone;

public interface CemeteryDataProviderInterface {

	CemeteryOptionsInterface getCemeteryOptions();

	void notifyOptionsChanged();

	/**
	 * @return all Owner
	 */
	List<GraveOwnerInterface> getAllOwner();

	/**
	 * 
	 * @return all graves
	 */
	List<GraveInterface> getAllGraves();

	/**
	 * 
	 * @return all died persons
	 */
	List<DiedPersonInterface> getAllDiedPersons();

	/**
	 * 
	 * @param row
	 * @return all graves of the specified row
	 */
	List<GraveInterface> getAllGravesByRow(int row);

	/**
	 * 
	 * @param owner
	 * @return all graves for the specified owner
	 */
	ArrayList<GraveInterface> getGravesByOwner(GraveOwnerInterface owner);

	/**
	 * delete the grave
	 * 
	 * @param grave
	 */
	void deleteGrave(GraveInterface grave);

	/**
	 * delete the owner
	 * 
	 * @param owner
	 */
	void deleteOwner(GraveOwnerInterface owner);

	/**
	 * delete the died person
	 * 
	 * @param diedPerson
	 */
	void deleteDiedPerson(DiedPersonInterface diedPerson);

	/**
	 * create a new died person which have only a id and will be saved
	 * automatically
	 * 
	 * @return
	 */
	DiedPersonInterface getNewDiedPerson();

	/**
	 * create a new owner which have only a id and will be saved automatically
	 * 
	 * @return
	 */
	GraveOwnerInterface getNewGraveOwner();

	/**
	 * create a new grave which have only a id and will be saved automatically
	 * 
	 * @return
	 */
	GraveInterface getNewGrave();

	/**
	 * update or save the died person
	 * 
	 * @param diedperson
	 */
	void addOrUpdateDiedPerson(DiedPersonInterface person);

	/**
	 * update or save the grave
	 * 
	 * @param grave
	 */
	void addOrUpdateGrave(GraveInterface grave);

	/**
	 * update or save the owner
	 * 
	 * @param owner
	 */
	void addOrUpdateGravOwner(GraveOwnerInterface owner);

	/**
	 * (not implemented yet) dump the whole datastructure into the logfile
	 */
	void dump();

	GraveOwnerInterface getActualGraveOwner();

	void setActualGraveOwner(GraveOwnerInterface owner);

	GraveInterface getActualGrave();

	void setActualGrave(GraveInterface grave);

	DiedPersonInterface getActualDiedPerson();

	void setActualDiedPerson(DiedPersonInterface diedPerson);

	GraveInterface getGraveById(int graveId);

	/**
	 * 
	 * @param grave
	 * @return sorted list of died persons for the specified grave, last died
	 *         person is the first
	 */
	List<DiedPersonInterface> getDiedPersonsForGrave(GraveInterface grave);

	GraveOwnerInterface getOwnerFromGrave(GraveInterface grave);

	GraveInterface getGraveByDiedPerson(DiedPersonInterface diedPerson);

	void addOrUpdateMultiGrave(MultiGraveInterface grave);

	void deleteMultiGrave(MultiGraveInterface grave);

	MultiGraveInterface getNewMultiGrave();

	boolean isGraveInMulitGrave(GraveInterface grave);

	/**
	 * returns the MultiGrave if it is in one, otherwise return null
	 * 
	 * @param grave
	 * @return
	 */
	MultiGraveInterface getMultiGraveForGrave(GraveInterface grave);

	List<MultiGraveInterface> getAllMultiGraves();

	/**
	 * returns the grave identified by zone, row, and place
	 * 
	 * @param zone
	 * @param row
	 * @param parseInt
	 * @return
	 */
	GraveInterface getGrave(GraveZone zone, String row, String place);

	/**
	 * returns the first Multigrave which contains one of the graves
	 * 
	 * @param graves
	 * @return
	 */
	MultiGraveInterface getMultiGraveForGraves(List<GraveInterface> graves);

	void addDataChangeListener(DataChangeListener listener);

}
