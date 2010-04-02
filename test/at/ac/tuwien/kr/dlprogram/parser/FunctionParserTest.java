package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.Function;
import at.ac.tuwien.kr.dlprogram.Term;


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
		ProgramParser parser = new ProgramParser(reader);
		Function result = parser.function();

		assertEquals("max(Y, X)", result.toString());
	}

	@Test
	public void testFunction002() throws ParseException {
		StringReader reader = new StringReader("abs(X1 - X2)");
		ProgramParser parser = new ProgramParser(reader);
		Function result = parser.function();

		assertEquals("abs(X1 - X2)", result.toString());
	}

	@Test
	public void testMultiplicative001() throws ParseException {
		StringReader reader = new StringReader("X1*X2");
		ProgramParser parser = new ProgramParser(reader);
		Term result = parser.multiplicative();

		assertEquals("X1 * X2", result.toString());
	}

	@Test
	public void testMultiplicative002() throws ParseException {
		StringReader reader = new StringReader("X1/X4");
		ProgramParser parser = new ProgramParser(reader);
		Term result = parser.multiplicative();

		assertEquals("X1 / X4", result.toString());
	}

	@Test
	public void testAdditive001() throws ParseException {
		StringReader reader = new StringReader("X1+X2");
		ProgramParser parser = new ProgramParser(reader);
		Term result = parser.additive();

		assertEquals("X1 + X2", result.toString());
	}

	@Test
	public void testAdditive002() throws ParseException {
		StringReader reader = new StringReader("X1-X4");
		ProgramParser parser = new ProgramParser(reader);
		Term result = parser.additive();

		assertEquals("X1 - X4", result.toString());
	}
}
