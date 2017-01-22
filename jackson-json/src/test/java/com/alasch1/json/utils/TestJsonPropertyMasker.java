package com.alasch1.json.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alasch1.json.masking.JsonPropertyMasker;

public class TestJsonPropertyMasker {
	
	private static final String PWD1="PWD1";
	private static final String PWD2="PWD2";	
	private static final String FNAME="FNAME";	

	private static final String loginBody2passwords ="[{\"Subsidiaries\":[{\"IsHostRegistered\":true,\"User\":\"sareserved\",\"Password\":"
			+ "\"" +PWD1 + "\"" //"\"testing\""
			+ "}],\"FirstName\":\"" + FNAME+ "\",\"LastName\":\"sss\",\"Email\":\"sss@com.com\",\"CompanyName\":\"sareserved\"},"
			+ "{\"IsHostRegistered1\":true,\"User1\":\"sareserved\",\"password\":"
			+ "\"" +PWD2 + "\"" //"\"testing\""
			+ "}],\"FirstName1\":\"sss\",\"LastName1\":\"sss\",\"Email1\":\"sss@com.com\",\"CompanyName1\":\"sareserved\"}]";
	
	public static final String loginBodyNopassword = "{\"IsSucceeded\":false,\"SyncId\":\"b6a1a19a-f033-4827-8b65-de453a2992e9\"," +
			"\"RequestCount\":0,\"BulkCount\":0,\"BulkNumber\":0,\"Ready\":false,\"ProcessStatus\":0," +
			"\"ProcessStartTime\":\"0001-01-01T00:00:00\",\"ProcessEndTime\":\"0001-01-01T00:00:00\"," +
			"\"ProcessResult\":null,\"ProcessError\":null,\"PercentageDone\":0," +
			"\"SuccessfulUpdates\":null,\"FailedUpdates\":null," +
			"\"ValidationErrors\":[{\"ErrorType\":11,\"ErrorDescription\":\"Invalid bulk number 1\"}],\"CharmError\":null" +
			"}";
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSeveralPasswordsAreHidden() {
		//VerbalTestExecutor.executeTest("testSeveralPasswordsAreHidden", () -> {
			String updatedBody = JsonPropertyMasker.mask(loginBody2passwords, "password");
			System.out.println(loginBody2passwords);
			System.out.println(updatedBody);
			assertFalse(loginBodyNopassword.equals(updatedBody));
			// Original string should not change
			assertTrue(loginBody2passwords.indexOf(PWD1) != -1);
			assertTrue(loginBody2passwords.indexOf(PWD2) != -1);
			// Updated string should not have passwords
			assertTrue(updatedBody.indexOf(PWD1) == -1);
			assertTrue(updatedBody.indexOf(PWD2) == -1);
		//});		
	}

	@Test
	public void testSameStringIfNoPassword() {
		//VerbalTestExecutor.executeTest("testSameStringIfNoPassword", () -> {
			String updatedBody = JsonPropertyMasker.mask(loginBodyNopassword, "password");
			assertEquals(loginBodyNopassword, updatedBody);
		//});		
	}

	@Test
	public void testMaskFirstName() {
		//VerbalTestExecutor.executeTest("testMaskFirstName", () -> {
			String updatedBody = JsonPropertyMasker.mask(loginBody2passwords, "FirstName");
			System.out.println(updatedBody);
			assertFalse(loginBody2passwords.equals(updatedBody));
			assertTrue(loginBody2passwords.indexOf(FNAME) != -1);
			assertTrue(updatedBody.indexOf(FNAME) == -1);
		//});		
	}
	
	@Test
	public void testMaskFirstNameAndPwd() {
		//VerbalTestExecutor.executeTest("testMaskFirstNameAndPwd", () -> {
			String updatedBody1 = JsonPropertyMasker.mask(loginBody2passwords, "FirstName");
			System.out.println(updatedBody1);
			assertFalse(loginBody2passwords.equals(updatedBody1));
			assertTrue(loginBody2passwords.indexOf(FNAME) != -1);
			assertTrue(updatedBody1.indexOf(FNAME) == -1);
			
			String updatedBody2 = JsonPropertyMasker.mask(updatedBody1, "password");
			System.out.println(updatedBody2);
			assertFalse(updatedBody1.equals(updatedBody2));
			assertTrue(updatedBody2.indexOf(PWD1) == -1);
		//});		
	}

	@Test
	public void testMaskNotStringProperty() {
		//VerbalTestExecutor.executeTest("testMaskNotStringProperty", () -> {
			String updatedBody = JsonPropertyMasker.mask(loginBody2passwords, "IsHostRegistered1");
			System.out.println(updatedBody);
			assertTrue(loginBody2passwords.equals(updatedBody));
		//});		
	}
	
}
