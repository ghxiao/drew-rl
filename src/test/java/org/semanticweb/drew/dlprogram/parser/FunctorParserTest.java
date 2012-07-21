package org.semanticweb.drew.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;
import org.semanticweb.drew.dlprogram.Functor;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;



public class FunctorParserTest {
	@Test
	public void testFunctor001() throws ParseException {
		StringReader reader = new StringReader("abs");
		DLProgramParser parser = new DLProgramParser(reader);
		Functor result = parser.functor();

		assertEquals("abs", result.toString());
	}

	@Test(expected = ParseException.class)
	public void testFunctor002() throws ParseException {
		StringReader reader = new StringReader("Abs"); // capital letter should
		// fail
		DLProgramParser parser = new DLProgramParser(reader);
		parser.functor();
	}
}
