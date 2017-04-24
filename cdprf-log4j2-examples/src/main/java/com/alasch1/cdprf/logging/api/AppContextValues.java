package com.alasch1.cdprf.logging.api;

import org.apache.logging.log4j.util.Strings;

/**
 * Encapsulates the standard log context values, which should be 
 * provided by the application
 * 
 * @author ala schneider
 *
 */
public class AppContextValues {

	public static final String ANONYMOUS_USER = "-";
	public static enum InOutDirection {
		IN, OUT, NA;
	}
	
	public String userId = Strings.EMPTY;
	public String source = Strings.EMPTY;
	public String destination = Strings.EMPTY;
	public String operation = Strings.EMPTY;
	public InOutDirection direction = InOutDirection.NA;
	
	public AppContextValues() {
	}
	
	public AppContextValues(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return String.format("AppContextValues [source=%s, destination=%s, operation=%s, direction=%s, userId=%s]",
				source, destination, operation, direction, userId);
	}
}
