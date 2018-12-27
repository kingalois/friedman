package at.am.friedman.data;

import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

import at.am.friedman.commons.utils.Constants;
import at.am.friedman.shared.PersonInterface;

public final class Person implements PersonInterface, IdBasedObjectInterface {

	private final String firstname;
	private final String surname;
	private final String street;
	private final String houseNr;
	private final String postalCode;
	private final String town;
	private final String telephon;
	private final IdBasedObject id;

	private Person(@NonNull PersonBuilder builder) {
		this.id = IdBasedObject.of(builder.id);
		this.firstname = builder.firstName;
		this.surname = builder.surName;
		this.street = builder.street;
		this.houseNr = builder.houseNr;
		this.postalCode = builder.postalCode;
		this.town = builder.town;
		this.telephon = builder.telephon;
	}

	@NonNull
	public static Person create(@NonNull PersonBuilder builder) {
		Objects.requireNonNull(builder);
		return new Person(builder);

	}

	@Override
	public int getId() {
		return id.getId();
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
	public String getStreet() {
		return street;
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
	public String getTown() {
		return town;
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
	public String getCellText() {
		return getFullName();
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

	public static class PersonBuilder {

		private int id;
		private String firstName;
		private String surName;
		private String street;
		private String houseNr;
		private String postalCode;
		private String town;
		private String telephon;

		public PersonBuilder() {

		}

		public PersonBuilder id(int id) {
			this.id = id;
			return this;
		}

		public PersonBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public PersonBuilder surName(String surName) {
			this.surName = surName;
			return this;
		}

		public PersonBuilder street(String street) {
			this.street = street;
			return this;
		}

		public PersonBuilder houseNr(String houseNr) {
			this.houseNr = houseNr;
			return this;
		}

		public PersonBuilder postalCode(String postalCode) {
			this.postalCode = postalCode;
			return this;
		}

		public PersonBuilder town(String town) {
			this.town = town;
			return this;
		}

		public PersonBuilder telephon(String telephon) {
			this.telephon = telephon;
			return this;
		}
	}

}
