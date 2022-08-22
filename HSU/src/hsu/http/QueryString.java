package hsu.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QueryString {
	
	private Map<String, String> DATA = new HashMap<>();
	
	public QueryString(String qs) {
		String[] data = strip(qs).split("&");
		
		saveData(data);
	}
	
	public Map<String, String> getData() {
		return DATA;
	}
	
	public Optional<String> getData(String key) {
		if (DATA.containsKey(key))
			return Optional.of(DATA.get(key));
		
		return Optional.empty();
	}
	
	private String strip(String data) {
		if (data.startsWith("?"))
			return data.substring(1);
		
		return data;
	}
	
	private void saveData(String[] data) {
		for (String it : data) {
			String [] d = it.split("=");
			if (d.length == 2) {
				if (d[0].length() > 0) {
					saveData(d[0], d[1]);
				}
			} else {
				if (d[0].length() > 0) {
					saveData(d[0], "");
				}
			}
		}
	}
	
	private void saveData(String key, String value) {
		String tmpValue = value;
		if (DATA.containsKey(key) && DATA.get(key).trim().length() > 0 && value.trim().length() > 0) {
			tmpValue = DATA.get(key) + ", " + value;
		}
		DATA.put(key, tmpValue);
	}
	
}
