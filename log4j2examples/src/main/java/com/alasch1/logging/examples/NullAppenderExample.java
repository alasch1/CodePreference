package com.alasch1.logging.examples;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alasch1.logging.examples.nullAppenderPackage.Class4NullLogging;
import com.alasch1.testutils.ConfigUtil;

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
	}

}
