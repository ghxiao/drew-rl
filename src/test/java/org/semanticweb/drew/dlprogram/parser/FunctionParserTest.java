package org.semanticweb.drew.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;
import org.semanticweb.drew.dlprogram.Function;
import org.semanticweb.drew.dlprogram.Term;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;



/**
 * Test basic function parser. Complex tests are included in term parser test suite.
 * 
 * @author Samuel
 * 
 */
public class FunctionParserTest {
	@Test
	public void testFunction001() throws ParseException {
		StringReader reader = new StringReader("max(Y, X)");
		DLProgramParser parser = new DLProgramParser(reader);
		Function result = parser.function();

		assertEquals("max(Y, X)", result.toString());
	}

	@Test
	public void testFunction002() throws ParseException {
		StringReader reader = new StringReader("abs(X1 - X2)");
		DLProgramParser parser = new DLProgramParser(reader);
		Function result = parser.function();

		assertEquals("abs(X1 - X2)", result.toString());
	}

	@Test
	public void testMultiplicative001() throws ParseException {
		StringReader reader = new StringReader("X1*X2");
		DLProgramParser parser = new DLProgramParser(reader);
		Term result = parser.multiplicative();

		assertEquals("X1 * X2", result.toString());
	}

	@Test
	public void testMultiplicative002() throws ParseException {
		StringReader reader = new StringReader("X1/X4");
		DLProgramParser parser = new DLProgramParser(reader);
		Term result = parser.multiplicative();

		assertEquals("X1 / X4", result.toString());
	}

	@Test
	public void testAdditive001() throws ParseException {
		StringReader reader = new StringReader("X1+X2");
		DLProgramParser parser = new DLProgramParser(reader);
		Term result = parser.additive();

		assertEquals("X1 + X2", result.toString());
	}

	@Test
	public void testAdditive002() throws ParseException {
		StringReader reader = new StringReader("X1-X4");
		DLProgramParser parser = new DLProgramParser(reader);
		Term result = parser.additive();

		assertEquals("X1 - X4", result.toString());
	}
}
