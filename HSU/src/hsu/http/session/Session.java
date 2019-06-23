package hsu.http.session;

public class Session {
	
	private final String ID;
	private final boolean IS_NEW;
	
	public Session(String id, boolean newSession) {
		ID = id;
		IS_NEW = newSession;
	}
	
	public boolean isNew() {
		return IS_NEW;
	}
	
	public String getId() {
		return ID;
	}

}
