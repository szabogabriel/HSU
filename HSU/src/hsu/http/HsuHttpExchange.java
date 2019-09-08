package hsu.http;

import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import hsu.http.session.Session;
import hsu.post.PostMultipartHandler;
import hsu.post.UploadedElement;

public class HsuHttpExchange {
	
	private final HttpExchange EXCHANGE;
	private final QueryString QUERY_STRING;
	
	public HsuHttpExchange(HttpExchange exchange) {
		EXCHANGE = exchange;
		
		String URI = EXCHANGE.getRequestURI().toString();
		if (containsQueryString(URI)) {
			QUERY_STRING = new QueryString(getQueryString(URI));
		} else {
			QUERY_STRING = new QueryString("");
		}
	}
	
	public Map<String, String> getValues() {
		return null;
	}

	public void setValue(Cookie cookie) {
		
	}
	
	public Session getSession() {
		return null;
	}
	
	public void removeSession() {
	}
	
	public HttpExchange getExchange() {
		return EXCHANGE;
	}
	
	public Map<String, String> getAllParameters() {
		Map<String, String> ret = new HashMap<>();
		
		ret.putAll(QUERY_STRING.getData());
		
		return ret;
	}
	
	public Map<String, UploadedElement> getAllParameters(PostMultipartHandler handler) {
		Map<String, UploadedElement> ret = new HashMap<>();
		
		//TODO
		
		return ret;
	}
	
	private boolean containsQueryString(String url) {
		return url != null && url.contains("?") && url.indexOf('?') < url.length() - 1;
	}
	
	private String getQueryString(String url) {
		return url.substring(url.indexOf("?"));
	}
	
}
