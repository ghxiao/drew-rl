package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.Program;



public class ProgramParserTest {
	@Test
	public void testProgram001() throws ParseException {
		String text = "d1(1). d2(1). d3(1). a(X) :- d1(X), not b(X). b(X) :- d1(X), not a(X).";

		StringReader reader = new StringReader(text);
		ProgramParser parser = new ProgramParser(reader);
		Program program = parser.program();

		assertEquals(5, program.getClauses().size());
	}

	@Test
	public void testProgram002() throws ParseException {
		String text = "...";

		StringReader reader = new StringReader(text);
		ProgramParser parser = new ProgramParser(reader);
		Program program = parser.program();

		assertEquals(0, program.getClauses().size());
	}
}
