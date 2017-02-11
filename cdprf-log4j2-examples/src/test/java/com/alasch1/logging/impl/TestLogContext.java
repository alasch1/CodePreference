package com.alasch1.logging.impl;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.logging.api.LogContext;
import com.alasch1.logging.mocks.AppConfigurationMock;
import com.alasch1.testutils.VerbalTestExecutor;

public class TestLogContext {
	
	private static Logger LOG = LogManager.getLogger();
	
	private LogConfiguration logConfiguration;
	private static final String USER_ID = "ADMIN";
	
	private LogContext context;
	
	@Before
	public void setup() throws ConfigurationException {
		logConfiguration = new LogConfiguration(new AppConfigurationMock());
		logConfiguration.init();
		LogContextHandler.setConfiguration(logConfiguration);
	}
	
	@Test
	public void testDefaultContextInit() {
		VerbalTestExecutor.executeTest("testDefaultContextInit", () -> {
			context = LogContextHandler.createContext();
			System.out.println("After init" + context);
			LOG.info("just checking");
			assertTrue("Log context is not valid !", context.isValid());
			assertUserId(LogContextHandler.ANONYMOUS_USER); // Expected is ANONYMOUS
		});
	}

	@Test
	public void testAdditionalContextTags() {
		VerbalTestExecutor.executeTest("testAdditionalContextTags", () -> {
			Map<String, String> addTags = new HashMap<>();
			addTags.put("Optional1", "kuku");
			context = LogContextHandler.createContext(USER_ID, addTags);
			System.out.println("Created "+context);
			LOG.info("just checking");
			assertTrue("Log context is not valid", context.isValid());
			assertUserId(USER_ID);
		});
	}

	@Test
	public void testContextUpdate() {
		VerbalTestExecutor.executeTest("testContextUpdate", () -> {
			context = LogContextHandler.createContext("");
			System.out.println("Created "+context);
			LOG.info("just checking 1");
			LogContextHandler.updateUserId(USER_ID);
			LOG.info("just checking 2");
			assertUserId(USER_ID);
		});
	}
	
	private void assertUserId(String expectedUserId) {
		assertTrue("Log context should contain user id !", context.toString().indexOf(expectedUserId) != -1);
	}

}
