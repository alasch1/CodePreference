package com.alasch1.logging.plugins;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.alasch1.cdprf.commons.configuration.api.AppConfiguration;
import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;
import com.alasch1.logging.mocks.AppConfigurationMock;
import com.alasch1.logging.testutils.StringAppender4Tests;


public class TestConfigConverter {
	
	final String MSG = "MSG";

	private static AppConfiguration configStub = new AppConfigurationMock();
	
	@Test
	public void testFormatNoConfiguration() {
		VerbalTestExecutor.executeTest("testFormatNoConfiguration", () -> {
			ConfigConverter converter = ConfigConverter.newInstance(new String[]{});
			StringBuilder toAppendTo = new StringBuilder(MSG);
			converter.format(null, toAppendTo);
			System.out.println(toAppendTo);
			assertEquals("Improper formatting", MSG + converter.getConfig(), toAppendTo.toString());
		});
	}

	@Test
	public void testFormatConfiguration() {
		VerbalTestExecutor.executeTest("testFormatConfiguration", () -> {
			ConfigConverter.setConfiguration(configStub);
			ConfigConverter converter = ConfigConverter.newInstance(new String[]{});
			StringBuilder toAppendTo = new StringBuilder(MSG);
			converter.format(null, toAppendTo);
			System.out.println(toAppendTo);
			assertEquals("Improper formatting", MSG + converter.getConfig(), toAppendTo.toString());
		});
	}

	@Test
	public void testConfigAppendedToLog() {
		VerbalTestExecutor.executeTest("testConfigAppendedToLog", () -> {
			final String layoutPattern = "%config %m%n";
			final Logger logger = LogManager.getRootLogger();
			
			StringAppender4Tests.append( (appender) -> {
					ConfigConverter.setConfiguration(configStub);
					logger.error("Test");
					System.out.println("Appender buffer:" + appender.getOutput());
					assertTrue("Configuration not found in message", appender.getOutput().indexOf(configStub.toString().replace('\n',  ' ')) != -1);
			}, 
			logger, layoutPattern, null);		
		});
	}

}
