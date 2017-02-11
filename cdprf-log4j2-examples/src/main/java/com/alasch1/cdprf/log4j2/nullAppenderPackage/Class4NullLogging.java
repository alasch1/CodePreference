package com.alasch1.cdprf.log4j2.nullAppenderPackage;

import org.apache.logging.log4j.Logger;

import com.alasch1.logging.impl.LoggerFactory;

public class Class4NullLogging {

	private static Logger LOG = LoggerFactory.getLogger(Class4NullLogging.class);
	
	public static void logMessage(String msg) {
		LOG.error("NullAppender is not configured : {} ", msg);
	}
}
