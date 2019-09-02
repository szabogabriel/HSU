package hsu.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;

import hsu.http.session.Session;

public class SessionHandler extends AbstractCookieHandler {

	private final static String SESSION_ID_COOKIE_NAME = "SESSIONID";
	
	private final static Map<String, Long> LAST_ACCESSED = new HashMap<>();

	public SessionHandler(HttpExchange exchange) {
		super(exchange);
	}

	public Session getSession() {
		Optional<String> sessionId = getSessionCookieValue();
		
		Session ret = null;
		if (sessionId.isPresent())
			ret = new Session(sessionId.get(), (LAST_ACCESSED.containsKey(sessionId.get()) ? LAST_ACCESSED.get(sessionId.get()) : 0L),false);
		else {
			String sid = generateSessionId();
			ret = new Session(sid, System.currentTimeMillis(), true);
			setCookieValue(generateCookie(sid));
		}
		
		return ret;
	}
	
	public void removeSession() {
		setCookieValue(generateCookie(""));
	}
	
	private Optional<String> getSessionCookieValue() {
		Optional<String> ret = getSessionCookieValue(getCookies());

		return ret;
	}
	
	private Optional<String> getSessionCookieValue(Map<String, String> cookies) {
		Optional<String> ret = Optional.empty();
		
		if (cookies.containsKey(SESSION_ID_COOKIE_NAME))
			ret = Optional.of(cookies.get(SESSION_ID_COOKIE_NAME));
		
		return ret;
	}
	
	private String generateSessionId() {
		double ret = (double)System.currentTimeMillis();
		
		ret = ret * Math.random() * ret;
		
		return ((long)ret) + "";
	}
	
	private String generateCookie(String sid) {
		return generateCookieString(SESSION_ID_COOKIE_NAME, sid);
	}
	
}
