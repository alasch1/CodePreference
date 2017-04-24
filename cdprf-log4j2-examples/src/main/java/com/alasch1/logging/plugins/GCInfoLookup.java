package com.alasch1.logging.plugins;

import java.lang.management.ManagementFactory;

import org.apache.logging.log4j.core.config.plugins.Plugin;

/**
 * Implements format lookup ${gcinfo} for PatternLayout
 * Interpolates the lookup into GC (garbage collection) info
 * 
 * @author as390x
 *
 */
@Plugin(name = "gcinfo", category = "Lookup")
public class GCInfoLookup extends InfoLookupBase {

	public static final String GC_INFO_TITLE = "GC info";

	private static final String TOTAL_GC = "Total Garbage Collections";
	private static final String TOTAL_GC_TIME = "Total Garbage Collection Time (ms)";
	private static final String AVE_TIME = "Average time per collection (ms)";
	
	private long totalGarbageCollections = 0;
	private long garbageCollectionTime = 0;
	
	@Override
	protected String getInfo() {
		StringBuilder sb = new StringBuilder(GC_INFO_TITLE);
		
		ManagementFactory.getGarbageCollectorMXBeans().stream()
		.forEach(gc->{
			long count = gc.getCollectionCount();
			if (count >= 0) {
				totalGarbageCollections += count;
			}

			long time = gc.getCollectionTime();
			if (time >= 0) {
				garbageCollectionTime += time;
			}
		});
		
		sb.append(END_LINE)
		.append(String.format(VAL_STD_FORMAT,TOTAL_GC, totalGarbageCollections))
		.append(String.format(VAL_STD_FORMAT,TOTAL_GC_TIME, garbageCollectionTime));
		
		if (totalGarbageCollections != 0) {
			sb.append(String.format(VAL_STD_FORMAT, AVE_TIME, garbageCollectionTime/totalGarbageCollections));
		}
		sb.append(END_LINE);
		
//		sb.append(String.format("%n\t%s: %s\t%s: %s\t%s: %s%n", 
//				// The total number of collections that have occurred
//				TOTAL_GC, totalGarbageCollections,
//				// The approximate accumulated collection elapsed time in milliseconds
//				TOTAL_GC_TIME, garbageCollectionTime,
//				// An estimate for how log takes each collection
//				AVE_TIME, totalGarbageCollections != 0?
//					garbageCollectionTime/totalGarbageCollections:Strings.EMPTY));
		return sb.toString();
	}

}
