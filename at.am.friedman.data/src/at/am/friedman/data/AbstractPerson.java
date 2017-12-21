package at.am.friedman.data;

import org.eclipse.ui.IMemento;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.shared.PersonInterface;

public abstract class AbstractPerson extends HashableObject implements PersonInterface {

	private String firstname;
	private String surname;
	private String street = "";
	private String houseNr = "";
	private String postalCode = "";
	private String town = "";

	private String telephon = "";

	@Override
	public void setTelephon(String telephon) {
		this.telephon = telephon;
	}

	@Override
	public String getTelephon() {
		return telephon;
	}

	@Override
	public String getFullName() {

		return getFirstName() + " " + getSurname();
	}

	@Override
	public String getSurname() {

		return surname;
	}

	@Override
	public String getFirstName() {

		return firstname;
	}

	@Override
	public void setSurname(String surname) {
		this.surname = surname;

	}

	@Override
	public void setFirstName(String firstname) {
		this.firstname = firstname;

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getFullName()).append(Constants.SPACE);
		builder.append(street).append(Constants.SPACE);
		builder.append(houseNr).append(Constants.SPACE);
		builder.append(town).append(Constants.SPACE);
		builder.append(postalCode).append(Constants.SPACE);
		builder.append(telephon).append(Constants.SPACE);
		return builder.toString();
	}

	@Override
	public void setStreet(String street) {
		this.street = street;

	}

	@Override
	public String getStreet() {
		return street;
	}

	@Override
	public void setHouseNr(String houseNr) {
		this.houseNr = houseNr;

	}

	@Override
	public String getHouseNr() {

		return houseNr;
	}

	@Override
	public String getPostalCode() {
		return postalCode;
	}

	@Override
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String getTown() {
		return town;
	}

	@Override
	public void setTown(String town) {
		this.town = town;
	}

	@Override
	public String getDetailText() {
		StringBuilder builder = new StringBuilder();
		builder.append(firstname + " " + surname);
		builder.append(Constants.NEWLINE);
		builder.append(getStreet() + " " + getHouseNr());
		builder.append(Constants.NEWLINE);
		builder.append(getPostalCode() + " " + getTown());
		builder.append(Constants.NEWLINE);
		return builder.toString();
	}

	@Override
	public String getText() {
		return firstname + " " + surname;
	}

	@Override
	public void saveState(IMemento memento) {
		memento.putInteger("id", getId());
		memento.putString("firstname", getFirstName());
		memento.putString("surname", getSurname());
		memento.putString("street", getStreet());
		memento.putString("housenr", getHouseNr());
		memento.putString("postalcode", getPostalCode());
		memento.putString("town", getTown());
		memento.putString("telephon", getTelephon());

	}

	@Override
	public void restoreState(IMemento memento) {
		setId(memento.getInteger("id"));
		setFirstName(memento.getString("firstname"));
		setSurname(memento.getString("surname"));
		setStreet(memento.getString("street"));
		setHouseNr(memento.getString("housenr"));
		setPostalCode(memento.getString("postalcode"));
		setTown(memento.getString("town"));
		setTelephon(memento.getString("telephon"));
	}

}
