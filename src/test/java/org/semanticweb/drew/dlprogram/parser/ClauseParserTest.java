package org.semanticweb.drew.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.drew.dlprogram.Clause;
import org.semanticweb.drew.dlprogram.OptionManager;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;



public class ClauseParserTest {
	
	@Before
	public void setUp(){
		OptionManager.getInstance().setCompatibleMode(false);
	}
	
	@Test
	public void testFact001() throws ParseException {
		final String text = "foo(\"a\").";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		Clause clause = parser.clause();

		assertEquals(text, clause.toString());
	}

	@Test
	public void testRule001() throws ParseException {
		StringReader reader = new StringReader("e(X):-d(X),d(Y),X !=Y.");
		DLProgramParser parser = new DLProgramParser(reader);
		Clause clause = parser.clause();

		assertEquals("e(X) :- d(X), d(Y), X != Y.", clause.toString());
	}

	@Test
	public void testRule002() throws ParseException {
		StringReader reader = new StringReader("over(X):-not good(X).");
		DLProgramParser parser = new DLProgramParser(reader);
		Clause clause = parser.clause();

		assertEquals("over(X) :- not good(X).", clause.toString());
	}
	
	@Test
	public void testConstraint001() throws ParseException {
		String text = ":- d(X1), d(X2), d(Y1), d(Y2), q(X1, Y1), q(X2, Y2), X1 != X2, Y1 != Y2, abs(X1 - X2) = abs(Y1 - Y2).";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		Clause clause = parser.clause();

		assertEquals(text, clause.toString());
	}
	
	
}
