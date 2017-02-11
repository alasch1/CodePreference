package com.alasch1.logging.plugins;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alasch1.testutils.ConfigUtil;

public class TestNullAppender {

	static Logger LOG;

	@BeforeClass
	public static void setLog4jConfiguration() {
		ConfigUtil.setLog4jConfig("nullAppender-log4j2.xml");
		LOG = LogManager.getLogger();
	}
	
	@Test
	public void test() {
		LOG.info("Nothing to test - "
				+ "just shows how to set the different log4j2"
				+ " configuration in tests");
	}

}
