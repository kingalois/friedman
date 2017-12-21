package at.am.friedman.data;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.ui.IMemento;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.commons.utils.StringUtils;
import at.am.friedman.data.enums.InternalPosition;
import at.am.friedman.shared.DiedPersonInterface;

public class DiedPersonImpl extends AbstractPerson implements DiedPersonInterface {

	long deathday;
	int internalPosition;
	int graveId;
	long DayOfInterment;
	int age;
	Collection<String> pictures = new HashSet<>();

	String comment;

	@Override
	public long getDeathday() {

		return deathday;
	}

	@Override
	public int getInternalPosition() {

		return internalPosition;
	}

	@Override
	public int getGraveId() {

		return graveId;
	}

	@Override
	public void setDeathday(long deathday) {
		this.deathday = deathday;

	}

	@Override
	public void setInternalPosition(int position) {
		this.internalPosition = position;

	}

	@Override
	public void setGraveId(int graveId) {
		this.graveId = graveId;

	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append(getFullName()).append(Constants.SPACE);
		builder.append("Todestag: " + StringUtils.stringifyDateLong(getDeathday())).append(Constants.SPACE);
		builder.append("Begräbnistag: " + StringUtils.stringifyDateLong(getDayOfInterment())).append(Constants.SPACE);
		builder.append("Lage: " + InternalPosition.values()[getInternalPosition()].toString()).append(Constants.SPACE);
		return builder.toString();

	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public long getDayOfInterment() {
		return DayOfInterment;
	}

	@Override
	public void setAge(int age) {
		this.age = age;

	}

	@Override
	public void setDayOfInterment(long dayOfInterment) {
		this.DayOfInterment = dayOfInterment;

	}

	@Override
	public void setDescription(String description) {
		this.comment = description;

	}

	@Override
	public String getDescription() {
		return comment;
	}

	@Override
	public String getDetailText() {
		StringBuilder builder = new StringBuilder();
		builder.append(getFullName()).append(Constants.SPACE);
		builder.append("Todestag: " + StringUtils.stringifyDateLong(getDeathday())).append(Constants.SPACE);
		builder.append("Begräbnistag: " + StringUtils.stringifyDateLong(getDayOfInterment())).append(Constants.SPACE);
		builder.append("im Alter: " + getAge()).append(Constants.SPACE);
		builder.append("Lage: " + InternalPosition.values()[getInternalPosition()].toString()).append(Constants.SPACE);

		return builder.toString();
	}

	@Override
	public void saveState(IMemento memento) {
		/*
		 * long deathday; int internalPosition; int graveId; long
		 * DayOfInterment; int age;
		 * 
		 * String comment;
		 */

		IMemento diedPerson = memento.createChild("DiedPerson");
		super.saveState(diedPerson);
		diedPerson.putString("deathday", Long.toString(getDeathday()));
		diedPerson.putString("dayofinterment", Long.toString(getDayOfInterment()));
		diedPerson.putInteger("internaPosition", getInternalPosition());
		diedPerson.putInteger("graveId", getGraveId());
		diedPerson.putInteger("age", getAge());
		diedPerson.putString("comment", getDescription());
		IMemento picturesMemento = diedPerson.createChild("pictures");
		for (String picture : getPictures()) {
			picturesMemento.createChild("picture").putString("path", picture);
		}

	}

	@Override
	public void restoreState(IMemento memento) {
		super.restoreState(memento);
		setDeathday(Long.parseLong(memento.getString("deathday")));
		setDayOfInterment(Long.parseLong(memento.getString("dayofinterment")));
		setInternalPosition(memento.getInteger("internaPosition"));
		setGraveId(memento.getInteger("graveId"));
		setAge(memento.getInteger("age"));
		setDescription(memento.getString("comment"));
		IMemento picturesMemento = memento.getChild("pictures");
		if (picturesMemento != null) {
			IMemento[] pictureArray = picturesMemento.getChildren("picture");
			for (IMemento picture : pictureArray) {
				addPicture(picture.getString("path"));
			}
		}
	}

	@Override
	public int compareTo(DiedPersonInterface o) {
		return new Long(o.getDayOfInterment()).compareTo(getDayOfInterment());
	}

	@Override
	public String getCellText() {
		StringBuilder builder = new StringBuilder();
		builder.append("Lage: ").append(InternalPosition.values()[getInternalPosition()].toString()).append(", ");
		builder.append(getFullName()).append(", ");
		builder.append("begraben am: ").append(StringUtils.stringifyDateLong(getDayOfInterment())).append(", ");
		builder.append("verstorben am: ").append(StringUtils.stringifyDateLong(getDeathday())).append(", ");
		builder.append("im Alter von: ").append(getAge());
		return builder.toString();
	}

	@Override
	public Collection<String> getPictures() {
		return pictures;
	}

	@Override
	public void addPicture(String pictureName) {
		pictures.add(pictureName);
	}

	@Override
	public void removePicture(String pictureName) {
		pictures.remove(pictureName);
	}

	@Override
	public boolean havePictures() {
		return !pictures.isEmpty();
	}

	@Override
	public void removePicture() {
		pictures.clear();

	}

}
