package hsu.http;

import java.util.List;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;

import hsu.http.session.Session;

public class SessionHandler extends AbstractCookieHandler {

	private final static String SESSION_ID_COOKIE_NAME = "SESSIONID";

	public SessionHandler(HttpExchange exchange) {
		super(exchange);
	}

	public Session getSession() {
		Optional<String> sessionId = getSessionCookieValue();
		
		Session ret = null;
		if (sessionId.isPresent())
			ret = new Session(sessionId.get(), false);
		else
			ret = new Session(generateSessionId(), true);
		
		return ret;
	}
	
	private Optional<String> getSessionCookieValue() {
		Optional<String> ret = getSessionCookieValue(getCookies());

		return ret;
	}
	
	private Optional<String> getSessionCookieValue(Optional<List<String>> cookies) {
		Optional<String> ret = Optional.empty();
		
		if (cookies.isPresent()) 
			for (String it : cookies.get())
				if (isSessionCookie(it))
					ret = Optional.of(getCookieValue(it));
		
		return ret;
	}
	
	private boolean isSessionCookie(String cookie) {
		boolean ret = (cookie != null) && cookie.startsWith(SESSION_ID_COOKIE_NAME) && cookie.contains("=") && !cookie.endsWith("=");
		return ret;
	}
	
	private String generateSessionId() {
		double ret = (double)System.currentTimeMillis();
		
		ret = ret * Math.random() * ret;
		
		
		return ((long)ret) + "";
	}
	
}
