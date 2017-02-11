package com.alasch1.cdprf.guice.basic;

import javax.inject.Inject;

public class MemberInjectionClient {
	@Inject
	private ServiceProvider service;
	@Inject
	private EagerSingletonBean eagerSingelton;
	@Inject
	private LazySingletonBean lazySingelton;
	
	public MemberInjectionClient() {
	}
	
	public void use() {
		service.useMe();
		eagerSingelton.useMe();
		lazySingelton.useMe();
	}
}
