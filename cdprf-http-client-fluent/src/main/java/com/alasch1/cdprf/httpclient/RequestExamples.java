package com.alasch1.cdprf.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentResponseHandler;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

public class RequestExamples {
	
	String basicGetRequest() throws ClientProtocolException, IOException {
		return Request.Get("http://someurl")
		.execute().returnContent().asString();
	}

	HttpResponse postRequestWithBody(String json) throws ClientProtocolException, IOException {
		return Request.Post("http://someurl")
		.addHeader("app-header", "example")
		.bodyString(json, ContentType.APPLICATION_JSON)
		.execute().returnResponse();
	}

String basicExecutorGetRequest() throws ClientProtocolException, IOException {
	Executor executor = Executor.newInstance();
	return executor.execute(Request.Get("http://someurl"))
			.returnContent().asString();
}

HttpResponse postExecutorRequestWithBody(String json) throws ClientProtocolException, IOException {
	Executor executor = Executor.newInstance();
	return executor.execute(Request.Post("http://someurl")
			.addHeader("app-header", "example")
			.bodyString(json, ContentType.APPLICATION_JSON))
			.returnResponse();
}

	AppResponse getRequest() throws ClientProtocolException, IOException {
		return Request.Get("http://someurl")
		.execute().handleResponse(
				new ResponseHandler<AppResponse>(){
					@Override
					public AppResponse handleResponse(HttpResponse response)
							throws ClientProtocolException, IOException {
						AppResponse appResponseData = new AppResponse();
						HttpEntity entity = response.getEntity();
						appResponseData.httpStatus = response.getStatusLine().getStatusCode();
						ContentResponseHandler contentHandler = new ContentResponseHandler();
						appResponseData.content = contentHandler.handleEntity(entity);
						return appResponseData;
					}
				}
		);
	}
}

class AppResponse {
	public Content content = Content.NO_CONTENT;
	public int httpStatus = HttpStatus.SC_OK;
	public String jsonBody = "";
}

class AppResponseHandler implements ResponseHandler<AppResponse> {

	@Override
	public AppResponse handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		AppResponse appResponseData = new AppResponse();
		HttpEntity entity = response.getEntity();
		appResponseData.httpStatus = response.getStatusLine().getStatusCode();
		ContentResponseHandler contentHandler = new ContentResponseHandler();
		appResponseData.content = contentHandler.handleEntity(entity);
		return appResponseData;
	}
}
