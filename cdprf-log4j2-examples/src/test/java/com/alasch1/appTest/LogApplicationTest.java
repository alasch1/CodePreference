package com.alasch1.appTest;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alasch1.appTest.InvocationScopeLogger.Scope;
import com.alasch1.cdprf.commons.utils.ConfigUtil;
import com.alasch1.logging.impl.LogConfiguration;
import com.alasch1.logging.impl.StdLogContext;
import com.alasch1.logging.mocks.AppConfigurationMock;

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
	private static final  String USER1 = "user-1";
	private static final  String USER2 = "user-2";
	
	public static void main(String[] args) throws ConfigurationException {
		initConfiguration();
		printBulkMessages();
		printMessageExample();
	}
	
	private static void printBulkMessages() {
		StdLogContext.createUserContext(USER1);
		InvocationScopeLogger.fromScope(Scope.SCOPE1, "app test", LOG, StdLogContext.STD_FORMAT, "ApplicationTest Start");
		//LOG.info("ApplicationTest Start");
		LOG.info("Expected header is with initial signature");

		logMessagesBulk("Test message before header update");

		appConfig.setSignature("Signature was updated");
		LOG.info("Expected header (in the next file) is with updated signature");
		logMessagesBulk("Test message after header update");

		InvocationScopeLogger.toScope(Scope.SCOPE1, "app test", LOG, StdLogContext.STD_FORMAT, "ApplicationTest End");
		//LOG.info("ApplicationTest End");
	}
	
	private static void printMessageExample() {
		StdLogContext.createUserContext(USER2);
		LOG.info("ApplicationTest Start");
		LOG.info("Test message");
		LOG.info("ApplicationTest End");
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
		StdLogContext.setConfiguration(logConfiguration);
		LOG.info("Init configuration was done");
	}
	
	private static Logger initLog() {
		final Logger logger = LogManager.getLogger();
		return logger;
	}
	
}
