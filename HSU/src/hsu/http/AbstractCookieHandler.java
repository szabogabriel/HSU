package hsu.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.net.httpserver.HttpExchange;

public class AbstractCookieHandler {
	
	private final static String HTTP_HEADER_REQUEST_COOKIE = "Cookie";
	private final static int COOKIE_INDEX_KEY = 0;
	private final static int COOKIE_INDEX_VALUE = 1;
	
	protected final HttpExchange HTTP_EXCHANGE;
	
	public AbstractCookieHandler(HttpExchange exchange) {
		HTTP_EXCHANGE = exchange;
	}
	
	protected Map<String, String> getCookies() {
		Map<String, String> ret = new HashMap<>();
		
		for (Entry<String, List<String>> it : getHttpRequestEntries())
			if (isCookieEntry(it))
				ret.putAll(parseCookies(it.getValue()));
		
		return ret;
	}
	
	protected void setCookieValue(String value) {
		HTTP_EXCHANGE.getResponseHeaders().add("Set-Cookie", value);
	}
	
	private boolean isCookieEntry(Entry<String, List<String>> httpEntry) {
		boolean ret = HTTP_HEADER_REQUEST_COOKIE.equalsIgnoreCase(httpEntry.getKey());
		return ret;
	}
	
	private Map<String, String> parseCookies(List<String> cookies) {
		Map<String, String> ret = new HashMap<>();
		
		for (String it : cookies) 
			ret.putAll(parseCookies(it));
		
		return ret;
	}
	
	private Map<String, String> parseCookies(String cookies) {
		if (cookies != null)
			return parseCookies(cookies.split(";"));
		
		return new HashMap<>();
	}
	
	private Map<String, String> parseCookies(String[] cookies) {
		Map<String, String> ret = new HashMap<>();
		Arrays.asList(cookies)
			.stream()
			.map(S -> S.split("="))
			.forEach(D -> {
				if (D.length == 2)
					ret.put(D[0], D[1]);
				else 
					ret.put(D[0], "");
			});
			
		return ret;
	}
	
	private Set<Entry<String, List<String>>> getHttpRequestEntries() {
		Set<Entry<String, List<String>>> ret = HTTP_EXCHANGE.getRequestHeaders().entrySet();
		return ret;
	}
	
	protected String getCookieName(String cookie) {
		String ret = getCookieParam(cookie, COOKIE_INDEX_KEY);
		return ret;
	}
	
	protected String getCookieValue(String cookie) {
		String ret = getCookieParam(cookie, COOKIE_INDEX_VALUE);
		return ret;
	}
	
	protected String generateCookieString(String key, String value) {
		return key + "=" + value + ";";
	}
	
	private String getCookieParam(String cookie, int index) {
		String ret = "";
		
		if (isValidCookie(cookie))
			ret = cookie.split("=")[index].trim();
		
		return ret;
	}
	
	private boolean isValidCookie(String cookie) {
		boolean ret = (cookie != null) && cookie.contains("=");
		return ret;
	}

}
