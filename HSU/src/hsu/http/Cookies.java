package hsu.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;

public class Cookies extends AbstractCookieHandler {
	
	public Cookies(HttpExchange httpExchange) {
		super(httpExchange);
	}
	
	public Map<String, String> getSessionValues() {
		Map<String, String> ret = new HashMap<>();
		
		Optional<List<String>> cookies = getCookies();
		if (cookies.isPresent())
			for (String cookie : cookies.get())
				ret.put(getCookieName(cookie), getCookieValue(cookie));
		
		return ret;
	}
	
	public void addSessionValue(String key, String value) {
		HTTP_EXCHANGE.getResponseHeaders().add(key, value);
	}

}
