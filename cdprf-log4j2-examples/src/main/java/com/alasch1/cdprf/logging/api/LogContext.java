package com.alasch1.cdprf.logging.api;

import java.util.Map;

/**
 * The API for logging context control
 * 
 * @author aschneider
 *
 */
public interface LogContext {
	
	/**
	 * Initializes logger Thread Context with the default values
	 */
	void init(AppContextValues appContextValues);
	
	/**
	 * Initializes logger Thread Context with the default values and additional tags
	 * 
	 * @param addContextTags
	 */
	void init(AppContextValues appContextValues, Map<String, String> addContextTags);
	
	/**
	 * Clears logger Thread Context  
	 */
	void clear();
	
	/**
	 * Validates that a context was initialized properly
	 * @return
	 */
	boolean isValid();
}
