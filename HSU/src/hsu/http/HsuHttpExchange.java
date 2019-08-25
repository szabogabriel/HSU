package hsu.http;

import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import hsu.http.session.Session;

public class HsuHttpExchange {
	
	private final HttpExchange EXCHANGE;
	private final SessionHandler SESSION;
	private final Cookies COOKIES;
	
	public HsuHttpExchange(HttpExchange exchange) {
		EXCHANGE = exchange;
		SESSION = new SessionHandler(exchange);
		COOKIES = new Cookies(exchange);
	}
	
	public Map<String, String> getValues() {
		return COOKIES.getValues();
	}

	public void setValue(String key, String value) {
		COOKIES.setValue(key, value);
	}
	
	public Session getSession() {
		return SESSION.getSession();
	}
	
	public void removeSession() {
		SESSION.removeSession();
	}
	
	public HttpExchange getExchange() {
		return EXCHANGE;
	}
	
	public Map<String, String> getAllParameters() {
		Map<String, String> ret = new HashMap<>();
		
		
		
		return ret;
	}
	
	public Map<String, String> getQueryStringParameters() {
		Map<String, String> ret = new HashMap<>();
		
		String URI = EXCHANGE.getRequestURI().toString();
		
		if (containsQueryString(URI)) {
			
		}
		
		return ret;
	}
	
	private boolean containsQueryString(String url) {
		return url != null && url.contains("?") && url.indexOf('?') < url.length() - 1;
	}
	
}
