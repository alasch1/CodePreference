package com.alasch1.logging.plugins;

import org.apache.logging.log4j.core.config.plugins.Plugin;

/**
 * Implements format lookup ${meminfo} for PatternLayout
 * Interpolates the lookup into runtime memory info
 * 
 * @author as390x
 *
 */
@Plugin(name = "meminfo", category = "Lookup")
public class MemoryInfoLookup extends InfoLookupBase {

	public static final String MEM_INFO_TITLE = "Memory info";
	
	private static final String MACHINE_TOTAL_USAGE = "Machine total(bytes)";
	private static final String JVM_USAGE = "JVM total memory(bytes)";
	private static final String JVM_FREE = "JVM free memory(bytes)";
	
	@Override
	protected String getInfo() {
		return String.format("%s: %s", MEM_INFO_TITLE, memoryUsage());
	}
	
	private String memoryUsage() {
		return String.format("%n\t%s: %,1d\t%s: %,1d\t%s: %,1d%n",
				MACHINE_TOTAL_USAGE, Runtime.getRuntime().maxMemory(), 
				JVM_USAGE, Runtime.getRuntime().totalMemory(),
				JVM_FREE, Runtime.getRuntime().freeMemory());
	}

}
