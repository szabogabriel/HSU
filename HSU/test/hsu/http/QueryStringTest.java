package hsu.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class QueryStringTest {
	
	private static QueryString QS;
	
	@Test
	public void empty() {
		QS = new QueryString("");
		
		Map<String, String> data = QS.getData();
		
		assertEquals(data.size(), 0);
	}
	
	@Test
	public void emptyWithPrefix() {
		QS = new QueryString("?");
		
		Map<String, String> data = QS.getData();
		
		assertEquals(data.size(), 0);
	}
	
	@Test
	public void justOneKey() {
		QS = new QueryString("key");
		
		Map<String, String> data = QS.getData();
		
		assertEquals(data.size(), 1);
		assertTrue(data.containsKey("key"));
	}
	
	@Test
	public void multipleKeys() {
		QS = new QueryString("first&second");
		
		Map<String, String> data = QS.getData();
		
		assertEquals(data.size(), 2);
		assertTrue(data.containsKey("first"));
		assertTrue(data.containsKey("second"));
	}

}
