package com.alasch1.logging.impl;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A singleton implements getting of JVM process ID
 * Is implemented with lazy initialization
 * 
 * @author aschneider
 *
 */
public final class JvmProcessID {
	
	private static final Logger LOG = LogManager.getLogger();
	private long processID;
	
	public static long get() {
		return JvmProcessIDLoader.getInstance().processID;
	}
	
	private JvmProcessID() {
		resolveProcessId();
	}
	
	private void resolveProcessId() {
		LOG.entry();
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		String jvmName = runtimeBean.getName();
		LOG.info("jvmName={}", jvmName);
		processID = Long.valueOf(jvmName.split("@")[0]);
		LOG.info("processID={}", processID);
	}
	
	/**
	 * Implements lazy initialization of JvmProcessID
	 *
	 */
	private static class JvmProcessIDLoader {
		static final JvmProcessID INSTANCE = new JvmProcessID();
		
		static JvmProcessID getInstance() {
			return INSTANCE; 
		}
	}
	
}
