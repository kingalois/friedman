package at.am.friedman.shared;

import java.util.List;

import at.am.friedman.data.enums.MultiGravePosition;

public interface MultiGraveInterface extends SaveableObject {

	public void setId(int id);

	public int getId();

	public void addGraveId(int graveId);

	public List<Integer> getGraveIds();

	public int getNrOfGraves();

	public MultiGravePosition getGravePosition(int graveId);

	public boolean isInMultiGrave(int graveId);

	public void clearGraves();

}
