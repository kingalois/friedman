package at.am.friedman.shared;

import at.am.friedman.data.IdBasedObjectInterface;

public interface PersonInterface extends IdBasedObjectInterface, ComboLabelProvider, TableCellLabelProvider {

	String getFullName();

	String getSurname();

	String getFirstName();

	String getStreet();

	String getHouseNr();

	String getDetailText();

	String getPostalCode();

	String getTown();

	String getTelephon();

}
