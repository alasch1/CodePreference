package controllers;

import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public Result exactRoute(String message) {
        return ok(examplepage.render("This is the exact route:" + message));
    }

    public Result insensitiveRoute(String dummy) {
    	// Extract query parameter case-insensitive
    	String message = InputRequestHelper.getInputQueryParameter("mymessage", request(), true);
        return ok(examplepage.render("This is insensitive route:" + message));
    }

}
