package sessionprovider;

public interface SessionProvider {
	public SessionDTO startSession(String loginUserName, String loginPassword);
	public String encryptSession(SessionDTO session);
	public SessionDTO restoreSession(String encriptedSession);
}
