package com.alasch1.logging.impl;

import com.alasch1.codepreference.commons.configuration.api.AppConfiguration;
import com.alasch1.codepreference.commons.configuration.api.AppPropertiesConfiguration;

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
	private static final int PROCESS_ID_TAG_NUMBER = 1;
	private static final int THREAD_ID_TAG_NUMBER = 2;
	private static final int USER_ID_TAG_NUMBER = 3;
	private static final int CONFERENCE_ID_TAG_NUMBER = 4;
	
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

	public String getConferenceIdTag() {
		return getTag(CONFERENCE_ID_TAG_NUMBER);
	}
	
	public String getUserIdTag() {
		return getTag(USER_ID_TAG_NUMBER);
	}
	
	public String getProcessIdTag() {
		return getTag(PROCESS_ID_TAG_NUMBER);
	}
	
	public String getThreadIdTag() {
		return getTag(THREAD_ID_TAG_NUMBER);
	}
	
	private String getTag(int tagNumber) {
		return getConfiguration().getString(TAG + tagNumber);
	}
}
