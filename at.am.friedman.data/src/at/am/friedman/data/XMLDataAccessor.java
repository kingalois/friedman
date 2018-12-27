package at.am.friedman.data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;

import at.am.common.logging.LogFactory;
import at.am.friedman.commons.utils.Constants;
import at.am.friedman.data.seriealizer.DiedPersonSerializer;
import at.am.friedman.shared.CemeteryMemento;
import at.am.friedman.shared.DataAccessorInterface;
import at.am.friedman.shared.DiedPersonInterface;
import at.am.friedman.shared.GraveInterface;
import at.am.friedman.shared.GraveOwnerInterface;
import at.am.friedman.shared.MultiGraveInterface;

public class XMLDataAccessor implements DataAccessorInterface, CemeteryMemento {

	private final Logger log = LogFactory.makeLogger();

	private final List<GraveOwnerInterface> allGraveOwner = new ArrayList<>();
	private final List<GraveInterface> allGraves = new ArrayList<>();
	private final List<DiedPersonInterface> allDiedPerson = new ArrayList<>();
	private final List<MultiGraveInterface> allMultiGraves = new ArrayList<>();

	private final IDGenerator graveIdGenerator = new IDGenerator();
	private final IDGenerator graveOwnerIdGenerator = new IDGenerator();
	private final IDGenerator diedPersonIdGenerator = new IDGenerator();
	private final IDGenerator mulitGraveIdGenerator = new IDGenerator();

	@Override
	public void saveGraveOwner(GraveOwnerInterface owner) {
		allGraveOwner.remove(owner);
		allGraveOwner.add(owner);
		saveState();

	}

	@Override
	public void saveGrave(GraveInterface grave) {
		allGraves.remove(grave);
		allGraves.add(grave);
		saveState();
	}

	@Override
	public void saveDiedPerson(DiedPersonInterface diedPerson) {
		allDiedPerson.remove(diedPerson);
		allDiedPerson.add(diedPerson);
		saveState();
	}

	@Override
	public void saveMultiGrave(MultiGraveInterface grave) {
		allMultiGraves.remove(grave);
		allMultiGraves.add(grave);
		saveState();
	}

	@Override
	public void deleteDiedPerson(DiedPersonInterface diedPerson) {
		allDiedPerson.remove(diedPerson);
		saveState();
	}

	@Override
	public void deleteGrave(GraveInterface grave) {
		allDiedPerson.remove(grave);
		saveState();
	}

	@Override
	public void deleteGraveOwner(GraveOwnerInterface owner) {
		allGraveOwner.remove(owner);
		saveState();
	}

	@Override
	public void deleteMultiGrave(MultiGraveInterface mulitGrave) {
		allMultiGraves.remove(mulitGrave);
		saveState();
	}

	@Override
	public void initDataAccessor() {
		loadState();

	}

	@Override
	public List<GraveInterface> getAllGraves() {
		return allGraves;
	}

	@Override
	public List<GraveOwnerInterface> getAllOwners() {
		return allGraveOwner;
	}

	@Override
	public List<DiedPersonInterface> getAllDiedPersons() {
		return allDiedPerson;
	}

	@Override
	public List<MultiGraveInterface> getAllMultiGraves() {
		return allMultiGraves;
	}

	@Override
	public int getMaxIdForDiedPerson() {
		return diedPersonIdGenerator.getNewId();
	}

	@Override
	public int getMaxIdForGrave() {
		return graveIdGenerator.getNewId();
	}

	@Override
	public int getMaxIdForOwner() {
		return graveOwnerIdGenerator.getNewId();
	}

	@Override
	public int getMaxIdForMultiGrave() {
		return mulitGraveIdGenerator.getNewId();
	}

	@Override
	public void dump() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveState() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(Constants.DATA_FILE);
			XMLMemento memento = XMLMemento.createWriteRoot("cemetery");
			for (GraveInterface grave : getAllGraves()) {
				grave.saveState(memento);
			}
			for (DiedPersonInterface p : getAllDiedPersons()) {
				DiedPersonSerializer.serialize(p, memento);
			}
			for (GraveOwnerInterface o : getAllOwners()) {
				o.saveState(memento);
			}
			for (MultiGraveInterface m : getAllMultiGraves()) {
				m.saveState(memento);
			}
			memento.save(writer);

		} catch (IOException e) {
			log.severe("Cannot write the cemetery data: " + e.getMessage());
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				// do nothing
			}
		}

	}

	@Override
	public void loadState() {
		FileReader reader = null;

		try {
			reader = new FileReader(Constants.DATA_FILE);
			XMLMemento memento = XMLMemento.createReadRoot(reader);
			for (IMemento memChild : memento.getChildren()) {
				switch (memChild.getType()) {
				case "grave":
					GraveInterface grave = new GraveImpl();
					grave.restoreState(memChild);
					graveIdGenerator.setId(grave.getId());
					allGraves.add(grave);
					break;
				case "DiedPerson":
					DiedPersonInterface diedPerson = new DiedPersonImpl();
					diedPerson.restoreState(memChild);
					diedPersonIdGenerator.setId(diedPerson.getId());
					allDiedPerson.add(diedPerson);
					break;
				case "owner":
					GraveOwnerInterface owner = new GraveOwnerImpl();
					owner.restoreState(memChild);
					graveOwnerIdGenerator.setId(owner.getId());
					allGraveOwner.add(owner);
					break;
				case "multigrave":
					MultiGraveInterface multiGrave = new MultiGraveImpl();
					multiGrave.restoreState(memChild);
					mulitGraveIdGenerator.setId(multiGrave.getId());
					allMultiGraves.add(multiGrave);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			log.severe("Cannot load cenetery data: " + e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}

	@Override
	public void saveState(String path) {
		saveState();

	}

	@Override
	public void loadState(String path) {
		loadState();
	}

}
