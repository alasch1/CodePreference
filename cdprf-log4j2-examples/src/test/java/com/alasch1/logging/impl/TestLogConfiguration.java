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
			assertFalse("Conference ID tag is missing", configuration.getConferenceIdTag().isEmpty());
			assertFalse("User ID tag is missing", configuration.getUserIdTag().isEmpty());
			assertFalse("Process ID tag is missing", configuration.getProcessIdTag().isEmpty());
			assertFalse("ThreadID tag is missing", configuration.getThreadIdTag().isEmpty());
		});
	}
}
