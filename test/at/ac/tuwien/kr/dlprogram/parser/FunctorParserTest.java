package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.Functor;


public class FunctorParserTest {
	@Test
	public void testFunctor001() throws ParseException {
		StringReader reader = new StringReader("abs");
		ProgramParser parser = new ProgramParser(reader);
		Functor result = parser.functor();

		assertEquals("abs", result.toString());
	}

	@Test(expected = ParseException.class)
	public void testFunctor002() throws ParseException {
		StringReader reader = new StringReader("Abs"); // capital letter should
		// fail
		ProgramParser parser = new ProgramParser(reader);
		parser.functor();
	}
}
