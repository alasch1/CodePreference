package guiceExamples.sessionProvider;

import guiceExamples.cache.CacheProvider;

import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

import play.cache.CacheApi;

import com.alasch1.logging.impl.LoggerFactory;

//@Singleton
public class SessionInCacheProvider implements SessionProvider {

	private static final Logger LOG = LoggerFactory.getLogger(SessionInCacheProvider.class);
	
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
		LOG.info("Started {}", session);
		return session;
	}

	@Override
	public String prepareCookie(SessionDTO session) {
		// This is the dummy code; real encryption should be done
		String encryptedSession = String.format("%s;%s",
				session.getUuid(), 
				session.getRecentTimestamp());
		updateCache(session);
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

	protected void updateCache(SessionDTO session) {
		int sessionTimeoutSec = SessionDTO.SESSION_TIMEOUT_IN_MIN * 60;
		getCache().set(session.getUuid(), session,  sessionTimeoutSec);
		LOG.debug("Cache updated with [{} : {}]", session.getUuid(), getCache().get(session.getUuid()));
	}
	
	protected CacheApi getCache() {
		return cacheProvider.getCache();
	}
	
}
