package at.am.friedman.test;

import java.util.Calendar;
import java.util.logging.Logger;

import org.junit.Test;

import at.am.friedman.shared.CemeteryDataProviderInterface;
import at.am.friedman.shared.DataProviderFactory;
import at.am.friedman.shared.DiedPersonInterface;
import at.am.common.logging.LogFactory;

public class TestDataProvider {

	private final Logger	logger	= LogFactory.makeLogger();

	@Test
	public void testDataProvider ( ) {

		CemeteryDataProviderInterface provider = DataProviderFactory.createDataProvider();

		DiedPersonInterface p = provider.getNewDiedPerson();
		p.setFirstName("Josef");
		p.setSurname("Mustermann");
		p.setDayOfInterment(Calendar.getInstance().getTimeInMillis());
		logger.info(p.toString());
		provider.addOrUpdateDiedPerson(p);
		provider.dump();
	}

}
