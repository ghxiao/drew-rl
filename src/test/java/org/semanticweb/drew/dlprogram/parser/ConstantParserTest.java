package org.semanticweb.drew.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.sql.Types;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.drew.dlprogram.Constant;
import org.semanticweb.drew.dlprogram.OptionManager;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;




public class ConstantParserTest {
	
	@Before
	public void setUp(){
		OptionManager.getInstance().setCompatibleMode(false);
	}
	
	@Test
	public void testString001() throws ParseException {
		String text = "\"mike\"";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		Constant result = parser.constant();

		assertEquals(text, result.toString());
		assertEquals(Types.VARCHAR, result.getType());
	}

	@Test
	public void testString002() throws ParseException {
		String text = "\"汉字\"";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		Constant result = parser.constant();

		assertEquals(text, result.toString());
		assertEquals(Types.VARCHAR, result.getType());
	}

	@Test
	public void testString003() throws ParseException {
		String text = "\"中國\"";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		Constant result = parser.constant();

		assertEquals(text, result.toString());
		assertEquals(Types.VARCHAR, result.getType());
	}

	@Test
	public void testString004() throws ParseException {
		String text = "\"システム\"";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		Constant result = parser.constant();

		assertEquals(text, result.toString());
		assertEquals(Types.VARCHAR, result.getType());
	}

	public void testString005() throws ParseException {
		StringReader reader = new StringReader("mike"); // now does not need single quotation marks
		DLProgramParser parser = new DLProgramParser(reader);
		parser.constant();
	}

	public void testString006() throws ParseException {
		StringReader reader = new StringReader("Mike"); // now does not need single quotation marks
		DLProgramParser parser = new DLProgramParser(reader);
		parser.constant();
	}

	@Test
	public void testInteger001() throws ParseException {
		String text = "0";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		Constant result = parser.constant();

		assertEquals(text, result.toString());
		assertEquals(Types.INTEGER, result.getType());
	}

	@Test
	public void testInteger002() throws ParseException {
		String text = "1";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		Constant result = parser.constant();

		assertEquals(text, result.toString());
		assertEquals(Types.INTEGER, result.getType());
	}

	@Test
	public void testInteger003() throws ParseException {
		String text = "-1";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		Constant result = parser.constant();

		assertEquals(text, result.toString());
		assertEquals(Types.INTEGER, result.getType());
	}

	@Test
	public void testInteger004() throws ParseException {
		// only 0 will be parsed as an integer and error will be throw at grammar level
		String text = "01";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		Constant result = parser.constant();

		assertEquals("0", result.toString());
		assertEquals(Types.INTEGER, result.getType());
	}

	@Test(expected = ParseException.class)
	public void testInteger005() throws ParseException {
		String text = "-01";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		parser.constant();
	}
}