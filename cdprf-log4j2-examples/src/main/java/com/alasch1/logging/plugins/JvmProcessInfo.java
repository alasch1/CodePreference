package com.alasch1.logging.plugins;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * A singleton implements getting of JVM process ID
 * Is implemented with lazy initialization
 * 
 * @author aschneider
 *
 */
public final class JvmProcessInfo {
	
	public static class ProcessInfo {
		public long pid;
		public String name;
		public int threadCount;
		
		@Override
		public String toString() {
			return String.format("PID: %s,\tProcess name: %s,\tActive threads: %s", pid, name, threadCount);
		}
	}
	
	private ProcessInfo processInfo = new ProcessInfo();
	
	private ProcessInfo getProcessInfo() {
		processInfo.threadCount = ManagementFactory.getThreadMXBean().getThreadCount();
		return processInfo;
	}
	
	public static ProcessInfo get() {
		return JvmProcessIDLoader.getInstance().getProcessInfo();
	}
	
	private JvmProcessInfo() {
		resolveProcessInfo();
	}
	
	private void resolveProcessInfo() {
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		processInfo.name = runtimeBean.getName();
		processInfo.pid = Long.valueOf(processInfo.name.split("@")[0]);
	}
	
	/**
	 * Implements lazy initialization of JvmProcessID
	 *
	 */
	private static class JvmProcessIDLoader {
		static final JvmProcessInfo INSTANCE = new JvmProcessInfo();
		
		static JvmProcessInfo getInstance() {
			return INSTANCE; 
		}
	}
	
}
