package com.alasch1.logging.impl;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;
import com.alasch1.logging.mocks.AppConfigurationMock;

public class TestLogContext {
	
	private static Logger LOG = LogManager.getLogger();
	
	private LogConfiguration csplLogConfiguration;
	private static final String USER_ID = "ADMIN";
	
	private StdLogContext context;
	
	@Before
	public void setup() throws ConfigurationException {
		csplLogConfiguration = new LogConfiguration(new AppConfigurationMock());
		csplLogConfiguration.init();
		StdLogContext.setConfiguration(csplLogConfiguration);
	}
	
	@Test
	public void testDefaultContextInit() {
		VerbalTestExecutor.executeTest("testDefaultContextInit", () -> {
			context = StdLogContext.createDefaultContext();
			System.out.println("After init" + context);
			LOG.info("just checking:{}", StdLogContext.toStringTags());
			assertTrue("CSPL context is not valid !", StdLogContext.isValid());
			assertUserId(StdLogContextTags.ANONYMOUS_USER); // Expected is ANONYMOUS
		});
	}

	@Test
	public void testAdditionalContextTags() {
		VerbalTestExecutor.executeTest("testAdditionalContextTags", () -> {
			final String OPTIONAL1 = "Optional1";
			final String OPTIONAL2 = "kuku";
			Map<String, String> addTags = new HashMap<>();
			addTags.put(OPTIONAL1, OPTIONAL2);
			context = StdLogContext.createContext(addTags, null);
			System.out.println("Created "+context);
			LOG.info("just checking");
			assertTrue("Log context is not valid", StdLogContext.isValid());
			assertTrue("Log context should contain user id !", StdLogContext.toStringTags().indexOf(OPTIONAL1) != -1);
			assertTrue("Log context should contain user id !", StdLogContext.toStringTags().indexOf(OPTIONAL2) != -1);
		});
	}

	@Test
	public void testContextUpdate() {
		VerbalTestExecutor.executeTest("testContextUpdate", () -> {
			context = StdLogContext.createUserContext("");
			System.out.println("Created "+context);
			LOG.info("just checking 1");
			StdLogContext.setUser(USER_ID);
			LOG.info("just checking 2");
			assertUserId(USER_ID);
		});
	}
	
	@Test
	public void testContextUseCurrent() {
		VerbalTestExecutor.executeTest("testContextUseCurrent", () -> {
			context = StdLogContext.createUserContext("");
			System.out.println("Created "+ context);
			LOG.info("just checking 1");
			StdLogContext.setUser(USER_ID);
			LOG.info("just checking 2");
			StdLogContext context2 = StdLogContext.useCurrent();
			System.out.println("Created "+ context2);
			LOG.info("just checking 3");
			assertEquals(context.toString(), StdLogContext.toStringTags());
		});
	}

	@Test
	public void testContextRecreate() {
		VerbalTestExecutor.executeTest("testContextRecreate", () -> {
			context = StdLogContext.createUserContext("");
			System.out.println("Created "+ context);
			LOG.info("just checking 1");
			StdLogContext.setUser(USER_ID);
			LOG.info("just checking 2");
			StdLogContext context2 = StdLogContext.createUserContext("");
			System.out.println("Created "+ context2);
			LOG.info("just checking 3");
			assertFalse("CSPL context should not contain user id !", StdLogContext.toStringTags().indexOf(USER_ID) != -1);
		});
	}

	private void assertUserId(String expectedUserId) {
		assertTrue("CSPL context should contain user id !", StdLogContext.toStringTags().indexOf(expectedUserId) != -1);
	}

}
