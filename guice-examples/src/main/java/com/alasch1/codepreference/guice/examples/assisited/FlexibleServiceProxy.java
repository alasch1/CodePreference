package com.alasch1.codepreference.guice.examples.assisited;

import javax.inject.Inject;

import com.alasch1.codepreference.guice.examples.DIContainer;
import com.alasch1.codepreference.guice.examples.basic.ServiceProvider;
import com.google.inject.Key;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Names;

public class FlexibleServiceProxy implements ServiceProxy {

	private NamedServiceProvider otherService;
	private ServiceProvider injectedService;
	
	@Inject 
	public FlexibleServiceProxy(ServiceProvider injectedService, @Assisted String otherServiceName) {
		System.out.println("serviceName:" + otherServiceName);
		this.injectedService = injectedService;
		otherService = DIContainer.getInjector().getInstance(Key.get(NamedServiceProvider.class, Names.named(otherServiceName)));
		System.out.println(otherService.getName());
	}
	
	@Override
	public NamedServiceProvider getNamedService() {
		return otherService;
	}

	public ServiceProvider getInjectedService() {
		return injectedService;
	}

}
