package com.alasch1.logging.plugins;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.lookup.StrLookup;

/**
 * Base class for info lookup plugin
 * The concrete class should has the plugin annotation
 * with argument name assigned with the particular plugin identifier and category "Lookup"
 * 
 * @author as390x
 *
 */
public abstract class InfoLookupBase implements StrLookup {

	protected static final String TAB = "\t";
	protected static final String END_LINE = "\n";
	protected static final String VAL_STD_FORMAT = "\t%s: %s";

	@Override
	public String lookup(String key) {
		return getInfo();
	}

	@Override
	public String lookup(LogEvent event, String key) {
		return getInfo();
	}
	
	abstract protected String getInfo();
}
