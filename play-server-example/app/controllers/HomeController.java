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
public class HomeController extends Controller {

	static private final String CSV_FILE = "demo.csv";
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	
	Provider<Application> applicationProvider;
	
	@Inject 
	public HomeController(
			Provider<Application> applicationProvider) {
		this.applicationProvider = applicationProvider;
	}
	
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
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
		return ok(applicationProvider.get().getFile(CSV_FILE)).as("text/csv");
    }
    
}
