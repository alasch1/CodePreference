package guiceExamples.sessionProvider;

import guiceExamples.cache.CacheProvider;

import javax.inject.Inject;

import play.cache.CacheApi;

//@Singleton
public class SessionInCacheProvider implements SessionProvider {

	@Inject 
	private CacheProvider cacheProvider;
	
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
		getCache().set(session.getUuid(), session.getData());
		return encryptedSession;
	}

	@Override
	public SessionDTO restoreSession(String encriptedSession) {
		String[] values = encriptedSession.split(";");
		SessionDTO restoredSession = new SessionDTO();
		restoredSession.setUuid(values[0]);
		restoredSession.setRecentTimestamp(values[1]);
		String sessionData = getCache().get(restoredSession.getUuid());
		restoredSession.setData(sessionData);
		return restoredSession;		
	}

	protected CacheApi getCache() {
		return cacheProvider.getCache();
	}
	
}
