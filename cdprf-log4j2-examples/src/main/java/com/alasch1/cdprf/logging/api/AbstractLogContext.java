package com.alasch1.cdprf.logging.api;

import java.util.Map;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.util.Strings;

/**
 * API for log context control
 * 
 * @author ala schneider
 *
 */
public abstract class AbstractLogContext<T extends LogContextTags> {
	
	/**
	 * Initializes logger Thread Context with the given tag values values
	 * 
	 * @param contextTags
	 */
	abstract public void init(T contextTags);
	
	/**
	 * Initializes logger Thread Context with the given tags and additional tags
	 * 
	 * @param contextTags
	 * @param addContextTags
	 */
	abstract public void init(T contextTags, Map<String, String> addContextTags);
	
	/**
	 * Clears logger Thread Context  
	 */
	public static void clear() {
		ThreadContext.clearAll();
	}
	
	/**
	 * Validates that a context was initialized properly
	 * @return
	 */
	public static boolean isValid() {
		return !ThreadContext.isEmpty();
	}
	
	/**
	 * Adds a new tag to the log context 
	 * 
	 * @param key
	 * @param value
	 */
	public static void addTag(String key, String value) {
		if (Strings.isNotBlank(value)) {
			ThreadContext.put(key, value);
		}
	}
	
	/**
	 * Removes a specified (by key) tag form the current log context
	 * 
	 * @param key
	 */
	public static void removeTag(String key) {
    	ThreadContext.remove(key);
	}
	
	/**
	 * Returns a specified (by key) tag form the current log context or null
	 * if such tag does not exists
	 * 
	 * @param key
	 * @return
	 */
	public static String getTag(String key) {
		return ThreadContext.get(key);
	}
	
}
