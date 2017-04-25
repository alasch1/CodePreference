package com.alasch1.logging.plugins;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.status.StatusLogger;

/**
 * Implements format lookup ${netwinfo:all} for PatternLayout
 * Interpolates the lookup into network info
 * 
 * @author ala schneider
 *
 */
@Plugin(name = "netwinfo", category = "Lookup")
public class NetworkInfoLookup extends InfoLookupBase {

	public static final String NETWORK_INFO_TITLE = "Network info";
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final Marker LOOKUP = MarkerManager.getMarker("LOOKUP");
	
	private static final String LOCAL_HOST_IP = "Localhost IP";
	private static final String DISPLAY_NAME = "Display name";
	private static final String NAME = "Name";
	private static final String HOST_IP = "Host IP";
	
	@Override
	protected String getInfo() {
		StringBuilder sb = new StringBuilder(NETWORK_INFO_TITLE);

		sb.append(END_LINE)
		.append(String.format(VAL_STD_FORMAT, LOCAL_HOST_IP, getHostAddress()))
		.append(getNetworkAdapters())
		.append(END_LINE);

		return sb.toString();
	}

	private String getNetworkAdapters() {
		StringBuilder sb = new StringBuilder();
		try {
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface v : Collections.list(nets)) {
				
				if (!v.isUp() || v.isVirtual() || v.isLoopback() || v.getHardwareAddress() == null)
					continue;
				Enumeration<InetAddress> inetAddresses = v.getInetAddresses();
				if(!inetAddresses.hasMoreElements())
					continue;
				
				sb.append(END_LINE)
				.append(String.format("\t%s: %-45s", DISPLAY_NAME, v.getDisplayName()))
				.append(String.format(VAL_STD_FORMAT, NAME, v.getName()))
				.append(String.format(VAL_STD_FORMAT, HOST_IP, getInetAddresses(inetAddresses)));
			}
			
		} 
		catch (Exception e) {
			sb.append(e.getMessage());
		}
		sb.append(END_LINE);
		return sb.toString();
	}
	
	private String getInetAddresses(Enumeration<InetAddress> inetAddresses) {
		StringBuilder sb = new StringBuilder();
		if(inetAddresses.hasMoreElements()) {
			for (InetAddress a:Collections.list(inetAddresses)) {
				String normalized = a.toString().replaceAll("%", "#").replaceAll("/", "");
				sb.append(normalized).append("\t");
			}
		}
		return sb.toString();		
	}
	
	private String getHostAddress() {
		try {
			return java.net.InetAddress.getLocalHost().getHostAddress();
		} 
		catch (Exception e) {
            LOGGER.warn(LOOKUP, "Failed to get host address");
			return "unknown host";
		}
	}

}
