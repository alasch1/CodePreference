package com.alasch1.cdprf.httpclient;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.alasch1.cdprf.commons.testutils.VerbalTestExecutor;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Request.class)
@PowerMockIgnore("javax.management.*")
public class TestAppHttpClient {
	
	@Mock
	private HttpResponse fakeHttpResponse;
	@Mock
	private HttpEntity fakeHttpEntity;
	@Mock
	private StatusLine fakeStatusLine;
	@Mock
	private Request fakeRequest;
	@Mock
	private Response fakeResponse;
	
	private AppHttpClient httpClient = new AppHttpClient();
	private static Map<String, String> headers;
	private static final String FAKE_URL = "dummy";
	private Optional<String> FAKE_BODY = Optional.of("No body");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		headers = new HashMap<>();
	}

	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(Request.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHandleOkResponse() {
		VerbalTestExecutor.executeTest("testHandleOkResponse", () -> {
			prepareGeneralMocks(HttpStatus.SC_OK);
			AppHttpResponse AppHttpResponse = httpClient.handleResponse(fakeHttpResponse);
			System.out.println(AppHttpResponse);
			assertOkResponse(AppHttpResponse);
		});
	}
	
	@Test
	public void testHandleResponseWithError() {
		VerbalTestExecutor.executeTest("testHandleResponseWithError", () -> {
			prepareGeneralMocks(HttpStatus.SC_NOT_FOUND);
			AppHttpResponse AppHttpResponse = httpClient.handleResponse(fakeHttpResponse);
			assertErrorResponse(AppHttpResponse, HttpStatus.SC_NOT_FOUND);
		});
	}

	@SuppressWarnings("unchecked")
	@Test (expected = RuntimeException.class)
	public void testHandleResponseWithException() throws Exception {
		VerbalTestExecutor.executeTestAllowEx("testHandleResponseWithException", () -> {
			when(fakeHttpResponse.getEntity()).thenThrow(RuntimeException.class);
			AppHttpResponse AppHttpResponse = httpClient.handleResponse(fakeHttpResponse);
			assertNull("Response was not expected" , AppHttpResponse);
		});
	}
	
	@Test
	public void testPostOk() {
		VerbalTestExecutor.executeTest("testPostOk", () -> {
			prepareGeneralMocks(HttpStatus.SC_OK);
			mockRequestExecution();
			when(Request.Post(Mockito.anyString())).thenReturn(fakeRequest);
			AppHttpResponse AppHttpResponse = httpClient.post(FAKE_URL, headers, FAKE_BODY);
			assertOkResponse(AppHttpResponse);
		});
	}

	@Test
	public void testPostWithNotFoundError() {
		VerbalTestExecutor.executeTest("testPostWithNotFoundError", () -> {
			prepareGeneralMocks(HttpStatus.SC_NOT_FOUND);
			mockRequestExecution();
			when(Request.Post(Mockito.anyString())).thenReturn(fakeRequest);
			AppHttpResponse AppHttpResponse = httpClient.post(FAKE_URL, headers, FAKE_BODY);
			assertErrorResponse(AppHttpResponse, HttpStatus.SC_NOT_FOUND);
		});
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testResponseTimeout() throws Exception {
		VerbalTestExecutor.executeTestAllowEx("testResponseTimeout", () -> {
			when(fakeRequest.bodyString(Mockito.anyString(), Mockito.anyObject())).thenReturn(fakeRequest);
			when(fakeRequest.execute()).thenThrow(SocketTimeoutException.class);
			when(fakeResponse.handleResponse(httpClient)).thenCallRealMethod();
			when(Request.Post(Mockito.anyString())).thenReturn(fakeRequest);
			AppHttpResponse AppHttpResponse = httpClient.post(FAKE_URL, headers, FAKE_BODY);
			assertErrorResponse(AppHttpResponse, HttpStatus.SC_REQUEST_TIMEOUT);
		});
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPostWithException() {
		VerbalTestExecutor.executeTest("testPostWithException", () -> {
			when(fakeHttpResponse.getEntity()).thenThrow(RuntimeException.class);
			mockRequestExecution();
			when(Request.Post(Mockito.anyString())).thenReturn(fakeRequest);
			AppHttpResponse AppHttpResponse = httpClient.post(FAKE_URL, headers, FAKE_BODY);
			assertErrorResponse(AppHttpResponse, HttpStatus.SC_INTERNAL_SERVER_ERROR);
		});
	}

	@Test
	public void testGetOk() {
		VerbalTestExecutor.executeTest("testGetOk", () -> {
			prepareGeneralMocks(HttpStatus.SC_OK);
			mockRequestExecution();
			when(Request.Post(Mockito.anyString())).thenReturn(fakeRequest);
			AppHttpResponse AppHttpResponse = httpClient.post(FAKE_URL, headers, FAKE_BODY);
			assertOkResponse(AppHttpResponse);
		});
	}

	private void assertOkResponse(AppHttpResponse AppHttpResponse) {		
		assertNotNull("Null AppHttpResponse", AppHttpResponse);
		assertEquals("OK http code is expected", AppHttpResponse.getHttpStatus(), HttpStatus.SC_OK);
		assertNotNull("Content is null !!", AppHttpResponse.getContent());
	}
	
	private void assertErrorResponse(AppHttpResponse AppHttpResponse, int httpStatus) {		
		assertNotNull("Null AppHttpResponse", AppHttpResponse);
		assertEquals("Not found http code is expected", AppHttpResponse.getHttpStatus(), httpStatus);
		assertNotNull("Content is null !!", AppHttpResponse.getContent());
	}
	
	private void prepareGeneralMocks(int httpStatus) {
		when(fakeHttpResponse.getEntity()).thenReturn(fakeHttpEntity);
		when(fakeHttpResponse.getStatusLine()).thenReturn(fakeStatusLine);
		when(fakeStatusLine.getStatusCode()).thenReturn(httpStatus);		
	}
	
	private void mockRequestExecution() throws Exception {
		when(fakeRequest.bodyString(Mockito.anyString(), Mockito.anyObject())).thenReturn(fakeRequest);
		when(fakeRequest.execute()).thenReturn(fakeResponse);
		when(fakeResponse.returnResponse()).thenReturn(fakeHttpResponse);
		when(fakeResponse.handleResponse(httpClient)).thenCallRealMethod();
		MemberModifier.field(Response.class, "response").set(fakeResponse, fakeHttpResponse);
	}
}

