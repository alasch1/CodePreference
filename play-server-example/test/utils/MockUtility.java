package utils;

import java.util.Base64;
import java.util.HashMap;

import play.libs.Json;
import play.mvc.Call;
import play.mvc.Http;
import play.mvc.Http.RequestBuilder;
import play.test.Helpers;

import com.fasterxml.jackson.databind.JsonNode;

public final class MockUtility {
	
	public static final String FAKE_SESSION_ID = "12345";
	public static final String BASIC_AUTH_VALUE = "stam@ff.com:12345";
	
	public static <T> RequestBuilder fakeRequestWithJson(T input, String method, String url) {
		JsonNode jsonNode = Json.toJson(input);
		System.out.println("jsonNode="+jsonNode);
		RequestBuilder fakeRequest = Helpers.fakeRequest(method, url).bodyJson(jsonNode);
		System.out.println("Created fakeRequest="+fakeRequest +", body="+fakeRequest.body().asJson());
		return fakeRequest;
	}

	public static RequestBuilder fakeActionRequest(Call action) {
		RequestBuilder fakeRequest = Helpers.fakeRequest(action);//.header("Content-Type","application/json");
		System.out.println("Created fakeRequest="+fakeRequest +", body="+fakeRequest.body().asJson());
		return fakeRequest;
	}
	
	public static RequestBuilder fakeActionRequestWithSession(Call action) {
		RequestBuilder fakeRequest = Helpers.fakeRequest(action).session("sessionId", FAKE_SESSION_ID);
		System.out.println("Created fakeRequest="+fakeRequest +", session="+fakeRequest.session());
		return fakeRequest;
	}
	
	public static RequestBuilder fakeActionRequestWithBaseAuthHeader(Call action) {
		String encoded = Base64.getEncoder().encodeToString(BASIC_AUTH_VALUE.getBytes());
		RequestBuilder fakeRequest = Helpers.fakeRequest(action).header(Http.HeaderNames.AUTHORIZATION, "Basic " + encoded);
		System.out.println("Created fakeRequest="+fakeRequest.toString() );
		return fakeRequest;
	}
	
	public static <T> RequestBuilder fakeActionRequestWithJson(Call action, T input) {
		JsonNode jsonNode = Json.toJson(input);
		System.out.println("jsonNode="+jsonNode);
		RequestBuilder fakeRequest = Helpers.fakeRequest(action).bodyJson(jsonNode);
		System.out.println("Created fakeRequest="+fakeRequest +", body="+fakeRequest.body().asJson());
		return fakeRequest;
	}
	
	public static Http.Session fakeSession() {
		return new Http.Session(new HashMap<String, String>());
	}
	
	private MockUtility() {}
}
