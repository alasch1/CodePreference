package com.alasch1.cdprf.log4j2.examples;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alasch1.cdprf.commons.utils.ConfigUtil;

/** 
 * The example demonstrates, how to output errors into the separate file
 * 
 * @author ala schneider
 *
 */
public class ErrorsToSeparateLogExample {
	
	private static Logger LOG;

	public static void main(String[] args) {
		ConfigUtil.setLog4jConfig("separateErrorLog-log4j2.xml");
		// Logger initialization should be done after setting the log4j2 configuration
		LOG = LogManager.getLogger();
		LOG.info("This message should be in the info log");
		LOG.error("This message should be in the error log");
	}

}
