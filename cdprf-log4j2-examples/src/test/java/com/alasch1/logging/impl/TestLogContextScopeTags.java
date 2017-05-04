package com.alasch1.logging.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;

public class TestLogContextScopeTags {

	private LogConfiguration csplLogConfiguration;
	
	private static final String USER1 = "USER-1";
	private static final String USER2 = "USER-2";
	private static final String ABC = "ABC";
	private static final String ERT = "ERT";
	private static final String OPERATION = "doThing";
	
	private static Logger LOG = LogManager.getLogger();
	
	@Before
	public void setUp() throws Exception {
		csplLogConfiguration = new LogConfiguration();
		csplLogConfiguration.init();
		StdLogContext.setConfiguration(csplLogConfiguration);
	}

	@Test
	public void testContextScopeIn() {
		VerbalTestExecutor.executeTest("testContextScopeIn", () -> {
			StdLogContext context = StdLogContext.createDefaultContext();
			LOG.info("Context created:{}", context);
			assertInvocationTags();
			assertUserId(StdLogContextTags.ANONYMOUS_USER);
			StdLogContext.setUser(USER1);
			LOG.info("User defined:{}", context);
			assertUserId(USER1);
			StdLogContext.setScopeIn(new InvocationScope(ABC, ERT, OPERATION), LOG, "{}", context);
			assertInvocationTags();
			StdLogContext.setScopeOut(new InvocationScope(ERT, ABC), LOG, "{}", context);
			assertInvocationTags();
			StdLogContext.setUser(USER2);
			LOG.info("User changed:{}", context);
			assertUserId(USER2);
		});
	}
	

	private void assertTagEmpty(String tagKey) {
		assertTrue("Tag" + tagKey + " is not empty!!", Strings.isBlank(StdLogContext.getTag(tagKey)));
	}

	private void assertInvocationTags() {
		assertTagEmpty(csplLogConfiguration.getOperationTag());
		assertTagEmpty(csplLogConfiguration.getInvocationTag());
	}

	private void assertUserId(String expectedUserId) {
		assertEquals("Context should contain user id !", expectedUserId, StdLogContext.getTag(csplLogConfiguration.getUserIdTag()));
	}

}
