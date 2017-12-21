package at.am.friedman.shared;

public interface PersonInterface extends SaveableObject, ComboLabelProvider, TableCellLabelProvider {

	int getId();

	String getFullName();

	String getSurname();

	String getFirstName();

	void setSurname(String surname);

	void setFirstName(String firstname);

	void setId(int id);

	void setStreet(String street);

	String getStreet();

	void setHouseNr(String houseNr);

	String getHouseNr();

	String getDetailText();

	public String getPostalCode();

	public void setPostalCode(String postalCode);

	public String getTown();

	public void setTown(String town);

	public void setTelephon(String telephon);

	public String getTelephon();

}
