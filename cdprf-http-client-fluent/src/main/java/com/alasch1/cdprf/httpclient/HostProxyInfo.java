package com.alasch1.cdprf.httpclient;

import org.apache.logging.log4j.util.Strings;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class HostProxyInfo {
	
	public static final HostProxyInfo NO_PROXY = new HostProxyInfo();
	
	private String url = Strings.EMPTY;
	private int port = 0;
	
	public HostProxyInfo() {		
	}
	
	public HostProxyInfo(String url, int port) {	
		this.url = url;
		this.port = port;
	}
	
	public boolean isEmpty() {
		return Strings.isBlank(url) || port == 0;
	}

	@Override
	public String toString() {
		return String.format("[%s:%s]", url, port);
	}	
	
}
