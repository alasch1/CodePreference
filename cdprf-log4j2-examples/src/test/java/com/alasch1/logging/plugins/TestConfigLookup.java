package com.alasch1.logging.plugins;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.Log4jHelper;
import com.alasch1.cdprf.commons.testutils.StringAppender4Tests;
import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;
import com.alasch1.logging.mocks.AppConfigurationMock;

public class TestConfigLookup {

	private static final String CONFIG_LOOKUP = "config";
	static Logger LOG = LogManager.getLogger();
	private AppConfigurationMock configStub;

	@Before
	public void setup() {
		configStub = new AppConfigurationMock();
	}

	@Test
	public void testLookupWrongKey() {
		VerbalTestExecutor.executeTest("testLookupWrongKey", () -> {
			final String BAD_LOOKUP = "config-wrong";
			assertFalse("Configuration is not null", getLookup(BAD_LOOKUP).isEmpty());
		});
	}

	@Test
	public void testLookupNoConfiguration() {
		VerbalTestExecutor.executeTest("testLookupNoConfiguration", () -> {
			assertFalse("Configuration not found in lookup", getLookup(CONFIG_LOOKUP).isEmpty());
		});
	}

	@Test
	public void testLookupWithConfiguration() {
		VerbalTestExecutor.executeTest("testLookupWithConfiguration", () -> {
			LOG.info("Dummy msg");
			assertTrue("Configuration not found in lookup", 
					getLookup(CONFIG_LOOKUP).indexOf(flatString(configStub.toString())) != -1);
		});
	}

	@Test
	public void testHeaderLookupSubstitutor() {
		VerbalTestExecutor.executeTest("testHeaderLookupSubstitutor", () -> {
			final String headerFormat = "HEADER: ${config:all}";
			String header = Log4jHelper.interpolate(headerFormat);

			final String layoutPattern = "%m%n";
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.append((StringAppender4Tests appender) ->{
					logger.info("Test");
					System.out.println("Appender buffer:" + appender.getOutput());
					assertTrue("Configuration not found in message", appender.getOutput().indexOf(configStub.toString()) != -1);
			}, 
			logger, layoutPattern, header);		
		});
	}

	@Test
	public void testDynamicConfiuration() {
		VerbalTestExecutor.executeTest("testDynamicConfiuration", () -> {
			final String UPDATED_SIGNATURE = "Signature was updated";
			assertTrue("Header contains updated signature !!", Log4jHelper.getAppLogHeader().indexOf(UPDATED_SIGNATURE) == -1);
			configStub.setSignature(UPDATED_SIGNATURE);
			assertTrue("Header does not contain updated signature !!",Log4jHelper.getAppLogHeader().indexOf(UPDATED_SIGNATURE) != -1);
		});
	}

	private String getLookup(String key) {
		ConfigurationLookup lookup = new ConfigurationLookup();
		System.out.println(lookup.lookup(key));
		return flatString(lookup.lookup(key));
	}

	private String flatString(String source) {
		return source.replace('\n', ' ');
	}

}
