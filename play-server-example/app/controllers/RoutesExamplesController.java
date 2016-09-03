package controllers;

import org.apache.logging.log4j.Logger;

import com.alasch1.logging.impl.LoggerFactory;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.examplepage;

public class RoutesExamplesController extends Controller {

	private static final Logger LOG = LoggerFactory.getLogger(RoutesExamplesController.class);
	
    public Result exactRoute(String message) {
        return ok(examplepage.render("This is the exact route:" + message));
    }

    public Result insensitiveRoute(String dummy) {
    	// Extract query parameter case-insensitive
    	String message = InputRequestHelper.getInputQueryParameter("mymessage", request(), true);
        return ok(examplepage.render("This is insensitive route:" + message));
    }
    
    public Result getPageLine(Integer page, Integer lineNum) {
    	return ok(String.format("Current page:%d, line number:%s", page, lineNum));
    }
    
    public Result getPageNote(int page, String pageNote) {
    	return ok(String.format("Page:%d, pageNote:%s", page, pageNote));
    }
    
    public Result redirectToExample1() {
       	final String REDIRECT_TO_URL = "/example1";
    	LOG.info("Redirecting to {}", REDIRECT_TO_URL);
    	return redirect(REDIRECT_TO_URL);
    }
    
    public Result redirectToIndex() {
    	final String REDIRECT_TO_URL = "/index.html";
    	LOG.info("Redirecting to {}", REDIRECT_TO_URL);
    	return redirect(REDIRECT_TO_URL);
    }
    
    public Result redirectToStaticHtml() {
       	String REDIRECT_TO_URL = "html/staticHtmlFileExample.html";
    	LOG.info("Redirecting to {}", REDIRECT_TO_URL);
    	return redirect(REDIRECT_TO_URL);
    }
}
