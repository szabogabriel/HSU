package hsu.http;

import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class Cookies extends AbstractCookieHandler {
	
	public Cookies(HttpExchange httpExchange) {
		super(httpExchange);
	}
	
	public Map<String, String> getValues() {
		Map<String, String> ret = new HashMap<>();
		
		Map<String, String> cookies = getCookies();
		if (cookies.size() > 0)
			ret.putAll(cookies);
		
		return ret;
	}
	
	public void setValue(String key, String value) {
		setCookieValue(generateCookieString(key, value));
	}

}
