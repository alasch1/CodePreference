package com.alasch1.logging.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.alasch1.testutils.VerbalTestExecutor;

public class TestGetProcessID {

	@Test
	public void testProcessIdAssigned() {
		long processId = JvmProcessID.get();
		assertTrue("Process id is not assigned", processId > 0);
	}

	@Test
	public void testProcessIdSameValue() {
		VerbalTestExecutor.executeTest("testProcessIdSameValue", () -> {
			long processId1 = JvmProcessID.get();
			long processId2 = JvmProcessID.get();
			assertTrue("Different values of process id ", processId1 == processId2);
		});
	}

}
