package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.Literal;


public class LiteralParserTest {
	@Test
	public void testLiteral001() throws ParseException {
		StringReader reader = new StringReader("q(X,Y)");
		ProgramParser parser = new ProgramParser(reader);
		Literal result = parser.literal();

		assertEquals("q(X, Y)", result.toString());
	}

	@Test
	public void testLiteral002() throws ParseException {
		StringReader reader = new StringReader("abs(X1-X2)=abs(Y1-Y2)");
		ProgramParser parser = new ProgramParser(reader);
		Literal result = parser.literal();

		assertEquals("abs(X1 - X2) = abs(Y1 - Y2)", result.toString());
	}

	@Test(expected = ParseException.class)
	public void testLiteral003() throws ParseException {
		StringReader reader = new StringReader("abs(X1-X2)==abs(Y1-Y2)"); // error operator
		ProgramParser parser = new ProgramParser(reader);
		parser.literal();
	}

	@Test
	public void testLiteral004() throws ParseException {
		StringReader reader = new StringReader("X1 != X2");
		ProgramParser parser = new ProgramParser(reader);
		Literal result = parser.literal();

		assertEquals("X1 != X2", result.toString());
	}

	@Test(expected = ParseException.class)
	public void testLiteral005() throws ParseException {
		StringReader reader = new StringReader("_abs(X1-X2)"); // no underscore in predicate name
		ProgramParser parser = new ProgramParser(reader);
		parser.literal();
	}
}
