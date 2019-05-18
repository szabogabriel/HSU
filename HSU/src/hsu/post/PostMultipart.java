package hsu.post;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.net.httpserver.HttpExchange;

public class PostMultipart {
	
	private final Map<String, WeakReference<PostMultipartHandler>> HANDLERS = new HashMap<>();
	
	public void addHandler(String fieldName, PostMultipartHandler handler) {
		if (fieldName != null && handler != null) {
			HANDLERS.put(fieldName, new WeakReference<>(handler));
		}
	}
	
	private void handle(String fieldName, FileItem fileItem) {
		if (HANDLERS.containsKey(fieldName)) {
			PostMultipartHandler handler = HANDLERS.get(fieldName).get();
			if (handler != null) {
				handle(fieldName, fileItem, handler);
			}
		}
	}
	
	private void handle(String fieldName, FileItem fileItem, PostMultipartHandler handler) {
		if (handler.isFilePart()) {
			try {
				File targetFolder = handler.getTargetFolder();
				String fileName = fileItem.getName();
				File targetFile = new File(targetFolder, fileName);
				fileItem.write(targetFile);
				handler.handle(fieldName, targetFile);
			} catch (Exception e) {
				handler.error(e);
			}
		} else {
			handler.handle(fieldName, new String(fileItem.get()));
		}
	}
	
	public void upload(HttpExchange exchange) {
		try {
			List<FileItem> result = createPostMessageItems(exchange);
			
			for (FileItem fi : result) {
				String fieldName = fi.getFieldName();
				
				handle(fieldName, fi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<FileItem> createPostMessageItems(HttpExchange exchange) throws FileUploadException {
		DiskFileItemFactory d = new DiskFileItemFactory();
		ServletFileUpload up = new ServletFileUpload(d);
		List<FileItem> result = up.parseRequest(new RequestContext() {
			@Override
			public String getCharacterEncoding() {
				return "UTF-8";
			}
			@Override
			public int getContentLength() {
				return 0; // tested to work with 0 as return
			}
			@Override
			public String getContentType() {
				return exchange.getRequestHeaders().getFirst("Content-type");
			}
			@Override
			public InputStream getInputStream() throws IOException {
				return exchange.getRequestBody();
			}
		});
		return result;
	}

}
