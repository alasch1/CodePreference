package com.alasch1.logging.plugins;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

import com.alasch1.configuration.api.AppConfiguration;

/**
 * Implements format lookup ${config} for PatternLayout
 * Interpolates the lookup into configuration toString method
 * 
 * @author aschneider
 *
 */
@Plugin(name = "config", category = "Lookup")
public class ConfigurationLookup implements StrLookup {

	private static final String NULL_CONFIGURATUION="";

	private static AppConfiguration configuration;
	
	public static void setConfiguration(AppConfiguration configuration) {
		ConfigurationLookup.configuration = configuration;
	}
	
	@Override
	public String lookup(String key) {
		return getConfig();
	}

	@Override
	public String lookup(LogEvent event, String key) {
		return getConfig();
	}

	public String getConfig() {
		if (null != configuration) {
			return configuration.toString();
		}
		else {
			return NULL_CONFIGURATUION;
		}
	}
	
}
