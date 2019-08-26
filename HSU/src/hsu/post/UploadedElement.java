package hsu.post;

import java.io.File;

public class UploadedElement {
	
	private final String VALUE;
	private final File FILE;
	
	public UploadedElement(String value) {
		VALUE = value;
		FILE = null;
	}
	
	public UploadedElement(File file) {
		VALUE = null;
		FILE = file;
	}
	
	public boolean isFile() {
		return FILE != null;
	}
	
	public boolean isValue() {
		return VALUE != null;
	}
	
	public File getFile() {
		return FILE;
	}
	
	public String getValue() {
		return VALUE;
	}

}
