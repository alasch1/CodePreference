package com.alasch1.cdprf.log4j2.loggingOffPackage;

import org.apache.logging.log4j.Logger;

import com.alasch1.logging.impl.LoggerFactory;

public class Class4LoggingOff {

	private static Logger LOG = LoggerFactory.getLogger(Class4LoggingOff.class);
	
	public static void logMessage(String msg) {
		LOG.error("Log-level OFF is not configured : {} ", msg);
	}
}
