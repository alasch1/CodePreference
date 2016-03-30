package com.alasch1.codepreference.commons.configuration.api;

import org.apache.commons.configuration.Configuration;

/**
 * Defines the application configuration Api
 * 
 * @author aschneider
 *
 */
public interface AppConfiguration {
	
	void init();
	
	/**
	 * Returns the underlying configuration
	 * @return
	 */
	Configuration getConfiguration();
	
	/**
	 * Returns the JVM property name for config.dir.
	 * This property will be used for loading of a configuration from file (if exists)
	 * 
	 * @return
	 */
	String getConfigDirSystemProperty();
	
	/**
	 * Returns configuration file name, if exists
	 * 
	 * @return
	 */
	String getConfigFileName();
}
