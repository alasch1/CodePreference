package com.alasch1.cdprf.guice.basic;

import com.google.inject.Singleton;

@Singleton
public class EagerSingletonBean {
	
	private static EagerSingletonBean instance;
	
	public EagerSingletonBean() {
		instance = this;
	}
	
	public void useMe() {
		System.out.println("EagerSingletonBean instance : " + this);			
	}
	
	public static void just4Test(String message) {
		if (instance !=null) {
			System.out.println(message);
			instance.useMe();
		}
		return;
	}
}
