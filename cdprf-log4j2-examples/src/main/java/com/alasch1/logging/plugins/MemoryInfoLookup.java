package com.alasch1.logging.plugins;

import org.apache.logging.log4j.core.config.plugins.Plugin;

/**
 * Implements format lookup ${meminfo:all} for PatternLayout
 * Interpolates the lookup into runtime memory info
 * 
 * @author ala schneider
 *
 */
@Plugin(name = "meminfo", category = "Lookup")
public class MemoryInfoLookup extends InfoLookupBase {

	public static final String MEM_INFO_TITLE = "Memory info";
	
	private static final String MACHINE_TOTAL_USAGE = "Machine total(bytes)";
	private static final String JVM_USAGE = "JVM total memory(bytes)";
	private static final String JVM_FREE = "JVM free memory(bytes)";
	private static final String NUM_VALUE_FMT = "\t%s: %,1d";
	private static final String COMMA = ",";
	
	@Override
	protected String getInfo() {
		return String.format("%s: %s", MEM_INFO_TITLE, memoryUsage());
	}
	
	private String memoryUsage() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(END_LINE)
			.append(String.format(NUM_VALUE_FMT, MACHINE_TOTAL_USAGE, Runtime.getRuntime().maxMemory())).append(COMMA)
			.append(String.format(NUM_VALUE_FMT, JVM_USAGE, Runtime.getRuntime().totalMemory())).append(COMMA)
			.append(String.format(NUM_VALUE_FMT, JVM_FREE, Runtime.getRuntime().freeMemory())).append(END_LINE);
		return sb.toString();
	}

}
