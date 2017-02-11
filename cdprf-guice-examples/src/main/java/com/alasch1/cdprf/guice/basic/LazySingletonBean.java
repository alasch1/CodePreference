package com.alasch1.cdprf.guice.basic;

import com.google.inject.Singleton;

@Singleton
public class LazySingletonBean {
	
	public void useMe() {
		System.out.println("LazySingletonBean instance : " + this);			
	}
	
}
