package hsu.post;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.net.httpserver.HttpExchange;

public class PostMultipart {
	
	private final PostMultipartHandler HANDLER;
	
	public PostMultipart(PostMultipartHandler handler) {
		HANDLER = handler;
	}
	
	public void upload(HttpExchange exchange) {
		try {
			List<FileItem> result = createPostMessageItems(exchange);
			
			for (FileItem fi : result) {
				handle(fi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handle(FileItem fileItem) {
		String fieldName = fileItem.getFieldName();
		if (HANDLER.isFilePart(fieldName)) {
			handleFileUpload(fieldName, fileItem);
		} else {
			handleParameterUpload(fieldName, fileItem);
		}
	}
	
	private void handleFileUpload(String fieldName, FileItem fileItem) {
		try {
			String fileName = fileItem.getName();
			File targetFolder = HANDLER.getTargetFolder();
			File targetFile = new File(targetFolder, fileName);
			fileItem.write(targetFile);
			HANDLER.handle(fieldName, targetFile);
		} catch (Exception e) {
			HANDLER.error(e, fieldName);
		}
	}
	
	private void handleParameterUpload(String fieldName, FileItem fileItem) {
		try {
			HANDLER.handle(fieldName, new String(fileItem.get()));
		} catch (Exception e) {
			HANDLER.error(e, fieldName);
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
