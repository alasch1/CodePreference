package com.alasch1.codepreference.log4j2examples.logging.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.codepreference.commons.testutils.ConfigUtil;
import com.alasch1.codepreference.commons.testutils.VerbalTestExecutor;

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
			assertFalse("Conference ID tag is missing", configuration.getConferenceIdTag().isEmpty());
			assertFalse("User ID tag is missing", configuration.getUserIdTag().isEmpty());
			assertFalse("Process ID tag is missing", configuration.getProcessIdTag().isEmpty());
			assertFalse("ThreadID tag is missing", configuration.getThreadIdTag().isEmpty());
		});
	}
}
