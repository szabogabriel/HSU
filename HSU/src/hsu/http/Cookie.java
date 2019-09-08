package hsu.http;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cookie {
	
	private static SimpleDateFormat SDF = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	
	private String name = null;
	private String value = null;
	private Date expires = null;
	private boolean secure = false;
	private boolean httpOnly = false;
	private String path = null;
	private SameSite sameSite = SameSite.EMPTY;

	public Cookie() {
		
	}
	
	public Cookie(String cookieValue) {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setExpires(long expires) {
		this.expires = new Date(expires);
	}
	
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	
	public void setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setSameSite(SameSite sameSite) {
		this.sameSite = sameSite;
	}
	
	public String toString() {
		String ret = "";
		if (name != null) {
			ret += name;
			if (value != null) ret += "=" + value;
			if (expires != null) ret += "; " + SDF.format(expires);
			if (secure) ret += "; Secure";
			if (httpOnly) ret += "; HttpOnly";
			if (path != null) ret += path;
			if (sameSite != SameSite.EMPTY) ret += "; " + sameSite.toString();
		} else {
			throw new IllegalArgumentException("No name set for cookie.");
		}
		return ret;
	}

	public static enum SameSite {
		STRICT("Strict"),
		LAX("Lax"),
		EMPTY("");
		
		private final String NAME;
		
		private SameSite(String name) {
			NAME = name;
		}
		
		public static SameSite find(String name) {
			for (SameSite it : values())
				if (it.NAME.equals(name))
					return it;
			return EMPTY;
		}
		
		public String toString() {
			return NAME;
		}
	}
}
