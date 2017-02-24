package com.alasch1.cdprf.httpclient;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.fluent.Content;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.util.Strings;

import com.alasch1.cdprf.httpclient.json.BodyPrinter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false) 
class AppHttpResponse {

	public static final AppHttpResponse HTTP_INTERNAL_ERROR_RESPONSE = new AppHttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	public static final AppHttpResponse HTTP_REQUEST_TIMEOUT = new AppHttpResponse(HttpStatus.SC_REQUEST_TIMEOUT);
	public static final int HTTP_MIN_ERR_CODE = 300;
	
	private Content content = Content.NO_CONTENT;
	private int httpStatus = HttpStatus.SC_OK;
	private String jsonBody = Strings.EMPTY;

	public AppHttpResponse() {
	}

	public AppHttpResponse(int httpCode) {
		setHttpStatus(httpCode);
	}

	public AppHttpResponse(int httpCode, Content content) {
		setHttpStatus(httpCode);
		setContent(content);
	}
	
	public void setContent(Content content) {
		this.content = content;
		initBody();
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public void setHttpStatus(StatusLine statusLine) {
		httpStatus = statusLine.getStatusCode();
	}
	
	public boolean isOk() {
		return httpStatus < HTTP_MIN_ERR_CODE;
	}
	
	public boolean isNotFound() {
		return httpStatus == HttpStatus.SC_NOT_FOUND;
	}
	
	public boolean isTimeout() {
		return httpStatus == HttpStatus.SC_REQUEST_TIMEOUT;
	}

	private void initBody() {
		// Content as string may throw null pointer exception, if something went wrong.
		try {
			if (isJsonContent()) {
				jsonBody = content.asString();
			}
		}
		catch(Exception e) {
			jsonBody="null";
		}
	}

	@Override
	public String toString() {
		return String.format("AppHttpResponse [httpStatus=%s, contentType=%s, body=%s]",
				httpStatus, 
				content.getType(), 
				isJsonContent() ? BodyPrinter.body4print(getJsonBody()):"");
	}
	
	private boolean isJsonContent() {
		return content != null && content.getType() == ContentType.APPLICATION_JSON;
	}
}