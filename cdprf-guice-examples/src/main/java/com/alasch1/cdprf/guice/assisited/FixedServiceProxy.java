package com.alasch1.cdprf.guice.assisited;

import javax.inject.Inject;
import javax.inject.Named;

public class FixedServiceProxy implements ServiceProxy {
	
	private NamedServiceProvider service;
	
	@Inject 
	public FixedServiceProxy(@Named(AppNames.SERVICE_A)NamedServiceProvider service) {
		this.service = service;
	}

	@Override
	public NamedServiceProvider getNamedService() {
		return service;
	}
}
