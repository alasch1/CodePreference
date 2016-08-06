package controllers;

import guiceExamples.cache.CacheProvider;
import guiceExamples.sessionProvider.SessionDTO;
import guiceExamples.sessionProvider.SessionInCacheProvider;

import java.io.ByteArrayInputStream;

import javax.inject.Inject;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import play.api.Play;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.examplepage;
import views.html.index;

import com.alasch1.logging.impl.LoggerFactory;
/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	static private final String CSV_FILE = "demo.csv";
	static private final String DEMO_SESSION = "demo-session";
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	private SessionInCacheProvider sessionProvider;
	
	@Inject 
	private CacheProvider cacheProvider;
	
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
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

    public Result exactRoute(String message) {
        return ok(examplepage.render("This is the exact route:" + message));
    }

    public Result insensitiveRoute(String dummy) {
    	// Extract query parameter case-insensitive
    	String message = InputRequestHelper.getInputQueryParameter("mymessage", request(), true);
        return ok(examplepage.render("This is insensitive route:" + message));
    }
    
    public Result getCsvFile1() {
    	String csvContent = "f1,f2,f3,f4";
		response().setHeader(CONTENT_DISPOSITION, 
				String.format("attachment; filename=\"%s\"", CSV_FILE));
		return ok(new ByteArrayInputStream(csvContent.getBytes())).as("text/csv");
    }

    public Result getCsvFile2() {
		response().setHeader(CONTENT_DISPOSITION, 
				String.format("attachment; filename=\"%s\"", CSV_FILE));
		return ok(Play.current().getFile(CSV_FILE)).as("text/csv");
    }
    
}
