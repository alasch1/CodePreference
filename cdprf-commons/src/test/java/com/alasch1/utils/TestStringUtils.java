package com.alasch1.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.junit.Test;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;
import com.alasch1.cdprf.commons.utils.StringUtils;

public class TestStringUtils {

	@Test
	public void testGetValidDomain() {
		VerbalTestExecutor.executeTest("testGetValidDomain", () -> {
			String email = "ala@com.com";
			String domain = StringUtils.getEmailDomain(email);
			System.out.println("Domain:" + domain);
			assertNotSame(email, domain);
		});		
	}
	
	@Test
	public void testGetMissingDomain() {
		VerbalTestExecutor.executeTest("testGetMissingDomain", () -> {
			String email = "ala.com.com";
			String domain = StringUtils.getEmailDomain(email);
			System.out.println("Domain:" + domain);
			assertEquals(email, domain);
		});		
	}
	
	@Test
	public void testUrlEncode() throws UnsupportedEncodingException {
		VerbalTestExecutor.executeTest("testUrlEncode", () -> {
			String [] urls4test = {"a b", "a^b", "("};
			for (int i=0; i<urls4test.length; i++) {
				String origUrl = urls4test[i];
				System.out.println("origUrl:" + origUrl);
				String encoded = StringUtils.encodeUrl(origUrl);
				System.out.println("encoded URL:" + encoded);
				String restored = URLDecoder.decode(encoded, "UTF-8");
				System.out.println("restored URL:" + restored);
				assertEquals(origUrl, restored);
			}
		});		
	}

	
	@Test
	public void testLocalTimeISO() {
		VerbalTestExecutor.executeTest("testLocalTimeISO", () -> {
			String localInNanos = StringUtils.localTimeISO(true);
			String localInSecs = StringUtils.localTimeISO(false);
			System.out.println("localInNanos:" + localInNanos);
			System.out.println("localInSecs:" + localInSecs);
			assertTrue(localInNanos.length() > localInSecs.length());
		});		
	}

}
