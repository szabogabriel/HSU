package hsu.http;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;

import com.sun.net.httpserver.HttpExchange;

public class AbstractCookieHandler {
	
	private final static String HTTP_HEADER_REQUEST_COOKIE = "Cookie";
	private final static int COOKIE_INDEX_KEY = 0;
	private final static int COOKIE_INDEX_VALUE = 1;
	
	protected final HttpExchange HTTP_EXCHANGE;
	
	public AbstractCookieHandler(HttpExchange exchange) {
		HTTP_EXCHANGE = exchange;
	}
	
	protected Optional<List<String>> getCookies() {
		Optional<List<String>> ret = Optional.empty();
		
		for (Entry<String, List<String>> it : getHttpRequestEntries())
			if (isCookieEntry(it)) 
				ret = Optional.of(it.getValue());
		
		return ret;
	}
	
	protected void setCookieValue(String value) {
		HTTP_EXCHANGE.getResponseHeaders().add("Set-Cookie", value);
	}
	
	private boolean isCookieEntry(Entry<String, List<String>> httpEntry) {
		boolean ret = HTTP_HEADER_REQUEST_COOKIE.equalsIgnoreCase(httpEntry.getKey());
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
