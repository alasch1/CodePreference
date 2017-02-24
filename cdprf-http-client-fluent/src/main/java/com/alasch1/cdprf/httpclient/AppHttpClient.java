package com.alasch1.cdprf.httpclient;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentResponseHandler;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alasch1.cdprf.httpclient.json.BodyPrinter;

/**
 * Implements HttpClient on base of fluent API.
 * Implements {@link ResponseHandler} for response type {@link AppHttpResponse}
 * Propagates status and content, received from the REST API, to a caller.
 * In case of unexpected runtime error returns Internal error code
 * 
 * @author aschneider
 *
 */
public class AppHttpClient implements ResponseHandler<AppHttpResponse> {
	
	public static final Optional<String> NO_BODY = Optional.ofNullable(null);
	
	private static final Logger LOG = LogManager.getLogger(AppHttpClient.class);

	private boolean ignoreSslErrors = false;
	private HostProxyInfo hostProxyInfo = HostProxyInfo.NO_PROXY;
	
	public AppHttpClient() {
	}
	
	public AppHttpClient(boolean ignoreSslErrors, HostProxyInfo hostProxyInfo) {
		this.ignoreSslErrors = ignoreSslErrors;
		this.hostProxyInfo = hostProxyInfo;
	}
	
	/**
	 * Executes http post request according to given url and headers;  if a body is provided puts it into request
	 * 
	 * @param url
	 * @param headersDefs
	 * @param body
	 * @return
	 */
	public AppHttpResponse post(String url, Map<String, String> headersDefs, Optional<String> body) {
		LOG.debug("POST for url:{}, body:{}", url, toStringBody(body));
		return executeMethod((String u) -> Request.Post(u), url, headersDefs, body);
	}
	
	/**
	 * Executes http get request according to given url and headers
	 * 
	 * @param url
	 * @param headersDefs
	 * @return HpaClientResponse
	 */
	public AppHttpResponse get(String url, Map<String, String> headersDefs) {
		LOG.debug("GET for url:{}", url);
		return executeMethod((String u) -> Request.Get(u), url, headersDefs, NO_BODY);
	}
	
	/**
	 * Executes http put request according to given url and headers; if a body is provided puts it into request
	 * Open issue - get request body
	 * 
	 * @param url
	 * @param headersDefs
	 * @return HpaClientResponse
	 * @param body
	 */
	public AppHttpResponse put(String url, Map<String, String> headersDefs, Optional<String> body) {
		LOG.debug("PUT for url:{}, body:{}" ,url, toStringBody(body));
		return executeMethod((String u) -> Request.Put(u), url, headersDefs, body);
	}
	
	@Override
	public AppHttpResponse handleResponse(HttpResponse response) throws IOException {
		LOG.trace("Start handling HttpResponse ... ");
		StatusLine statusLine = response.getStatusLine();
		HttpEntity entity = response.getEntity();
		AppHttpResponse hpaClientResponse = new AppHttpResponse();
		hpaClientResponse.setHttpStatus(statusLine);
		hpaClientResponse.setContent(extractResultContent(entity));
		return hpaClientResponse;
	}
	
	/**
	 * Encapsulates execution of {@link HttpMethod}. Calls underlying execution
	 * method depending on value of {@code ignoreSslErrors}
	 * 
	 * @param method
	 * @param url
	 * @param headersDefs
	 * @param body
	 * @return
	 */
	private AppHttpResponse executeMethod(
			HttpMethod method, 
			String url, 
			Map<String, String> headersDefs, 
			Optional<String> body) {
		
		try {
			if (ignoreSslErrors) {
				return executorExecute(method, url, headersDefs, body);
			}
			else {
				return requestExecute(method, url, headersDefs, body);
			}
		}
		catch(SocketTimeoutException | SocketException| ConnectTimeoutException e) {
			LOG.error("HTTP TIMEOUT with :[{}]", e.toString());
			return AppHttpResponse.HTTP_REQUEST_TIMEOUT;
		}
		catch (Exception e) {
			LOG.error("Failed to handle HTTP response", e);
			return AppHttpResponse.HTTP_INTERNAL_ERROR_RESPONSE;
		}
	}
	
	/**
	 * Executes http request with fluent API Request.execute
	 * 
	 * @param method
	 * @param url
	 * @param headersDefs
	 * @param body
	 * @return
	 * @throws Exception
	 */
	private AppHttpResponse requestExecute(
			HttpMethod method, String url, 
			Map<String, String> headersDefs, 
			Optional<String> body) throws Exception {
		if (body.isPresent()) {
			return prepareRequest(method, url, headersDefs).bodyString(body.get(), ContentType.APPLICATION_JSON).execute().handleResponse(this);
		}
		else {
			return prepareRequest(method, url, headersDefs).execute().handleResponse(this);
		}
	}
	
	/**
	 * Executes http request with fluent API Executor.execute. Allows explicit configuration
	 * of HttpClient
	 * 
	 * @param method
	 * @param url
	 * @param headersDefs
	 * @param body
	 * @return
	 * @throws Exception
	 */
	private AppHttpResponse executorExecute(HttpMethod method, 
			String url, Map<String, String> headersDefs, 
			Optional<String> body) throws Exception {
		Executor executor = createExecutor(ignoreSslErrors);
		if (body.isPresent()) {
			return executor.execute(prepareRequest(method, url, headersDefs).bodyString(body.get(), ContentType.APPLICATION_JSON)).handleResponse(this);
		}
		else {
			return executor.execute(prepareRequest(method, url, headersDefs)).handleResponse(this);
		}
	}
	
	private Request prepareRequest(HttpMethod method, String url, Map<String, String> headersDefs) {
		Request request = addHeaders(method.run(url), headersDefs);
		if (!hostProxyInfo.isEmpty()) {
			LOG.debug("Connection via proxy {}", hostProxyInfo);
			request.viaProxy(new HttpHost(hostProxyInfo.getUrl(), hostProxyInfo.getPort()));
		}
		return request;
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
			LOG.warn("DON'T USE THIS METHOD IN PRODUCTION. Invalid SSL certificates will be ignored !!" );
			return Executor.newInstance(noSslHttpClient());
		}
		else {
			return Executor.newInstance();
		}
	}
	
	protected Content extractResultContent(HttpEntity entity) throws IOException {
		ContentResponseHandler contentHandler = new ContentResponseHandler();
		return contentHandler.handleEntity(entity);
	}
	
	protected Request addHeaders(Request fluentRequest, Map<String, String> headers)  {
		headers.forEach((k, v) -> {
			LOG.trace(String.format("adding header %s, value=%s", k, v));
			fluentRequest.addHeader(k, v);
		});
		return fluentRequest;
	}
	
	protected Request addBody(Request fluentRequest, String body) {
		fluentRequest.bodyString(body, ContentType.APPLICATION_JSON);
		return fluentRequest;
	}

	private String toStringBody( Optional<String> body) {
		return body.isPresent() ? BodyPrinter.body4print(body.get()) : "no body";
	}
	/**
	 * The interface which represents fluent request method execution (Post, Get etc.)
	 * 
	 * @author aschneider
	 *
	 */
	@FunctionalInterface
	public interface HttpMethod {
		Request run(String url);
	}
	
	/**
	 * The interface which represents the REST API call to be sent by HttpClient
	 * 
	 * @author aschneider
	 *
	 */
	
	@FunctionalInterface
	public interface HttpClientMethod {
		AppHttpResponse run(String url, Optional<String> body, Map<String, String> headersDefs);
	}
}
