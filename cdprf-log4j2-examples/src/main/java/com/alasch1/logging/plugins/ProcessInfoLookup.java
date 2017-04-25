package com.alasch1.logging.plugins;

import org.apache.logging.log4j.core.config.plugins.Plugin;

/**
 * Implements format lookup ${pinfo:all} for PatternLayout
 * Interpolates the lookup into process ID
 * 
 * @author ala schneider
 *
 */
@Plugin(name = "pinfo", category = "Lookup")
public class ProcessInfoLookup extends InfoLookupBase {
	
	public static final String PROCESS_INFO_TITLE = "Process info:";

	@Override
	protected String getInfo() {
		StringBuilder sb = new StringBuilder(PROCESS_INFO_TITLE);
		sb.append(END_LINE)
			.append(String.format(VAL_STD_FORMAT, PROCESS_INFO_TITLE, JvmProcessInfo.get()))
			.append(END_LINE);
		return sb.toString();
	}
	
}
