package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.Map;

import org.junit.Test;

public class NamespaceParserTest {
	@Test
	public void testNamespace001() throws ParseException {
		String key = "test";
		String value = "http://www.test.com/test#";
		String text = String.format("#namespace(\"%s\",\"%s\").", key, value);

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		parser.namespace();
		String message = String.format(
				"<%s,%s> should be contained in namespace declareation", key,
				value);
		Map<String, String> namespaces = parser.namespaces;
		assertTrue(message, namespaces.containsKey(key)
				&& namespaces.get(key).equals(value));
	}

	@Test()
	public void testNamespace002() throws ParseException {
		String key = "test";
		String value = "http://www.test.com/test#";
		String text = String.format("#namespace(\"%s\",\"%s\").#namespace(\"%s1\",\"%s1\").", key, value,key,value);

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		parser.namespace();
		parser.namespace();
		String message = String.format(
				"<%s,%s> should be contained in namespace declareation", key,
				value);
		Map<String, String> namespaces = parser.namespaces;
		assertTrue(message, namespaces.containsKey(key)
				&& namespaces.get(key).equals(value));
		assertEquals(2,namespaces.size());
	}
}
