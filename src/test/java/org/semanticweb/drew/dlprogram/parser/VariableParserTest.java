package org.semanticweb.drew.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;
import org.semanticweb.drew.dlprogram.Term;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;




public class VariableParserTest {
	@Test
	public void testVariable001() throws ParseException {
		StringReader reader = new StringReader("X");
		DLProgramParser parser = new DLProgramParser(reader);
		Term result = parser.variable();

		assertEquals("X", result.toString());
	}

	@Test(expected = ParseException.class)
	public void testVariable002() throws ParseException {
		StringReader reader = new StringReader("x"); // only capital letter will succeed
		DLProgramParser parser = new DLProgramParser(reader);
		parser.variable();
	}
}
