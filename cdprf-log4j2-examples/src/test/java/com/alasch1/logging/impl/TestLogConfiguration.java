package com.alasch1.logging.impl;

import static org.junit.Assert.*;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;
import com.alasch1.cdprf.commons.utils.ConfigUtil;

public class TestLogConfiguration {
	
	private LogConfiguration configuration;
	
	@Before
	public void setup() {
		ConfigUtil.setConfigDirProperty();
		configuration = new LogConfiguration();
		configuration.init();
	}

	@Test
	public void testInitOk() {
		VerbalTestExecutor.executeTest("testInitOk", () -> {
			assertTrue("No properties were read", configuration.getConfiguration().getKeys().hasNext() == true);
		});
	}

	@Test
	public void testGetProperties() throws ConfigurationException {
		VerbalTestExecutor.executeTest("testGetProperties", () -> {
			assertFalse("Invocation tag is missing", configuration.getInvocationTag().isEmpty());
			assertFalse("User ID tag is missing", configuration.getUserIdTag().isEmpty());
			assertFalse("Operation tag is missing", configuration.getOperationTag().isEmpty());
		});
	}
}
