package hsu.http.session;

public class Session {
	
	private final String ID;
	private final long LAST_ACCESSED;
	private final boolean IS_NEW;
	
	public Session(String id, long lastAccessed, boolean newSession) {
		ID = id;
		LAST_ACCESSED = lastAccessed;
		IS_NEW = newSession;
	}
	
	public boolean isNew() {
		return IS_NEW;
	}
	
	public long lastAccessed() {
		return LAST_ACCESSED;
	}
	
	public String getId() {
		return ID;
	}

}
