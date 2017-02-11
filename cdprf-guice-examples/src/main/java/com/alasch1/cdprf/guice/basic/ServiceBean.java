package com.alasch1.cdprf.guice.basic;

public class ServiceBean implements ServiceProvider {

	public ServiceBean() {		
	}
	
	@Override
	public void useMe() {
		System.out.println("ServiceBean instance : " + this);			
	}
	
}