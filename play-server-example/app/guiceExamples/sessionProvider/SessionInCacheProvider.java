package guiceExamples.sessionProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import play.cache.CacheApi;

//@Singleton
public class SessionInCacheProvider implements SessionProvider {

	@Inject
	private CacheApi cache;
	
//	@Inject
	public SessionInCacheProvider() {
	}

	@Override
	public SessionDTO startSession(String loginUserName, String loginPassword) {
		SessionDTO session = new SessionDTO();
		session.setData(String.format("%s-%s", loginUserName, loginPassword));
		session.start();
		return session;
	}

	@Override
	public String encryptSession(SessionDTO session) {
		// This is the dummy code; real encryption should be done
		String encryptedSession = String.format("%s;%s",
				session.getUuid(), 
				session.getRecentTimestamp());
		cache.set(session.getUuid(), session.getData());
		return encryptedSession;
	}

	@Override
	public SessionDTO restoreSession(String encriptedSession) {
		String[] values = encriptedSession.split(";");
		SessionDTO restoredSession = new SessionDTO();
		restoredSession.setUuid(values[0]);
		restoredSession.setRecentTimestamp(values[1]);
		String sessionData = cache.get(restoredSession.getUuid());
		restoredSession.setData(sessionData);
		return restoredSession;		
	}

}
