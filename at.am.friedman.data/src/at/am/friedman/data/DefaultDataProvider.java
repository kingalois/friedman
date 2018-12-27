package at.am.friedman.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.data.DiedPersonImpl.DiedPersonBuilder;
import at.am.friedman.data.enums.GraveZone;
import at.am.friedman.shared.CemeteryDataProviderInterface;
import at.am.friedman.shared.CemeteryOptionsInterface;
import at.am.friedman.shared.DataAccessorFactory;
import at.am.friedman.shared.DataAccessorInterface;
import at.am.friedman.shared.DataChangeListener;
import at.am.friedman.shared.DiedPersonInterface;
import at.am.friedman.shared.GraveInterface;
import at.am.friedman.shared.GraveOwnerInterface;
import at.am.friedman.shared.MultiGraveInterface;
import at.am.common.logging.LogFactory;

public class DefaultDataProvider implements CemeteryDataProviderInterface {

	private final DataAccessorInterface dataAccessor;
	private final CemeteryOptionsInterface options;

	private final List<DataChangeListener> dataChangeListener;

	private GraveOwnerInterface actualOwner;

	private GraveInterface actualGrave;

	private DiedPersonInterface actualDiedPerson;

	private static final Logger log = LogFactory.makeLogger();

	public DefaultDataProvider() {
		dataAccessor = DataAccessorFactory.createDataAccessor();
		dataAccessor.initDataAccessor();
		options = CemeteryOptions.getInstance(Constants.OPTIONS_FILE);
		dataChangeListener = new ArrayList<>();

	}

	@Override
	public List<GraveOwnerInterface> getAllOwner() {
		return dataAccessor.getAllOwners();
	}

	@Override
	public List<GraveInterface> getAllGraves() {
		return dataAccessor.getAllGraves();
	}

	@Override
	public List<GraveInterface> getAllGravesByRow(int row) {
		List<GraveInterface> gravesByRow = new ArrayList<>();
		for (GraveInterface g : getAllGraves()) {
			if (g.getRow().equals(row)) {
				gravesByRow.add(g);
			}
		}
		return gravesByRow;
	}

	@Override
	public List<DiedPersonInterface> getAllDiedPersons() {
		return dataAccessor.getAllDiedPersons();
	}

	@Override
	public ArrayList<GraveInterface> getGravesByOwner(GraveOwnerInterface owner) {
		ArrayList<GraveInterface> ret = new ArrayList<>();
		for (GraveInterface grave : getAllGraves()) {
			if (grave.getOwnerId() == owner.getId()) {
				ret.add(grave);
			}
		}
		return ret;
	}

	@Override
	public void addOrUpdateGrave(GraveInterface grave) {
		dataAccessor.saveGrave(grave);
		notifyDataChangeListener(Collections.singletonList(grave));
	}

	@Override
	public void deleteGrave(GraveInterface grave) {
		dataAccessor.deleteGrave(grave);

	}

	private void notifyDataChangeListener(Collection<GraveInterface> graves) {
		for (DataChangeListener listener : dataChangeListener) {
			listener.onDataChange(graves);
		}
	}

	@Override
	public DiedPersonInterface getNewDiedPerson() {
		DiedPersonInterface ret = DiedPersonBuilder
		ret.setId(dataAccessor.getMaxIdForDiedPerson());
		return ret;
	}

	@Override
	public void addOrUpdateDiedPerson(DiedPersonInterface person) {
		dataAccessor.saveDiedPerson(person);
		notifyDataChangeListener(Collections.singletonList(getGraveByDiedPerson(person)));
	}

	@Override
	public void dump() {
		dataAccessor.dump();

	}

	@Override
	public GraveOwnerInterface getNewGraveOwner() {
		GraveOwnerInterface owner = new GraveOwnerImpl();
		owner.setId(dataAccessor.getMaxIdForOwner());
		return owner;
	}

	@Override
	public GraveInterface getNewGrave() {
		GraveInterface grave = new GraveImpl();
		grave.setId(dataAccessor.getMaxIdForGrave());

		return grave;
	}

	@Override
	public void addOrUpdateGravOwner(GraveOwnerInterface owner) {
		dataAccessor.saveGraveOwner(owner);
		notifyDataChangeListener(getGravesByOwner(owner));
	}

	@Override
	public void deleteOwner(GraveOwnerInterface owner) {
		dataAccessor.deleteGraveOwner(owner);
		notifyDataChangeListener(getGravesByOwner(owner));
	}

	@Override
	public void deleteDiedPerson(DiedPersonInterface diedPerson) {
		dataAccessor.deleteDiedPerson(diedPerson);
		notifyDataChangeListener(Collections.singletonList(getGraveByDiedPerson(diedPerson)));
	}

