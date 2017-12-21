package at.am.friedman.shared;

import at.am.friedman.data.XMLDataAccessor;

public class DataAccessorFactory {

	public static DataAccessorInterface createDataAccessor() {
		return new XMLDataAccessor();
	}

}
