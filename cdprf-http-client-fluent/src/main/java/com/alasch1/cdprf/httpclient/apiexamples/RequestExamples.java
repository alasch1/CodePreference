package com.alasch1.cdprf.httpclient.apiexamples;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentResponseHandler;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.logging.log4j.util.Strings;

public class RequestExamples {

	String basicGetRequest() throws ClientProtocolException, IOException {
		//System.out.println(Request.Get("https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fluent.html").execute().returnContent().asString());
		return Request.Get("http://someurl")
				.execute().returnContent().asString();
	}

	HttpResponse postRequestWithBody(String json) throws ClientProtocolException, IOException {
		return Request.Post("http://someurl")
				.addHeader("app-header", "example")
				.bodyString(json, ContentType.APPLICATION_JSON)		
				.execute().returnResponse();
	}
	
	void postRequestWithBody2(String json) throws ClientProtocolException, IOException {
		HttpResponse response = Request.Post("http://someurl")
				.addHeader("app-header", "example")
				.bodyString(json, ContentType.APPLICATION_JSON)		
				.execute().returnResponse();
		ContentResponseHandler contentHandler = new ContentResponseHandler();
		String body = contentHandler.handleEntity(response.getEntity()).asString();
		int httpStatus = response.getStatusLine().getStatusCode();
	}
	
	HttpResponse requestViaProxy() throws ClientProtocolException, IOException  {
		return Request.Get("http://someurl")
				.addHeader("app-header", "example")
				.viaProxy(new HttpHost("myproxy", 8080))
				.execute().returnResponse();
	}
	
	HttpResponse requestViaProxy2() throws ClientProtocolException, IOException  {
		Executor executor = Executor.newInstance();
		return executor.execute(
				Request.Get("http://someurl")
					.addHeader("app-header", "example")
					.viaProxy(new HttpHost("myproxy", 8080)))
				.returnResponse();
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
						new AppResponseHandler());
	}
	
	String ignoreSslErrorsRequest() throws Exception {
		Executor executor = Executor.newInstance(noSslHttpClient());
		return executor.execute(Request.Get("http://someurl")).returnContent().asString();
	}

	private static CloseableHttpClient noSslHttpClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		final SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(null, (x509CertChain, authType) -> true)
				.build();

		return HttpClientBuilder.create()
				.setSSLContext(sslContext)
				.setConnectionManager(
						new PoolingHttpClientConnectionManager(
								RegistryBuilder.<ConnectionSocketFactory>create()
								.register("http", PlainConnectionSocketFactory.INSTANCE)
								.register("https", new SSLConnectionSocketFactory(sslContext,
										NoopHostnameVerifier.INSTANCE))
								.build()
								))
				.build();
	}

	private static Executor createExecutor(boolean ignoreSSL) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		if (ignoreSSL) {
			System.out.println("DON'T USE THIS METHOD IN PRODUCTION. Invalid SSL certificates will be ignored !!" );
			return Executor.newInstance(noSslHttpClient());
		}
		else {
			return Executor.newInstance();
		}
	}
}

class AppResponse {
	int httpStatus = HttpStatus.SC_OK;
	String jsonBody = Strings.EMPTY;
	
	AppResponse(int httpStatus, String jsonBody) {
		this.httpStatus = httpStatus;
		this.jsonBody = jsonBody;
	}
}

class AppResponseHandler implements ResponseHandler<AppResponse> {

	@Override
	public AppResponse handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		HttpEntity entity = response.getEntity();
		ContentResponseHandler contentHandler = new ContentResponseHandler();
		AppResponse appResponse = new AppResponse(
				response.getStatusLine().getStatusCode(),
				contentHandler.handleEntity(entity).asString());
		return appResponse;
	}
}
