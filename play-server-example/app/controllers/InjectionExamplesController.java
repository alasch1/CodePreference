package controllers;

import guiceExamples.cache.CacheProvider;
import guiceExamples.sessionProvider.SessionDTO;
import guiceExamples.sessionProvider.SessionInCacheProvider;

import java.io.ByteArrayInputStream;

import javax.inject.Inject;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import play.mvc.Controller;
import play.mvc.Result;
import play.Application;
import views.html.examplepage;
import views.html.index;
import views.html.injectappexample;

import com.alasch1.logging.impl.LoggerFactory;
import com.google.inject.Provider;
/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class InjectionExamplesController extends Controller {

	static private final String DEMO_SESSION = "demo-session";
	private static final Logger LOG = LoggerFactory.getLogger(InjectionExamplesController.class);
	
	//@Inject
	private SessionInCacheProvider sessionProvider;
	
	//@Inject 
	private CacheProvider cacheProvider;
	
	Provider<Application> applicationProvider;
	
	@Inject 
	public InjectionExamplesController(
			SessionInCacheProvider sessionProvider,
			CacheProvider cacheProvider,
			Provider<Application> applicationProvider) {
		this.sessionProvider = sessionProvider;
		this.cacheProvider = cacheProvider;	
		this.applicationProvider = applicationProvider;
	}
	
    public Result startSession() {
    	SessionDTO sessionDto = sessionProvider.startSession("one","two");
    	String encryptedSession = sessionProvider.prepareCookie(sessionDto);
    	LOG.info("Cookie value: {}", encryptedSession);
    	ctx().session().put(DEMO_SESSION, encryptedSession);
    	LOG.info("Started {}", sessionDto);
    	return ok("Session was started:" + sessionDto.toString());
    }
    
    public Result restoreSession() {
    	String encryptedSession = ctx().session().get(DEMO_SESSION);
    	SessionDTO restoredSession = sessionProvider.restoreSession(encryptedSession);
    	LOG.info("Restored session {}", restoredSession);
    	return ok("Session was restored: " + restoredSession.toString());
    }
    
	/**
	 * Gets data for the current session from cache. Serves for maintenance. 
	 * 
	 * @return
	 */
    public Result getCacheData() {
//    	Object cacheData = cacheProvider.getCache().get("DEMO-KEY");
//    	return ok(String.format("Cache content : %s", cacheData));		
		LOG.info("Getting from cache the current browser session data");
		String sessionCookie = ctx().session().get(DEMO_SESSION);
		if (Strings.isNotBlank(sessionCookie)) {
			SessionDTO session = sessionProvider.restoreSession(sessionCookie);
			Object cacheData = cacheProvider.getCache().get(session.getUuid());
//			return ok(String.format("Cache content at %s: [%s]: %s", SspStringUtils.localTimeISO(false), session.getBrowserUuid(), cacheData));		
			return ok(String.format("Cache content: [%s]: %s", session.getUuid(), cacheData));		
		}
		else {
			return ok("No active section, cache is empty");
		}
    }

    public Result injectAppExample() {
        return ok(injectappexample.render("Use application object", applicationProvider.get()));
    }
}
