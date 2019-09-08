package hsu.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HsuTest {

	private static HttpServer server;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
	
	private static String PAGE = "<html><head><title>Test</title></head>"
			+ "<body>"
			+ "<p>Previous value: ##value##</p>"
			+ "<a href='/hsu'>get value</a><br/>"
			+ "</body></html>";
	
	public static void main(String [] args) throws IOException {
		server = HttpServer.create(new InetSocketAddress(8080), 0);
		
		server.createContext("/hsu", new HttpHandler() {

			@Override
			public void handle(HttpExchange arg0) throws IOException {
				HsuHttpExchange hsu = new HsuHttpExchange(arg0);
				
				String previousValue = hsu.getValues().get("test") + "";
				
				hsu.setValue("test", "value_" + sdf.format(new Date(System.currentTimeMillis())));
				
				String content = PAGE.replaceAll("##value##", previousValue);
				
				arg0.sendResponseHeaders(200, content.length());
				arg0.getResponseBody().write(content.getBytes());
				arg0.getResponseBody().close();
			}
		});
		
		server.start();
	}
	
}
