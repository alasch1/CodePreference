package com.alasch1.cdprf.guice.basic;

import javax.inject.Inject;

public class ConstructorInjectionClient {
	private ServiceProvider service;
	private LazySingletonBean singelton;
	
	@Inject
	public ConstructorInjectionClient(ServiceProvider service,LazySingletonBean singelton) {
		this.service = service;
		this.singelton = singelton;
	}
	
	public void use() {
		service.useMe();
		singelton.useMe();
	}
}