	@Override
	public GraveOwnerInterface getActualGraveOwner() {
		return actualOwner;
	}

	@Override
	public void setActualGraveOwner(GraveOwnerInterface owner) {
		this.actualOwner = owner;
		this.actualDiedPerson = null;
		this.actualGrave = null;

	}

	@Override
	public GraveInterface getActualGrave() {

		return actualGrave;
	}

	@Override
	public void setActualGrave(GraveInterface grave) {
		this.actualGrave = grave;
		this.actualDiedPerson = null;
		this.actualOwner = null;

	}

	@Override
	public DiedPersonInterface getActualDiedPerson() {

		return actualDiedPerson;
	}

	@Override
	public void setActualDiedPerson(DiedPersonInterface diedPerson) {
		this.actualDiedPerson = diedPerson;
		this.actualGrave = null;
		this.actualOwner = null;

	}

	@Override
	public GraveInterface getGraveById(int graveId) {
		List<GraveInterface> allGraves = getAllGraves();
		for (GraveInterface grave : allGraves) {
			if (grave.getId() == graveId) {
				return grave;
			}
		}
		return null;
	}

	@Override
	public List<DiedPersonInterface> getDiedPersonsForGrave(GraveInterface grave) {
		List<DiedPersonInterface> ret = new ArrayList<>();
		if (grave == null) {
			return ret;
		}
		for (DiedPersonInterface diedPerson : getAllDiedPersons()) {
			if (grave.getId() == diedPerson.getGraveId()) {
				ret.add(diedPerson);
			}
		}
		Collections.sort(ret);
		return ret;
	}

	@Override
	public GraveOwnerInterface getOwnerFromGrave(GraveInterface grave) {
		if (grave == null) {
			return null;
		}
		for (GraveOwnerInterface owner : getAllOwner()) {
			if (owner.getId() == grave.getOwnerId()) {

				return owner;
			}
		}
		return null;
	}

	@Override
	public GraveInterface getGraveByDiedPerson(DiedPersonInterface diedPerson) {
		for (GraveInterface grave : getAllGraves()) {
			if (grave.getId() == diedPerson.getGraveId()) {
				return grave;
			}
		}
		return null;
	}

	@Override
	public void addOrUpdateMultiGrave(MultiGraveInterface mGrave) {
		dataAccessor.saveMultiGrave(mGrave);
		List<GraveInterface> graves = new ArrayList<>();
		for (int id : mGrave.getGraveIds()) {
			graves.add(getGraveById(id));
		}
		notifyDataChangeListener(graves);
	}

	@Override
	public void deleteMultiGrave(MultiGraveInterface mGrave) {
		dataAccessor.deleteMultiGrave(mGrave);
		List<GraveInterface> graves = new ArrayList<>();
		for (int id : mGrave.getGraveIds()) {
			graves.add(getGraveById(id));
		}
		notifyDataChangeListener(graves);
	}

	@Override
	public MultiGraveInterface getNewMultiGrave() {
		MultiGraveInterface multiGrave = new MultiGraveImpl();
		multiGrave.setId(dataAccessor.getMaxIdForMultiGrave());
		return multiGrave;
	}

	@Override
	public boolean isGraveInMulitGrave(GraveInterface grave) {
		for (MultiGraveInterface mGrave : getAllMultiGraves()) {
			if (mGrave.isInMultiGrave(grave.getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<MultiGraveInterface> getAllMultiGraves() {
		return dataAccessor.getAllMultiGraves();
	}

	@Override
	public MultiGraveInterface getMultiGraveForGrave(GraveInterface grave) {
		for (MultiGraveInterface mGrave : getAllMultiGraves()) {
			if (mGrave.isInMultiGrave(grave.getId())) {
				return mGrave;
			}
		}
		return null;
	}

	@Override
	public GraveInterface getGrave(GraveZone zone, String row, String place) {
		for (GraveInterface grave : getAllGraves()) {
			if (grave.getZone().equals(zone) && grave.getRow().equals(row) && grave.getPlace().equals(place)) {
				return grave;
			}
		}
		return null;
	}

	@Override
	public MultiGraveInterface getMultiGraveForGraves(List<GraveInterface> graves) {
		for (MultiGraveInterface mGrave : getAllMultiGraves()) {
			for (GraveInterface grave : graves) {
				if (mGrave.isInMultiGrave(grave.getId())) {
					return mGrave;
				}
			}
		}
		return null;
	}

	@Override
	public void addDataChangeListener(DataChangeListener listener) {
		dataChangeListener.add(listener);

	}

	@Override
	public CemeteryOptionsInterface getCemeteryOptions() {
		return options;
	}

	@Override
	public void notifyOptionsChanged() {
		options.saveState(Constants.OPTIONS_FILE);
	}
}
