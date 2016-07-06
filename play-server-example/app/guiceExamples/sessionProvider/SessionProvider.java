package guiceExamples.sessionProvider;

import com.google.inject.ImplementedBy;

@ImplementedBy(SessionInCacheProvider.class)
public interface SessionProvider {
	public SessionDTO startSession(String loginUserName, String loginPassword);
	public String encryptSession(SessionDTO session);
	public SessionDTO restoreSession(String encriptedSession);
}
