package com.alasch1.codepreference.commons.configuration.api;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * Implements application configuration, based on properties file
 * 
 * @author aschneider
 *
 */
public abstract class AppPropertiesConfiguration implements AppConfiguration {

	private static final String CONFIG_DIR_SYSTEM_PROPERTY = "config.dir";
	private static final String SEPARATOR = "\n+++++++++++++++++++ Configuration ++++++++++++++++++\n";	

	private PropertiesConfiguration configuration;
	
	public AppPropertiesConfiguration() {
	}

	@Override
	public void init() {
		try {
			configuration = new PropertiesConfiguration(buildConfigPath());
			configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
			configuration.setAutoSave(true);
		} 
		catch (ConfigurationException e) {
			System.err.println("Failed to initilize configuration file "+ getConfigFileName());
			throw new ConfigurationError(e);
		}		
	}

	@Override
	public String getConfigDirSystemProperty() {
		return CONFIG_DIR_SYSTEM_PROPERTY;
	}

	@Override
	public PropertiesConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public String toString() {
		
		StringBuilder configString = new StringBuilder(SEPARATOR);
		configString.append(String.format("%nconfig.file:%s%n", buildConfigPath()));
		Iterator<String> propKeysItr = getConfiguration().getKeys();
		while (propKeysItr.hasNext()) {
			String key = propKeysItr.next();
			if (isPrintableProperty(key)) {
					configString.append(String.format("%s:%s;%n", key, getConfiguration().getString(key)));
			}
		}
		configString.append(SEPARATOR);
		return configString.toString();
	}
	
	public String buildConfigPath() {
		String configPath = System.getProperty(getConfigDirSystemProperty());
		if (configPath != null) {
			// System property config.dir is defined
			configPath += File.separator + getConfigFileName();
		}
		else {
			// Use embedded configuration file
			configPath = Thread.currentThread().getContextClassLoader().getResource(getConfigFileName()).getPath();
		}
		return configPath;
	}
	
	public boolean isPrintableProperty(String key) {
		// All properties are printable by default
		return true;
	}	
	
}
