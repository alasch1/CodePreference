package controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;

import com.alasch1.logging.impl.LoggerFactory;
import com.google.inject.Provider;

import play.Application;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.index;
/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	static private final String CSV_FILE = "demo.csv";
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	
	Provider<Application> applicationProvider;
	
	@Inject 
	public HomeController(Provider<Application> applicationProvider) {
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
    
    public Result uploadFile() throws IOException {
        MultipartFormData<File> body = request().body().asMultipartFormData();
        FilePart<File> filePart = body.getFile("data");
        if (filePart != null) {
            LOG.info("filePart={}", filePart);
            String fileName = filePart.getFilename();
            String contentType = filePart.getContentType();
            File file = filePart.getFile();
            LOG.info("fileName={}, contentType={}, file={}", fileName,contentType, file);
            String stringFromFile = FileUtils.readFileToString(file);
            LOG.info("stringFromFile.length={}", stringFromFile.length());
            return ok("File uploaded to String (length=" + stringFromFile.length()+")");
        } 
        else {
            flash("error", "Missing file");
            return badRequest();
        }
    	
    }
    public Result uploadFileAsText() {
    	String text =  request().body().asText();
        LOG.info("body.asText={}", text);
        return ok("File uploaded:" + text);
    }
    
}
