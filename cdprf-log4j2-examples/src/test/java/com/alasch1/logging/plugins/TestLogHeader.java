package com.alasch1.logging.plugins;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;
import com.alasch1.cdprf.commons.utils.ConfigUtil;
import com.alasch1.logging.mocks.AppConfigurationMock;
import com.alasch1.logging.plugins.JvmProcessInfo.ProcessInfo;
import com.alasch1.logging.testutils.Log4jHelper;
import com.alasch1.logging.testutils.StringAppender4Tests;

public class TestLogHeader {

	private static final String STD_HEADER_FORMAT = 
			"Version: ${bundle:contextTags:version}${sys:line.separator}${osinfo:all}${cpuinfo:all}${meminfo:all}${pinfo:all}${diskinfo:all}${gcinfo:all}${netwinfo:all}"
					+ "${config:all}";
	
	private AppConfigurationMock configStub;

	@Before
	public void setup() {
		initConfiguration();
	}

	@Test
	public void testHeaderHasVersionLookup() {
		VerbalTestExecutor.executeTest("testHeaderHasVersionLookup", () -> {
			String header = Log4jHelper.interpolate(STD_HEADER_FORMAT);

			final String layoutPattern = "%m%n";
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.append((StringAppender4Tests appender) ->{
					logger.info("Test");
					System.out.println("Appender buffer:\n" + appender.getOutput());
					assertTrue("Version info not found in message", appender.getOutput().indexOf("Version") != -1);
			}, 
			logger, layoutPattern, header);		
		});
	}

	@Test
	public void testHeaderHasOSInfoLookup() {
		VerbalTestExecutor.executeTest("testHeaderHasOSInfoLookup", () -> {
			String header = Log4jHelper.interpolate(STD_HEADER_FORMAT);

			final String layoutPattern = "%m%n";
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.append((StringAppender4Tests appender) ->{
					logger.info("Test");
					System.out.println("Appender buffer:\n" + appender.getOutput());
					assertTrue("OS info not found in message", appender.getOutput().indexOf(OsInfoLookup.OS_INFO_TITLE) != -1);
			}, 
			logger, layoutPattern, header);		
		});
	}

	@Test
	public void testHeaderHasCpuInfoLookup() {
		VerbalTestExecutor.executeTest("testHeaderHasCpuInfoLookup", () -> {
			String header = Log4jHelper.interpolate(STD_HEADER_FORMAT);

			final String layoutPattern = "%m%n";
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.append((StringAppender4Tests appender) ->{
					logger.info("Test");
					System.out.println("Appender buffer:\n" + appender.getOutput());
					assertTrue("CPU info not found in message", appender.getOutput().indexOf(CpuInfoLookup.CPU_INFO_TITLE) != -1);
			}, 
			logger, layoutPattern, header);		
		});
	}

	@Test
	public void testHeaderHasMemoryInfoLookup() {
		VerbalTestExecutor.executeTest("testHeaderHasMemoryInfoLookup", () -> {
			String header = Log4jHelper.interpolate(STD_HEADER_FORMAT);

			final String layoutPattern = "%m%n";
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.append((StringAppender4Tests appender) ->{
					logger.info("Test");
					System.out.println("Appender buffer:\n" + appender.getOutput());
					assertTrue("Memory info not found in message", appender.getOutput().indexOf(MemoryInfoLookup.MEM_INFO_TITLE) != -1);
			}, 
			logger, layoutPattern, header);		
		});
	}

	@Test
	public void testHeaderHasPidLookup() {
		VerbalTestExecutor.executeTest("testHeaderHasPidLookup", () -> {
			String header = Log4jHelper.interpolate(STD_HEADER_FORMAT);

			final String layoutPattern = "%m%n";
			final Logger logger = LogManager.getRootLogger();
			ProcessInfo jvmInfo = JvmProcessInfo.get();

			StringAppender4Tests.append((StringAppender4Tests appender) ->{
					logger.info("Test");
					System.out.println("Appender buffer:\n" + appender.getOutput());
					assertTrue("JVM info not found in message", appender.getOutput().indexOf(jvmInfo.toString()) != -1);
			}, 
			logger, layoutPattern, header);		
		});
	}

	@Test
	public void testHeaderHasDiskInfoLookup() {
		VerbalTestExecutor.executeTest("testHeaderHasDiskInfoLookup", () -> {
			String header = Log4jHelper.interpolate(STD_HEADER_FORMAT);

			final String layoutPattern = "%m%n";
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.append((StringAppender4Tests appender) ->{
					logger.info("Test");
					System.out.println("Appender buffer:\n" + appender.getOutput());
					assertTrue("Disk info not found in message", appender.getOutput().indexOf(DiskInfoLookup.DISK_INFO_TITLE) != -1);
			}, 
			logger, layoutPattern, header);		
		});
	}

	@Test
	public void testHeaderHasGCInfoLookup() {
		VerbalTestExecutor.executeTest("testHeaderHasGCInfoLookup", () -> {
			String header = Log4jHelper.interpolate(STD_HEADER_FORMAT);

			final String layoutPattern = "%m%n";
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.append((StringAppender4Tests appender) ->{
					logger.info("Test");
					System.out.println("Appender buffer:\n" + appender.getOutput());
					assertTrue("GC info not found in message", appender.getOutput().indexOf(GCInfoLookup.GC_INFO_TITLE) != -1);
			}, 
			logger, layoutPattern, header);		
		});
	}

	@Test
	public void testHeaderHasNetworkInfoLookup() {
		VerbalTestExecutor.executeTest("testHeaderHasNetworkInfoLookup", () -> {
			String header = Log4jHelper.interpolate(STD_HEADER_FORMAT);

			final String layoutPattern = "%m%n";
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.append((StringAppender4Tests appender) ->{
					logger.info("Test");
					System.out.println("Appender buffer:\n" + appender.getOutput());
					assertTrue("Network info not found in message", appender.getOutput().indexOf(NetworkInfoLookup.NETWORK_INFO_TITLE) != -1);
			}, 
			logger, layoutPattern, header);		
		});
	}

	@Test
	public void testHeaderCfgLookupSubstitutor() {
		VerbalTestExecutor.executeTest("testHeaderCfgLookupSubstitutor", () -> {
			String header = Log4jHelper.interpolate(STD_HEADER_FORMAT);

			final String layoutPattern = "%m%n";
			final Logger logger = LogManager.getRootLogger();

			StringAppender4Tests.append((StringAppender4Tests appender) ->{
					logger.info("Test");
					System.out.println("Appender buffer:\n" + appender.getOutput());
					assertTrue("Configuration not found in message", appender.getOutput().indexOf(firstNotEmptyLine(configStub.toString())) != -1);
			}, 
			logger, layoutPattern, header);		
		});
	}
	
	private void initConfiguration() {
		configStub = new AppConfigurationMock();
	}
	
	private String firstNotEmptyLine(String source) {
		final String emptySource = "Failed to read configuration";
		String[] lines = source.split("\n");
		System.out.println("lines[0]:" + lines[0]);
		for (String line: Arrays.asList(lines)) {
			if (Strings.isNotBlank(line)) {
				return line;
			}
		}
		return emptySource;
	}

}
