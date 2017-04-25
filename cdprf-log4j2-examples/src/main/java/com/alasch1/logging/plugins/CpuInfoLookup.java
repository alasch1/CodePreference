package com.alasch1.logging.plugins;

import java.lang.management.ManagementFactory;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.status.StatusLogger;

/**
 * Implements format lookup ${cpuinfo:all} for PatternLayout
 * Interpolates the lookup into cpu info
 * 
 * @author ala schneider
 *
 */
@Plugin(name = "cpuinfo", category = "Lookup")
public class CpuInfoLookup extends InfoLookupBase {
	
	public static final String CPU_INFO_TITLE = "Cpu info";
	
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final Marker LOOKUP = MarkerManager.getMarker("LOOKUP");
	
    private static final String SYSTEM_CPU_LOAD = "System Cpu Load";
    private  static final String PROCESS_CPU_LOAD = "Process Cpu Load";
    private  static final String AVAILABLE_PROCESSORS = "Available processors(cores)";
    
	private static final String SYSTEM_CPU_LOAD_ATTR = "SystemCpuLoad";
	private  static final String PROCESS_CPU_LOAD_ATTR = "ProcessCpuLoad";
	
	@Override
	protected String getInfo() {
		return String.format("%s:%n%s%s", CPU_INFO_TITLE, availableProcessorsInfo(), cpuLoadInfo());
	}

	private String availableProcessorsInfo() {
		return String.format(VAL_STD_FORMAT, AVAILABLE_PROCESSORS, Runtime.getRuntime().availableProcessors());
	}
	
	private String cpuLoadInfo() {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name;
		try {
			StringBuilder sb = new StringBuilder();
			name = ObjectName.getInstance("java.lang:type=OperatingSystem");
			AttributeList list = mbs.getAttributes(name, new String[] { SYSTEM_CPU_LOAD_ATTR, PROCESS_CPU_LOAD_ATTR });
			if (!list.isEmpty()) {
				sb.append(String.format(VAL_STD_FORMAT, SYSTEM_CPU_LOAD, getComponentValue((Attribute) list.get(0), SYSTEM_CPU_LOAD_ATTR)));
				sb.append(String.format(VAL_STD_FORMAT, PROCESS_CPU_LOAD, getComponentValue((Attribute) list.get(1),PROCESS_CPU_LOAD_ATTR)));
			}
			sb.append(END_LINE);	
			return sb.toString();
		} 
		catch (Exception e) {
            LOGGER.warn(LOOKUP, "Error while getting cpu lod info.", e);
            return null;
		}
	}
	
	private static double getComponentValue(Attribute att,String component) {
		Double value = (Double) att.getValue();
		// usually takes a couple of seconds before we get real values
		if (value == -1.0)
			return Double.NaN;
		else
			return ((int)(value * 1000) / 10.0); // returns a percentage value with 1 decimal point precision
	}

}
