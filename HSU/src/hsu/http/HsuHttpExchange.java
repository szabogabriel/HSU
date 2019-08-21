package hsu.http;

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
}
