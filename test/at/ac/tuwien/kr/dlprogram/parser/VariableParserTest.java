package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.Term;



public class VariableParserTest {
	@Test
	public void testVariable001() throws ParseException {
		StringReader reader = new StringReader("X");
		ProgramParser parser = new ProgramParser(reader);
		Term result = parser.variable();

		assertEquals("X", result.toString());
	}

	@Test(expected = ParseException.class)
	public void testVariable002() throws ParseException {
		StringReader reader = new StringReader("x"); // only capital letter will succeed
		ProgramParser parser = new ProgramParser(reader);
		parser.variable();
	}
}
