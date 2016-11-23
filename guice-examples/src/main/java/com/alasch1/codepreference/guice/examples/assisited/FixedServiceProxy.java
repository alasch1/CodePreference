package com.alasch1.codepreference.guice.examples.assisited;

import javax.inject.Inject;
import javax.inject.Named;

public class FixedServiceProxy implements ServiceProxy {

	private NamedServiceProvider service;
	
	@Inject 
	public FixedServiceProxy(@Named("serviceA")NamedServiceProvider service) {
		this.service = service;
	}

	@Override
	public NamedServiceProvider getService() {
		return service;
	}
}
