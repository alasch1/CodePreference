package com.alasch1.codepreference.guice.examples.basic;

public class ServiceBean implements ServiceProvider {

	public ServiceBean() {		
	}
	
	@Override
	public void useMe() {
		System.out.println("ServiceBean instance : " + this);			
	}
	
}