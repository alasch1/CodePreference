package com.alasch1.logging.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;
import com.alasch1.cdprf.logging.api.AppContextValues;
import com.alasch1.cdprf.logging.api.AppContextValues.InOutDirection;
import com.alasch1.cdprf.logging.api.LogContext;
import com.alasch1.logging.mocks.AppConfigurationMock;

public class TestLogContext {
	
	private static Logger LOG = LogManager.getLogger();
	private static final String USER_ID = "ADMIN";
	
	private LogConfiguration logConfiguration;
	private AppContextValues appContextValues;
	
	private LogContext context;
	
	@Before
	public void setup() throws ConfigurationException {
		logConfiguration = new LogConfiguration(new AppConfigurationMock());
		logConfiguration.init();
		LogContextHandler.setConfiguration(logConfiguration);
		initAppContext();
	}
	
	private void initAppContext() {
		appContextValues = new AppContextValues();
		appContextValues.source = "SSP";
		appContextValues.destination = "ABC";
		appContextValues.direction = InOutDirection.IN;
	}
	
	@Test
	public void testDefaultContextInit() {
		VerbalTestExecutor.executeTest("testDefaultContextInit", () -> {
			context = LogContextHandler.createContext(appContextValues);
			System.out.println("After init" + context);
			LOG.info("just checking");
			assertTrue("Log context is not valid !", context.isValid());
			assertUserId(AppContextValues.ANONYMOUS_USER); // Expected is ANONYMOUS
		});
	}

	@Test
	public void testAdditionalContextTags() {
		VerbalTestExecutor.executeTest("testAdditionalContextTags", () -> {
			final String OPTIONAL1 = "Optional1";
			final String OPTIONAL2 = "kuku";
			Map<String, String> addTags = new HashMap<>();
			addTags.put(OPTIONAL1, OPTIONAL2);
			context = LogContextHandler.createContext(appContextValues, addTags);
			System.out.println("Created "+context);
			LOG.info("just checking");
			assertTrue("Log context is not valid", context.isValid());
			assertTrue("Log context should contain user id !", context.toString().indexOf(OPTIONAL1) != -1);
			assertTrue("Log context should contain user id !", context.toString().indexOf(OPTIONAL2) != -1);
		});
	}

	@Test
	public void testContextUpdate() {
		VerbalTestExecutor.executeTest("testContextUpdate", () -> {
			context = createContext("");
			System.out.println("Created "+context);
			LOG.info("just checking 1");
			LogContextHandler.updateUserId(USER_ID);
			LOG.info("just checking 2");
			assertUserId(USER_ID);
		});
	}
	
	@Test
	public void testContextUseCurrent() {
		VerbalTestExecutor.executeTest("testContextUseCurrent", () -> {
			context = createContext("");
			System.out.println("Created "+ context);
			LOG.info("just checking 1");
			LogContextHandler.updateUserId(USER_ID);
			LOG.info("just checking 2");
			LogContext context2 = LogContextHandler.useCurrent();
			System.out.println("Created "+ context2);
			LOG.info("just checking 3");
			assertEquals(context.toString(), context2.toString());
		});
	}

	@Test
	public void testContextRecreate() {
		VerbalTestExecutor.executeTest("testContextRecreate", () -> {
			context = createContext("");
			System.out.println("Created "+ context);
			LOG.info("just checking 1");
			LogContextHandler.updateUserId(USER_ID);
			LOG.info("just checking 2");
			LogContext context2 = createContext("");
			System.out.println("Created "+ context2);
			LOG.info("just checking 3");
			assertFalse("CSPL context should not contain user id !", context2.toString().indexOf(USER_ID) != -1);
		});
	}

	
	private LogContext createContext(String userId) {
		LogContext context = LogContextHandler.createContext(appContextValues);
		if (Strings.isNotBlank(userId)) {
			LogContextHandler.updateUserId(userId);
		}
		return context;
	}
	
	private void assertUserId(String expectedUserId) {
		assertTrue("Log context should contain user id !", context.toString().indexOf(expectedUserId) != -1);
	}

}
