package com.alasch1.logging.plugins;

import java.io.File;

import org.apache.logging.log4j.core.config.plugins.Plugin;

/**
 * Implements format lookup ${diskinfo} for PatternLayout
 * Interpolates the lookup into disk info
 * 
 * @author as390x
 *
 */
@Plugin(name = "diskinfo", category = "Lookup")
public class DiskInfoLookup extends InfoLookupBase {

	public static final String DISK_INFO_TITLE = "Disk info";

	private static final String ROOT = "root";
	private static final String TOTAL_SPACE_BYTES = "Total space (bytes)";
	private static final String FREE_SPACE_BYTES = "Free space (bytes)";
	private static final String FREE_SPACE_PERCENTS = "Free space (%%)";

	@Override
	protected String getInfo() {
		StringBuilder sb = new StringBuilder(DISK_INFO_TITLE);

		/* Get a list of all files ystem roots on this system */
		File[] roots = File.listRoots();
		sb.append(END_LINE)
		.append(TAB).append(ROOT)
		.append(TAB).append(TOTAL_SPACE_BYTES)
		.append(TAB).append(TAB).append(FREE_SPACE_BYTES)
		.append(TAB).append(TAB).append(FREE_SPACE_PERCENTS)
		.append(END_LINE);
		
		/* For each filesystem root, print some info */
		for (File root : roots) {
			sb.append("\t");
			sb.append(root.getAbsolutePath());
			sb.append(String.format("\t%,20d\t\t%,18d\t%,15.2f%n",
					root.getTotalSpace(), 
					root.getFreeSpace(),
					(root.getTotalSpace() > 0) ? ((double)root.getFreeSpace()/(double)root.getTotalSpace())*100 : 0));
		}

		return sb.toString();
		
	}

}
