package com.alasch1.logging.mocks;

import org.apache.commons.configuration.Configuration;

import com.alasch1.cdprf.commons.configuration.api.AppConfiguration;
import com.alasch1.logging.plugins.ConfigurationLookup;

public class AppConfigurationMock implements AppConfiguration {
	
	private String DUMMY_CFG = "prop1=one \npro2=two \n";
	private String signature;

	public AppConfigurationMock() {
		init();
		ConfigurationLookup.setConfiguration(this);
	}
	
	@Override
	public void init() {
		signature = "Initial signature";
	}
	
	@Override
	public Configuration getConfiguration() {
		return null;
	}

	@Override
	public String getConfigDirSystemProperty() {
		return null;
	}

	@Override
	public String getConfigFileName() {
		return null;
	}
	
	public String getSignature() {
		return signature;
	}	
	
	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return DUMMY_CFG + "signature=" + signature;
	}


}
