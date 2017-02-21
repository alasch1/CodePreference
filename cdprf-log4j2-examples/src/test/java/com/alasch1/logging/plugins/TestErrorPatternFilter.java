package com.alasch1.logging.plugins;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;
import com.alasch1.logging.impl.LogConfiguration;
import com.alasch1.logging.impl.LogContextHandler;
import com.alasch1.logging.mocks.AppConfigurationMock;
import com.alasch1.logging.mocks.ErrorsPatterns4Tests;
import com.alasch1.logging.testutils.StringAppender4Tests;

public class TestErrorPatternFilter {

	private LogConfiguration logConfiguration;
	private final String LAYOUT = "%m%n";
	private final String ERROR_PATTERNS_CLASS = "com.alasch1.logging.mocks.ErrorsPatterns4Tests";
	
	@Before
	public void setUp() throws Exception {
		logConfiguration = new LogConfiguration(new AppConfigurationMock());
		logConfiguration.init();
		LogContextHandler.setConfiguration(logConfiguration);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFilterMapNotEmpty() {
		VerbalTestExecutor.executeTest("testFilterMapNotEmpty", () -> {
			System.out.println("Class name:" + ErrorsPatterns4Tests.class.getName());
			ErrorPatternsFilter filter = ErrorPatternsFilter.createFilter(ERROR_PATTERNS_CLASS, false);
			System.out.println(filter);
			List<String> matchingValues = filter.getMatchingValues();
			assertTrue("Filter map should not be empty", ! matchingValues.isEmpty());
			assertEquals(ErrorsPatterns4Tests.TEST_VALUES.length, matchingValues.size());
		});
	}
	
	@Test
	public void testDropOnMatchFilter() {
		VerbalTestExecutor.executeTest("testDropOnMatchFilter", () -> {
			ErrorPatternsFilter filter = ErrorPatternsFilter.createFilter(ERROR_PATTERNS_CLASS, true);
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.appendWithFilter((appender) ->{
				String errorMsg = "Error msg for logging test";
				logger.info(errorMsg);
				System.out.println("Appender buffer 1:" + appender.getOutput());
				assertFalse("Message should not be filtered !", appender.getOutput().indexOf(errorMsg) == -1);
				String patternedError = ErrorsPatterns4Tests.ERROR_PATTERN_1+ errorMsg;
				logger.info(patternedError);
				System.out.println("Appender buffer 2:" + appender.getOutput());
				assertTrue("Message should be filtered !", appender.getOutput().indexOf(patternedError) == -1);
			}, logger, LAYOUT, filter);
		});
	}

	@Test
	public void testAcceptOnMatchFilter() {
		VerbalTestExecutor.executeTest("testAcceptOnMatchFilter", () -> {
			ErrorPatternsFilter filter = ErrorPatternsFilter.createFilter(ERROR_PATTERNS_CLASS, false);
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.appendWithFilter((appender) ->{
				String errorMsg = "Error msg for logging test";
				logger.info(errorMsg);
				System.out.println("Appender buffer 1:" + appender.getOutput());
				assertFalse("Message should be filtered !", appender.getOutput().indexOf(errorMsg) != -1);
				String patternedError = ErrorsPatterns4Tests.ERROR_PATTERN_1+ errorMsg;
				logger.info(patternedError);
				System.out.println("Appender buffer 2:" + appender.getOutput());
				assertTrue("Message should not be filtered !", appender.getOutput().indexOf(patternedError) != -1);
			}, logger, LAYOUT, filter);
		});
	}
	
	@Test
	public void tesInvalidErrorsPatternClass() {
		VerbalTestExecutor.executeTest("tesInvalidErrorsPatternClass", () -> {
			ErrorPatternsFilter filter = ErrorPatternsFilter.createFilter(InvalidErrorsPatterns.class.getName(), false);
			System.out.println(filter);
			List<String> matchingValues = filter.getMatchingValues();
			assertTrue("Filter map should be empty",  matchingValues.isEmpty());
		});
	}
	
	static class InvalidErrorsPatterns {
		
	}
}
