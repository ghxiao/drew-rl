package org.semanticweb.drew.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;
import org.semanticweb.drew.dlprogram.Term;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;




/**
 * Test complex expressions.
 * 
 * @author Samuel
 * 
 */
public class TermParserTest {
	@Test
	public void testFunction001() throws ParseException {
		StringReader reader = new StringReader("(Y      -X)*Z+A/(B*C)");
		DLProgramParser parser = new DLProgramParser(reader);
		Term result = parser.term();

		assertEquals("(Y - X) * Z + A / (B * C)", result.toString());
	}

	@Test
	public void testFunction002() throws ParseException {
		StringReader reader = new StringReader("abs(X1-X2)+X3");
		DLProgramParser parser = new DLProgramParser(reader);
		Term result = parser.term();

		assertEquals("abs(X1 - X2) + X3", result.toString());
	}
}
