package at.am.friedman.test;

import java.util.logging.Logger;

import org.junit.Test;

import at.am.common.logging.LogFactory;

public class TestLogging {

	private final Logger	logger	= LogFactory.makeLogger();

	@Test
	public void testDefaultLoggingFormat ( ) {
		logger.info("This is a simple info logging message");

	}

}
