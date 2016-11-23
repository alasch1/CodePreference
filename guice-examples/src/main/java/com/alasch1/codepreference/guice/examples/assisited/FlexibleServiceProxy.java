package com.alasch1.codepreference.guice.examples.assisited;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.google.inject.assistedinject.Assisted;

public class FlexibleServiceProxy implements ServiceProxy {

	private NamedServiceProvider service;
	
	@Inject 
	public FlexibleServiceProxy(Injector injector, @Assisted String serviceName) {
		System.out.println("serviceName:" + serviceName);
		service = injector.getInstance(Key.get(NamedServiceProvider.class, Names.named(serviceName)));
		System.out.println(service.getName());
	}
	
	@Override
	public NamedServiceProvider getService() {
		return service;
	}
}
