package com.alasch1.cdprf.guice.assisited;

public class NamedServiceA implements NamedServiceProvider {
	
	@Override
	public String getName() {
		return this +":ServiceA";			
	}    	
}