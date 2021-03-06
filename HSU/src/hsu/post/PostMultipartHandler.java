package hsu.post;

import java.io.File;

public interface PostMultipartHandler {
	
	boolean isFilePart(String name);
	
	File getTargetFolder();
	
	void handle(String name, String value);
	
	void handle(String name, File value);
	
	void error(Exception e, String name);

}
