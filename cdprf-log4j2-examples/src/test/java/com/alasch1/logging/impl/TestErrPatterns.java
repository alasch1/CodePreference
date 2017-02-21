package com.alasch1.logging.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;
import com.alasch1.logging.mocks.AppConfigurationMock;
import com.alasch1.logging.mocks.ErrorsPatterns4Tests;
import com.alasch1.logging.testutils.Log4jHelper;

public class TestErrPatterns {

	private static Logger LOG = LogManager.getLogger();
	private LogConfiguration logConfiguration;
	
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
	public void testAppenderExists() {
		VerbalTestExecutor.executeTest("testAppenderExists", () -> {
			assertNotNull(Log4jHelper.getRollingFileAppender("ErrorsLog"));
		});
	}
	
	@Test
	public void testCustomMsgIsLogged() {
		VerbalTestExecutor.executeTest("testCustomMsgIsLogged", () -> {
			String customMsg = "Error msg for logging test";
			for (String pattern : ErrorsPatterns4Tests.TEST_VALUES) {
				String patternedError = pattern + ":" +customMsg;
				System.out.println("Produced patterned error:" + patternedError);
				// Logs here just for verbality and may be omitted
				LOG.error(customMsg);
				LOG.error(patternedError);
				assertTrue("The error message was nout put into the log", 
						patternedError.indexOf(pattern) != -1);
			}
		});
	}
	
}
