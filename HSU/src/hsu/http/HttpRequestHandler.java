package hsu.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.net.httpserver.HttpExchange;

public class HttpRequestHandler {
	
	private final static String HTTP_HEADER_REQUEST_COOKIE = "Cookie";
	
	protected final HttpExchange HTTP_EXCHANGE;
	
	public HttpRequestHandler(HttpExchange exchange) {
		HTTP_EXCHANGE = exchange;
	}
	
	protected void setCookieValue(Cookie cookie) {
		HTTP_EXCHANGE.getResponseHeaders().add("Set-Cookie", cookie.toString());
	}
	
	private boolean isCookieEntry(String key) {
		boolean ret = HTTP_HEADER_REQUEST_COOKIE.equalsIgnoreCase(key);
		return ret;
	}
	
	public Map<String, String> getCookies() {
		Map<String, String> ret = new HashMap<>();
		

		
		return ret;
	}
	
	public Map<String, String> getValues() {
		Map<String, String> ret = new HashMap<>();
		
		for (Entry<String, List<String>> it : getHttpRequestEntries()) {
			String name = it.getKey();
			String value = it.getValue().get(0);
			if (isCookieEntry(name)) {
				
			} else {
				ret.put(name, value);
			}
			
		}
		
		return ret;
	}
	
	private Set<Entry<String, List<String>>> getHttpRequestEntries() {
		Set<Entry<String, List<String>>> ret = HTTP_EXCHANGE.getRequestHeaders().entrySet();
		return ret;
	}
	
}
