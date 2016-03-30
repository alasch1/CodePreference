package com.alasch1.codepreference.log4j2examples.appTest;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alasch1.codepreference.commons.testutils.ConfigUtil;
import com.alasch1.codepreference.log4j2examples.logging.api.LogContext;
import com.alasch1.codepreference.log4j2examples.logging.impl.LogConfiguration;
import com.alasch1.codepreference.log4j2examples.logging.impl.LogContextHandler;
import com.alasch1.codepreference.log4j2examples.logging.mocks.AppConfigurationMock;

/**
 * Test logger manually :
 * test logs rolling due to size, changed headers etc.
 * 
 * @author aschneider
 *
 */
public class LogApplicationTest {
	
	private static Logger LOG;// = LogManager.getLogger();	
	private static LogConfiguration logConfiguration;
	private static AppConfigurationMock appConfig;
	
	public static void main(String[] args) throws ConfigurationException {
		initConfiguration();
		printBulkMessages();
		printMessageExample();
	}
	
	private static void printBulkMessages() {
		LogContext ctxt = LogContextHandler.createContext("conference-1");
		
		try {			
			LOG.info("ApplicationTest Start");
			LOG.info("Expected header is with initial signature");
			
			logMessagesBulk("Test message before header update");
			
			appConfig.setSignature("Signature was updated");
			LOG.info("Expected header (in the next file) is with updated signature");
			logMessagesBulk("Test message after header update");
			
			LOG.info("ApplicationTest End");
		}
		finally {
			ctxt.clear();
		}		
	}
	
	private static void printMessageExample() {
		LogContext ctxt = LogContextHandler.createContext("conference-2");
		
		try {			
			LOG.info("ApplicationTest Start");
			LOG.info("Test message");
			LOG.info("ApplicationTest End");
		}
		finally {
			ctxt.clear();
		}		
	}
	
	private static void logMessagesBulk(String message) {
		for (int i=1; i<=20; i++) {
			LOG.info("{} - #{}", message, i);
		}
	}
	
	private static void initConfiguration() throws ConfigurationException {
		ConfigUtil.setLog4jConfig("app-demo-log4j2.xml");
		appConfig = new AppConfigurationMock();
		logConfiguration = new LogConfiguration(appConfig);
		logConfiguration.init();
		LOG = initLog();
		LogContextHandler.setConfiguration(logConfiguration);
		LOG.info("Init configuration was done");
	}
	
	private static Logger initLog() {
		final Logger logger = LogManager.getLogger();
		return logger;
	}
	
}
