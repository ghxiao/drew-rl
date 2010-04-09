package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.Term;



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
