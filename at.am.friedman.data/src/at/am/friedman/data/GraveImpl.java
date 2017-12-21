package at.am.friedman.data;

import org.eclipse.ui.IMemento;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.data.enums.GraveType;
import at.am.friedman.data.enums.GraveZone;
import at.am.friedman.shared.GraveInterface;

public class GraveImpl extends HashableObject implements GraveInterface {

	String row;
	int ownerId;
	String place;
	GraveZone zone;
	GraveType type;

	int offset;
	long startTime;
	int runtime;

	@Override
	public String getRow() {

		return row;
	}

	@Override
	public String getPlace() {

		return place;
	}

	@Override
	public GraveZone getZone() {
		return zone;

	}

	@Override
	public GraveType getType() {

		return type;
	}

	@Override
	public int getOwnerId() {

		return ownerId;
	}

	@Override
	public void setRow(String row) {
		this.row = row;
	}

	@Override
	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public void setOwnerId(int owner) {
		this.ownerId = owner;

	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		GraveImpl other = (GraveImpl) obj;
		if (row != other.row)
			return false;
		if (place != other.place)
			return false;
		if (zone != other.zone)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getZone().toString() + " Reihe: " + getRow() + " Platz: " + getPlace() + " Type: " + type.name();
	}

	@Override
	public void setType(GraveType type) {
		this.type = type;

	}

	@Override
	public void setZone(GraveZone zone) {
		this.zone = zone;
	}

	@Override
	public String getDetailText() {
		StringBuilder builder = new StringBuilder();
		builder.append("Grab:");
		builder.append(Constants.NEWLINE);
		builder.append("Zone: " + zone);
		builder.append(Constants.NEWLINE);
		if(!zone.isWallGrave()){
			builder.append("Reihe: " + row + " ");
		}
		builder.append("Platz: " + place);
		if(offset >0 ){
			builder.append(Constants.NEWLINE);
			builder.append("Versatz(cm): ").append(offset);
		}
		builder.append(Constants.NEWLINE + Constants.SEPARATOR);
		
		return builder.toString();
	}

	@Override
	public int getOffset() {

		return offset;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;

	}

	@Override
	public long getStarttime() {
		return startTime;
	}

	@Override
	public int getRuntime() {
		return runtime;
	}

	@Override
	public void setStarttime(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public void setRuntime(int runtime) {
		this.runtime = runtime;

	}

	@Override
	public void saveState(IMemento memento) {
		IMemento grave = memento.createChild("grave");
		grave.putInteger("id", getId());
		grave.putString("type", getType().name());
		grave.putString("zone", getZone().name());
		grave.putString("row", getRow());
		grave.putString("place", getPlace());
		grave.putInteger("ownerid", getOwnerId());
		grave.putInteger("runtime", getRuntime());
		grave.putInteger("offset", getOffset());
		grave.putString("starttime", Long.toString(getStarttime()));

	}

	@Override
	public void restoreState(IMemento memento) {
		setId(memento.getInteger("id"));
		setType(GraveType.valueOf(memento.getString("type")));
		setZone(GraveZone.valueOf(memento.getString("zone")));
		setRow(memento.getString("row"));
		setPlace(memento.getString("place"));
		setOwnerId(memento.getInteger("ownerid"));
		setRuntime(memento.getInteger("runtime"));
		setOffset(memento.getInteger("offset"));
		setStarttime(Long.parseLong(memento.getString("starttime")));

	}

	@Override
	public String getText() {
		return toString();
	}
}
