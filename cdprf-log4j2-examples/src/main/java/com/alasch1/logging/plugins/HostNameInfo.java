package com.alasch1.logging.plugins;

import java.net.UnknownHostException;

import org.apache.logging.log4j.util.Strings;

public final class HostNameInfo {

	private String hostName = Strings.EMPTY;
	
	public static String get() {		
		return HostNameInfoLoader.getInstance().hostName;
	}
	
	private HostNameInfo() {
		initHostInfo();
	}
	
	private void initHostInfo() {
		try {
			java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
			hostName = localMachine.getHostName();
		} 
		catch (UnknownHostException e1) {
			hostName = "Unknown";
		}
	}

	private static class HostNameInfoLoader {
		static final HostNameInfo INSTANCE = new HostNameInfo();
		
		static HostNameInfo getInstance() {
			return INSTANCE;
		}
	}
}
