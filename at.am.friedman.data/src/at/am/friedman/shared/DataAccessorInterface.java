package at.am.friedman.shared;

import java.util.List;

public interface DataAccessorInterface {

	void saveGraveOwner ( GraveOwnerInterface owner );

	void saveGrave ( GraveInterface grave );

	void saveDiedPerson ( DiedPersonInterface diedPerson );

	void saveMultiGrave ( MultiGraveInterface grave );

	void deleteDiedPerson ( DiedPersonInterface diedPerson );

	void deleteGrave ( GraveInterface grave );

	void deleteGraveOwner ( GraveOwnerInterface owner );

	void deleteMultiGrave ( MultiGraveInterface mulitGrave );

	void initDataAccessor ( );

	List<GraveInterface> getAllGraves ( );

	List<GraveOwnerInterface> getAllOwners ( );

	List<DiedPersonInterface> getAllDiedPersons ( );

	List<MultiGraveInterface> getAllMultiGraves ( );

	int getMaxIdForDiedPerson ( );

	int getMaxIdForGrave ( );

	int getMaxIdForOwner ( );

	int getMaxIdForMultiGrave ( );

	void dump ( );

}
