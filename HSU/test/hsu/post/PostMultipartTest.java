package hsu.post;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class PostMultipartTest {
	
	private static String page =
			  "<html>"
			+ "  <head>"
			+ "    <title>Test</title>"
			+ "  </head>"
			+ "  <body>"
			+ "    <h1>Test page</h1>"
			+ "    <form method='POST' action='/test' enctype='multipart/form-data' >"
			+ "      <input type='text' name='first' id='first'></input><br/>"
			+ "      <input type='text' name='second' id='second'></input><br/>"
			+ "      <input type='submit' value='test' name='test' id='test'/>"
			+ "    </form>"
			+ "  </body>"
			+ "</html>";
	
	private static PostMultipart pm;
	
	public static void main(String [] args) throws IOException {
		initUploadHandler();
		
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		
		server.createContext("/test", new HttpHandler() {
			@Override
			public void handle(HttpExchange arg0) throws IOException {
				String method = arg0.getRequestMethod();
				
				if ("GET".equalsIgnoreCase(method)) {
					arg0.getResponseHeaders().add("Content-Type", "text/html");
					arg0.sendResponseHeaders(200, page.length());
					arg0.getResponseBody().write(page.getBytes());
					arg0.getResponseBody().close();
				} else
				if ("POST".equalsIgnoreCase(method)) {
					pm.upload(arg0);
					arg0.getResponseHeaders().add("Content-Type", "text/html");
					arg0.sendResponseHeaders(200, page.length());
					arg0.getResponseBody().write(page.getBytes());
					arg0.getResponseBody().close();
				}
			}
		});
		
		server.start();
	}
	
	private static void initUploadHandler() {
		pm = new PostMultipart(new PostMultipartHandler() {
			@Override
			public boolean isFilePart(String name) {
				return false;
			}
			
			@Override
			public void handle(String name, File value) {
			}
			
			@Override
			public void handle(String name, String value) {
				System.out.println("Handling name: " + name + ", value: " + value);
			}
			
			@Override
			public File getTargetFolder() {
				return null;
			}
			
			@Override
			public void error(Exception e, String name) {
			}
		});
	}

}
