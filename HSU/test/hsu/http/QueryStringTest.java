package hsu.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class QueryStringTest {
	
	private static QueryString QS;
	
	private Map<String, String> prepare(String data) {
		QS = new QueryString(data);
		
		Map<String, String> ret = QS.getData();
		return ret;
	}
	
	@Test
	public void empty() {
		Map<String, String> data = prepare("");
		
		assertEquals(data.size(), 0);
	}
	
	@Test
	public void emptyWithPrefix() {
		Map<String, String> data = prepare("?");
		
		assertEquals(data.size(), 0);
	}
	
	@Test
	public void justOneKey() {
		Map<String, String> data = prepare("key");
		
		assertEquals(data.size(), 1);
		assertTrue(data.containsKey("key"));
	}
	
	@Test
	public void multipleKeys() {
		Map<String, String> data = prepare("first&second");
		
		assertEquals(data.size(), 2);
		assertTrue(data.containsKey("first"));
		assertTrue(data.containsKey("second"));
	}
	
	@Test
	public void singleKeyValueWithoutPrefix() {
		Map<String, String> data = prepare("key=value");
		
		assertEquals(data.size(), 1);
		assertTrue(data.containsKey("key"));
		assertEquals(data.get("key"), "value");
	}
	
	@Test
	public void singleKeyValueWithPrefix() {
		Map<String, String> data = prepare("?key=value");
		
		assertEquals(data.size(), 1);
		assertTrue(data.containsKey("key"));
		assertEquals(data.get("key"), "value");
	}
	
	@Test
	public void multipleKeyValue() {
		Map<String, String> data = prepare("key1=value1&key2=value2");
		
		assertEquals(data.size(), 2);
		assertTrue(data.containsKey("key1"));
		assertEquals(data.get("key1"), "value1");
		assertTrue(data.containsKey("key2"));
		assertEquals(data.get("key2"), "value2");
	}
	
	@Test
	public void multipleKeyValueWithPrefix() {
		Map<String, String> data = prepare("?key1=value1&key2=value2");
		
		assertEquals(data.size(), 2);
		assertTrue(data.containsKey("key1"));
		assertEquals(data.get("key1"), "value1");
		assertTrue(data.containsKey("key2"));
		assertEquals(data.get("key2"), "value2");
	}
	
	@Test
	public void mixedKeyValue() {
		Map<String, String> data = prepare("?key1=value1&key2&key3=value3");
		
		assertEquals(data.size(), 3);
		assertTrue(data.containsKey("key1"));
		assertEquals(data.get("key1"), "value1");
		assertTrue(data.containsKey("key2"));
		assertEquals(data.get("key2"), "");
		assertTrue(data.containsKey("key3"));
		assertEquals(data.get("key3"), "value3");
	}
	
	@Test
	public void keyWithEqualsButNoData() {
		Map<String, String> data = prepare("key1=");
		
		assertEquals(data.size(), 1);
		assertTrue(data.containsKey("key1"));
		assertEquals(data.get("key1"), "");
	}

}
