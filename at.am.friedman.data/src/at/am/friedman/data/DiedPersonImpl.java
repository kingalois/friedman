package at.am.friedman.data;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.ui.IMemento;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.commons.utils.StringUtils;
import at.am.friedman.data.Person.PersonBuilder;
import at.am.friedman.data.enums.InternalPosition;
import at.am.friedman.data.seriealizer.PersonSerializer;
import at.am.friedman.shared.DiedPersonInterface;

public class DiedPersonImpl implements DiedPersonInterface, IdBasedObjectInterface {

	private final Person person;
	private final long deathday;
	private final int internalPosition;
	private final int graveId;
	private final long DayOfInterment;
	private final int age;
	private final Collection<String> pictures = new HashSet<>();
	private final String comment;

	private DiedPersonImpl(DiedPersonBuilder builder) {
		this.person = Person.create(builder.personBuilder);
	}

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
		PersonSerializer personSerializer = new PersonSerializer();
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

	public class DiedPersonBuilder {

		private PersonBuilder personBuilder;

	}

	@Override
	public String getFullName() {
		return person.getFullName();
	}

	@Override
	public String getSurname() {
		return person.getSurname();
	}

	@Override
	public String getFirstName() {
		return person.getFirstName();
	}

	@Override
	public String getStreet() {
		return person.getStreet();
	}

	@Override
	public String getHouseNr() {
		return person.getHouseNr();
	}

	@Override
	public String getPostalCode() {
		return person.getPostalCode();
	}

	@Override
	public String getTown() {
		return person.getTown();
	}

	@Override
	public String getTelephon() {
		return person.getTelephon();
	}

	@Override
	public String getText() {
		return person.getText();
	}

	@Override
	public int getId() {
		return person.getId();
	}

}
