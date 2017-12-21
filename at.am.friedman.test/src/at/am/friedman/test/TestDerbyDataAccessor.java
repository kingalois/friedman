package at.am.friedman.test;

import org.junit.Test;

import at.am.friedman.shared.DataAccessorFactory;
import at.am.friedman.shared.DataAccessorInterface;

public class TestDerbyDataAccessor {

	@Test
	public void test ( ) {
		DataAccessorInterface accessor = DataAccessorFactory.createDataAccessor();
		accessor.initDataAccessor();
	}

}
