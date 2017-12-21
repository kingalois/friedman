package at.am.friedman.data;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;

import at.am.friedman.data.enums.MultiGravePosition;
import at.am.friedman.shared.MultiGraveInterface;

public class MultiGraveImpl extends HashableObject implements
		MultiGraveInterface {

	private final List<Integer> graveIds = new ArrayList<>();

	@Override
	public void addGraveId(int graveId) {
		this.graveIds.add(graveId);
	}

	@Override
	public List<Integer> getGraveIds() {
		return this.graveIds;
	}

	@Override
	public int getNrOfGraves() {

		return graveIds.size();
	}

	@Override
	public MultiGravePosition getGravePosition(int graveId) {
		if (graveIds.indexOf(graveId) == 0) {
			return MultiGravePosition.LEFT;
		} else if (graveIds.indexOf(graveId) == graveIds.size() - 1) {
			return MultiGravePosition.RIGHT;
		}
		return MultiGravePosition.CENTER;
	}

	@Override
	public boolean isInMultiGrave(int graveId) {
		return graveIds.contains(graveId);
	}

	@Override
	public void clearGraves() {
		graveIds.clear();

	}

	@Override
	public void saveState(IMemento memento) {
		IMemento multiGrave = memento.createChild("multigrave");
		multiGrave.putInteger("id", getId());
		multiGrave.putInteger("nrofgraves", getNrOfGraves());
		IMemento graves = multiGrave.createChild("graveids");
		for (int i = 0; i < getNrOfGraves(); i++) {
			graves.putInteger("grave" + i, getGraveIds().get(i));
		}

	}

	@Override
	public void restoreState(IMemento memento) {
		setId(memento.getInteger("id"));
		int nrOfGraves = memento.getInteger("nrofgraves");
		IMemento graves = memento.getChild("graveids");
		for (int i = 0; i < nrOfGraves; i++) {
			addGraveId(graves.getInteger("grave" + i));
		}

	}

}
