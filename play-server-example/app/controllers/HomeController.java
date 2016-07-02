package controllers;

import java.io.ByteArrayInputStream;

import javax.inject.Inject;

import play.Logger;
import play.api.Play;
import play.mvc.*;
import sessionprovider.SessionDTO;
import sessionprovider.SessionInCashProvider;
import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	static private final String CSV_FILE = "demo.csv";
	static private final String DEMO_SESSION = "demo-session";
	
	@Inject
	private SessionInCashProvider sessionProvider;
	
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
    	SessionDTO session = sessionProvider.startSession("one","two");
    	String encryptedSession = sessionProvider.encryptSession(session);
    	ctx().session().put(DEMO_SESSION, encryptedSession);
    	Logger.info("Started {}", session);
    	return ok("Session was started");
    }
    
    public Result restoreSession() {
    	String encryptedSession = ctx().session().get(DEMO_SESSION);
    	SessionDTO restoredSession = sessionProvider.restoreSession(encryptedSession);
    	Logger.info("Restored {}", restoredSession);
    	return ok("Session was restored");
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
