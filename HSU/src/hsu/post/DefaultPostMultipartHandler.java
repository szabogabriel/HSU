package hsu.post;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultPostMultipartHandler implements PostMultipartHandler {
	
	private final File TARGET_UPLOAD_DIR;
	private final Set<String> FILES;
	private final Set<String> VALUES;
	private final Map<String, UploadedElement> UPLOADED_ELEMENTS = new HashMap<>(); 
	
	
	public DefaultPostMultipartHandler(File targetUploadFolder, String[] files, String[] values) {
		TARGET_UPLOAD_DIR = targetUploadFolder;
		FILES = Arrays.asList(files).stream().collect(Collectors.toSet());
		VALUES = Arrays.asList(values).stream().collect(Collectors.toSet());
	}

	@Override
	public boolean isFilePart(String name) {
		return FILES.contains(name);
	}

	@Override
	public File getTargetFolder() {
		return TARGET_UPLOAD_DIR;
	}

	@Override
	public void handle(String name, String value) {
		if (VALUES.contains(value)) {
			UPLOADED_ELEMENTS.put(name, new UploadedElement(value));
		}
	}

	@Override
	public void handle(String name, File value) {
		if (FILES.contains(name)) {
			UPLOADED_ELEMENTS.put(name, new UploadedElement(value));
		}
	}

	@Override
	public void error(Exception e, String name) {
		System.err.println("Error handling upload of parameter with name: " + name);
		System.err.println("Error is: " + e);
	}
	
	public Map<String, UploadedElement> getParameters() {
		return new HashMap<String, UploadedElement>(UPLOADED_ELEMENTS);
	}

}
