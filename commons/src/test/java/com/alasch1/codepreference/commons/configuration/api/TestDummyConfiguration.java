package com.alasch1.codepreference.commons.configuration.api;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.codepreference.commons.testutils.ConfigUtil;
import com.alasch1.codepreference.commons.testutils.VerbalTestExecutor;

public class TestDummyConfiguration {
	
	private static final String DUMMY_CONFIG_DIR = "dummy_folder";
	DummyConfiguration dummyConfiguration;
	
	@Before
	public void setup() {
		ConfigUtil.setConfigDirProperty(DUMMY_CONFIG_DIR);
		dummyConfiguration = new DummyConfiguration();
	}
	
	@AfterClass
	public static void restoreConfigurationDir() {
		ConfigUtil.setConfigDirProperty();
	}
	
	@Test
	public void testResetConfigDir() {
		VerbalTestExecutor.executeTest("testResetConfigDir", () -> {
			ConfigUtil.resetConfigDirProperty();
			System.out.println(ConfigUtil.getConfigDirProperty());
			assertTrue("Value of config.dir property is not empty", ConfigUtil.getConfigDirProperty().isEmpty());
		});
	}

	@Test
	public void testConfigPathWithConfigDir() {
		VerbalTestExecutor.executeTest("testConfigPathWithConfigDir", () -> {
			String fullPath = dummyConfiguration.buildConfigPath();
			System.out.println(fullPath);
			assertTrue("Value of config.dir property has no " + DUMMY_CONFIG_DIR + " word", 
					fullPath.indexOf(DUMMY_CONFIG_DIR) != -1);
		});
	}

	@Test (expected = ConfigurationError.class)
	public void testErrorOnInitNoFile() throws Exception {
		VerbalTestExecutor.executeTestAllowEx("testErrorOnInitNoFile", () -> {
			dummyConfiguration.init();
			assertNull(dummyConfiguration.getConfiguration());
		});
	}

	class DummyConfiguration extends AppPropertiesConfiguration {

		@Override
		public String getConfigFileName() {
			return "dummy.properties";
		}
		
	}
}
