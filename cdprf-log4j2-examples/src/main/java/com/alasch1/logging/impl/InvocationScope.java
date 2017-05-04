package com.alasch1.logging.impl;

/**
 * DTO for invocation scope
 * 
 * @author as390x
 *
 */
public class InvocationScope {	
	public String source;
	public String destination;
	public String operation;
	
	public InvocationScope(String source, String destination) {
		this.source = source;
		this.destination = destination;
	}
	
	public InvocationScope(String source, String destination, String operation) {
		this(source, destination);
		this.operation = operation;
	}
	
}