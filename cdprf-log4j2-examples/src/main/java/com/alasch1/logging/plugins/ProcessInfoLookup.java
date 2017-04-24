package com.alasch1.logging.plugins;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

/**
 * Implements format lookup ${pinfo} for PatternLayout
 * Interpolates the lookup into process ID
 * 
 * @author as390x
 *
 */
@Plugin(name = "pinfo", category = "Lookup")
public class ProcessInfoLookup implements StrLookup {
	
	public static final String PROCESS_INFO_TITLE = "Process info";

	@Override
	public String lookup(String key) {
		return processInfoToString();
	}

	@Override
	public String lookup(LogEvent event, String key) {
		return processInfoToString();
	}
	
	private String processInfoToString() {
		return String.format("%s:%n\t%s%n", PROCESS_INFO_TITLE, JvmProcessInfo.get());
	}
	
}
