package at.am.friedman.shared;

import at.am.friedman.data.DefaultDataProvider;

public class DataProviderFactory {

	private static CemeteryDataProviderInterface	instance	= null;

	public static CemeteryDataProviderInterface createDataProvider ( ) {
		if ( instance == null ) {
			instance = new DefaultDataProvider();
		}
		return instance;
	}

}
