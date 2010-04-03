package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.Clause;


public class ClauseParserTest {
	@Test
	public void testFact001() throws ParseException {
		final String text = "foo(\"a\").";

		StringReader reader = new StringReader(text);
		ProgramParser parser = new ProgramParser(reader);
		Clause clause = parser.clause();

		assertEquals(text, clause.toString());
	}

	@Test
	public void testRule001() throws ParseException {
		StringReader reader = new StringReader("e(X):-d(X),d(Y),X !=Y.");
		ProgramParser parser = new ProgramParser(reader);
		Clause clause = parser.clause();

		assertEquals("e(X) :- d(X), d(Y), X != Y.", clause.toString());
	}

	@Test
	public void testConstraint001() throws ParseException {
		String text = ":- d(X1), d(X2), d(Y1), d(Y2), q(X1, Y1), q(X2, Y2), X1 != X2, Y1 != Y2, abs(X1 - X2) = abs(Y1 - Y2).";

		StringReader reader = new StringReader(text);
		ProgramParser parser = new ProgramParser(reader);
		Clause clause = parser.clause();

		assertEquals(text, clause.toString());
	}
}
