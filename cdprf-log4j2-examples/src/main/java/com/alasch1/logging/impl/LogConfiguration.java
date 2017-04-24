package com.alasch1.logging.impl;

import com.alasch1.cdprf.commons.configuration.api.AppConfiguration;
import com.alasch1.cdprf.commons.configuration.api.AppPropertiesConfiguration;

/**
 * Implements logging configuration
 * 
 * @author aschneider
 *
 */
public final class LogConfiguration extends AppPropertiesConfiguration {

	private static final String CONFIG_FILE_NAME = "contextTags.properties";
	
	// Properties KEYS and default values
	private static final String TAG = "tag";
	private static final int INVOCATION_TAG_NUMBER = 1;
	private static final int OPERATION_TAG_NUMBER = 2;
	private static final int USER_ID_TAG_NUMBER = 3;
	
	private AppConfiguration appConfiguration;

	public LogConfiguration() {		
	}
	
	public LogConfiguration(AppConfiguration appConfiguration) {
		setAppConfiguration(appConfiguration);
	}

	@Override
	public String getConfigFileName() {
		return CONFIG_FILE_NAME;
	}
	
	public AppConfiguration getAppConfiguration() {
		return appConfiguration;
	}

	public void setAppConfiguration(AppConfiguration appConfiguration) {
		this.appConfiguration = appConfiguration;
	}

	public String getInvocationTag() {
		return getTag(INVOCATION_TAG_NUMBER);
	}	
	
	public String getOperationTag() {
		return getTag(OPERATION_TAG_NUMBER);
	}
	
	public String getUserIdTag() {
		return getTag(USER_ID_TAG_NUMBER);
	}
	
	private String getTag(int tagNumber) {
		return getConfiguration().getString(TAG + tagNumber);
	}
}
