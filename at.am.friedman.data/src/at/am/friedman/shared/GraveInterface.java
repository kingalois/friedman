package at.am.friedman.shared;

import at.am.friedman.data.enums.GraveType;
import at.am.friedman.data.enums.GraveZone;

public interface GraveInterface extends SaveableObject, ComboLabelProvider {

	int getId();

	String getRow();

	String getPlace();

	GraveZone getZone();

	GraveType getType();

	long getStarttime();

	int getRuntime();

	int getOwnerId();

	int getOffset();

	void setOffset(int offset);

	void setId(int id);

	void setRow(String row);

	void setPlace(String place);

	void setType(GraveType type);

	void setZone(GraveZone zone);

	void setOwnerId(int owner);

	void setStarttime(long startTime);

	void setRuntime(int runtime);

	public String getDetailText();

}
