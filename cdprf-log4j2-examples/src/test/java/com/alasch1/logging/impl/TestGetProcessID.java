package com.alasch1.logging.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;
import com.alasch1.logging.plugins.JvmProcessInfo;
import com.alasch1.logging.plugins.JvmProcessInfo.ProcessInfo;

public class TestGetProcessID {

	@Test
	public void testProcessIdAssigned() {
		ProcessInfo processInfo = JvmProcessInfo.get();
		assertTrue("Process id is not assigned", processInfo.pid > 0);
	}

	@Test
	public void testProcessIdSameValue() {
		VerbalTestExecutor.executeTest("testProcessIdSameValue", () -> {
			ProcessInfo processInfo1 = JvmProcessInfo.get();
			ProcessInfo processInfo2 = JvmProcessInfo.get();
			assertTrue("Different values of process id ", processInfo1.pid == processInfo2.pid);
		});
	}

}
