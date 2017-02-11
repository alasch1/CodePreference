package com.alasch1.logging.examples;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.alasch1.testutils.ConfigUtil;

public class SimpleThreadContextExample {
	private static Logger LOG;
	
	public static void main(String[] args) {
		ConfigUtil.setLog4jConfig("example1-log4j2.xml");
		// Logger initialization should be done after setting the log4j2 configuration
		LOG = LogManager.getLogger();
		threadContextMapExample();
		threadContextStackExample();
	}
	
	private static void threadContextMapExample() {
		ThreadContext.put("USER", "user-ala");
		ThreadContext.put("EXAMPLE", "example1");
		LOG.info("This is an example of thread context map");
		ThreadContext.clearMap();
	}

	private static void threadContextStackExample() {
		ThreadContext.push("user-ala");
		ThreadContext.push("example2");
		LOG.info("This is an example of thread context stack");
		ThreadContext.clearStack();
	}

}
