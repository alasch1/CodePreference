package com.alasch1.mocking.examples.voidMethod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.function.Consumer;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.alasch1.cdprf.mocking.voidMethod.Cookie;
import com.alasch1.cdprf.mocking.voidMethod.Cookies;
import com.alasch1.cdprf.mocking.voidMethod.CookiesMapper;

/**
 * The test demonstrates mocking of void method forEach of an interface
 * 
 * @author ala schneider
 *
 */
public class TestCookiesMapper {
	
	private static final String COOKIE_NAME1 = "Cookie1";
	private static final String COOKIE_VALUE1 = "Value1";
	private static final String COOKIE_NAME2 = "Cookie2";
	private static final String COOKIE_VALUE2 = "Value2";
	private static final String PATH = "path";
	private static final String DOMAIN = "domain";
	private static final boolean SECURED=true;
	private static final boolean NOT_HTTP_ONLY=false;
	
	private Cookie[] FAKE_COOKIES = {
			new Cookie(COOKIE_NAME1, COOKIE_VALUE1, 10, PATH, DOMAIN, SECURED, NOT_HTTP_ONLY),
			new Cookie(COOKIE_NAME2, COOKIE_VALUE2, 10, PATH, DOMAIN, SECURED, NOT_HTTP_ONLY)
	};
	
	private Cookies mockCookies = Mockito.mock(Cookies.class);

	@Test
	public void testMapping() {
		Mockito.doAnswer(mockForEach()).when(mockCookies).forEach(Mockito.any());
		Map<String,String> cookiesMap = CookiesMapper.buildCookiesMap(mockCookies);
		assertNotNull(cookiesMap);
		assertTrue(cookiesMap.size() == FAKE_COOKIES.length);
		assertEquals(COOKIE_VALUE1, cookiesMap.get(COOKIE_NAME1));
		assertEquals(COOKIE_VALUE2, cookiesMap.get(COOKIE_NAME2));
	}

	Answer<Void> mockForEach() {
		return new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] arguments = invocation.getArguments();
				Consumer<Cookie> c = (Consumer<Cookie>)arguments[0];
				c.accept(FAKE_COOKIES[0]);
				c.accept(FAKE_COOKIES[1]);
				return null;
			}};
	}
}
