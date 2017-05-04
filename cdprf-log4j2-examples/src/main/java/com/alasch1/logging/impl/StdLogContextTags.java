package com.alasch1.logging.impl;

import org.apache.logging.log4j.util.Strings;

import com.alasch1.cdprf.logging.api.LogContextTags;

/**
 * Encapsulates the standard log context values, which should be 
 * provided by the application
 * 
 * @author as390x
 *
 */
public class StdLogContextTags implements LogContextTags {
	
	public static final String ANONYMOUS_USER = "-";
	public static enum InOutDirection {
		IN, OUT, NA;
	}
	
	public String left = Strings.EMPTY;
	public String right = Strings.EMPTY;
	public String operation = Strings.EMPTY;
	public InOutDirection direction = InOutDirection.NA;
	public String userId = ANONYMOUS_USER;
	
	public StdLogContextTags() {
	}
	
	public StdLogContextTags(String userId) {
		if (null != userId) {
			this.userId = userId;
		}
	}

	@Override
	public String toString() {
		return String.format("StdLogContextTags [left=%s, right=%s, operation=%s, direction=%s, userId=%s]",
				left, right, operation, direction, userId);
	}
}