package guiceExamples;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import guiceExamples.sessionProvider.SessionDTO;
import guiceExamples.sessionProvider.SessionProvider;

import org.junit.Before;
import org.junit.Test;

import utils.FakeApplicationTestBase;

public class TestInCacheSessionProvider extends FakeApplicationTestBase {
	
	static private String FAKE_USER ="FAKE_USER";
	static private String FAKE_PASSWORD = "FAKE_PASSWORD";
	static private String FAKE_DATA = "FAKE_DATA";
	
	private SessionProvider sessionProvider;
	
 	@Before
	public void setUp() {
		sessionProvider = getInjector().instanceOf(SessionProvider.class);
	}
	
	@Test
	public void testEncodeDecodeSession() {
		SessionDTO sessionId = sessionProvider.startSession(FAKE_USER, FAKE_PASSWORD);
		assertNotNull(sessionId);
		sessionId.setData(FAKE_DATA);
		String sessionEncrypt = sessionProvider.prepareCookie(sessionId);
		assertFalse(sessionEncrypt.isEmpty());
		SessionDTO restoredSessionId = sessionProvider.restoreSession(sessionEncrypt);
		assertNotNull(restoredSessionId);
		assertEquals(sessionId.getUuid(), restoredSessionId.getUuid());
		assertEquals(sessionId.getData(), restoredSessionId.getData());
	}
	
	
}
