package com.alasch1.codepreference.guice.examples.assisited;

public class NamedServiceA implements NamedServiceProvider {
	
	@Override
	public String getName() {
		return this +":ServiceA";			
	}    	
}