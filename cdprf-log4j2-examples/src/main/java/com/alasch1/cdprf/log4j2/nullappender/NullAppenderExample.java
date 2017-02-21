package com.alasch1.cdprf.log4j2.nullappender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alasch1.cdprf.commons.utils.ConfigUtil;
import com.alasch1.cdprf.log4j2.loggingOffPackage.Class4LoggingOff;
import com.alasch1.cdprf.log4j2.nullAppenderPackage.Class4NullLogging;

public class NullAppenderExample {
	private static Logger LOG;

	public static void main(String[] args) {
		ConfigUtil.setLog4jConfig("nullAppender-log4j2.xml");
		// Logger initialization should be done after setting the log4j2 configuration
		LOG = LogManager.getLogger();
		runNullAppenderExample();
	}
	
	private static void runNullAppenderExample() {
		final String message = "the demo message";
		LOG.info("From the main {}", message);
		Class4NullLogging.logMessage(message);
		Class4LoggingOff.logMessage(message);
	}

}
