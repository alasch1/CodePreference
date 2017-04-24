package com.alasch1.logging.plugins;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;


public class TestLookups extends TestLookupBase {

	private static final String CPUINFO_LOOKUP = "cpuinfo";
	private static final String OSINFO_LOOKUP = "osinfo";
	private static final String MEMINFO_LOOKUP = "meminfo";
	private static final String DISKINFO_LOOKUP = "diskinfo";
	private static final String GCINFO_LOOKUP = "gcinfo";
	private static final String NETWINFO_LOOKUP = "netwinfo";
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCpuInfoLookupOk() {
		VerbalTestExecutor.executeTest("testCpuInfoLookupOk", () -> {
			String lookupValue = getLookup(CPUINFO_LOOKUP, CpuInfoLookup.class);
			assertFalse("CpuInfoLookup is empty!!", lookupValue.isEmpty());
			assertTrue(lookupValue.indexOf(CpuInfoLookup.CPU_INFO_TITLE) != -1);
		});
	}

	@Test
	public void testMemoryInfoLookupOk() {
		VerbalTestExecutor.executeTest("testMemoryInfoLookupOk", () -> {
			String lookupValue = getLookup(MEMINFO_LOOKUP, MemoryInfoLookup.class);
			assertFalse("MemoryInfoLookup is empty!!", lookupValue.isEmpty());
			assertTrue(lookupValue.indexOf(MemoryInfoLookup.MEM_INFO_TITLE) != -1);
		});
	}

	@Test
	public void testOsInfoLookupOk() {
		VerbalTestExecutor.executeTest("testOsInfoLookupOk", () -> {
			String lookupValue = getLookup(OSINFO_LOOKUP, OsInfoLookup.class);
			assertFalse("OsInfoLookup is empty!!", lookupValue.isEmpty());
			assertTrue(lookupValue.indexOf(OsInfoLookup.OS_INFO_TITLE) != -1);
		});
	}

	@Test
	public void testDiskInfoLookupOk() {
		VerbalTestExecutor.executeTest("testDiskInfoLookupOk", () -> {
			String lookupValue = getLookup(DISKINFO_LOOKUP, DiskInfoLookup.class);
			assertFalse("DiskInfoLookup is empty!!", lookupValue.isEmpty());
			assertTrue(lookupValue.indexOf(DiskInfoLookup.DISK_INFO_TITLE) != -1);
		});
	}

	@Test
	public void testGCInfoLookupOk() {
		VerbalTestExecutor.executeTest("testGCInfoLookupOk", () -> {
			String lookupValue = getLookup(GCINFO_LOOKUP, GCInfoLookup.class);
			assertFalse("GCInfoLookup is empty!!", lookupValue.isEmpty());
			assertTrue(lookupValue.indexOf(GCInfoLookup.GC_INFO_TITLE) != -1);
		});
	}

	@Test
	public void testNetworkInfoLookupOk() {
		VerbalTestExecutor.executeTest("testNetworkInfoLookupOk", () -> {
			String lookupValue = getLookup(NETWINFO_LOOKUP, NetworkInfoLookup.class);
			assertFalse("NetworkInfoLookup is empty!!", lookupValue.isEmpty());
			assertTrue(lookupValue.indexOf(NetworkInfoLookup.NETWORK_INFO_TITLE) != -1);
		});
	}
}
