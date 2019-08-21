package hsu.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class CookiesTest {
	
	private static HttpServer server;
	
	private static int counter = 0;
	
	public static void main(String [] args) throws IOException {
		server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.createContext("/cookies", new HttpHandler() {
			@Override
			public void handle(HttpExchange arg0) throws IOException {
				try {
					Set<Entry<String, List<String>>> entries = arg0.getRequestHeaders().entrySet();
					
					for (Entry<String, List<String>> it : entries) {
							System.out.println("Key: " + it.getKey() + ", value: " + it.getValue());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String response = "Heureka! " + (++counter);

				arg0.getResponseHeaders().set("Content-Type", "text/plain");
				
				if (counter % 2 == 1) {
					arg0.getResponseHeaders().add("Set-Cookie", "session=" + System.currentTimeMillis());
					arg0.getResponseHeaders().add("Set-Cookie", "sessionid=" +(System.currentTimeMillis() * 2)+"; HttpOnly=true; Path=/");
				} else {
					arg0.getResponseHeaders().add("Set-Cookie", "session=;");
					arg0.getResponseHeaders().add("Set-Cookie", "sessionid=; HttpOnly=true; Path=/");
				}
				
				arg0.sendResponseHeaders(200, response.getBytes().length);
				arg0.getResponseBody().write(response.getBytes());
				arg0.getResponseBody().close();
			}
		});
		
		server.start();
	}
	

}
