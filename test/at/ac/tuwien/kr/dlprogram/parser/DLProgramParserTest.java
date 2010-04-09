package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.Clause;
import at.ac.tuwien.kr.dlprogram.DLProgram;

public class DLProgramParserTest {
	@Test
	public void testProgram001() throws ParseException {
		String text = "d1(1). d2(1). d3(1). a(X) :- d1(X), not b(X). b(X) :- d1(X), not a(X).";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		DLProgram program = parser.program();

		assertEquals(5, program.getClauses().size());
	}

	@Test
	public void testProgram002() throws ParseException {
		String text = "...";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		DLProgram program = parser.program();

		assertEquals(0, program.getClauses().size());
	}

	// dl-program
	@Test
	public void testProgram003() throws ParseException {
		String text = "p(a). s(a). s(b). q:-DL[C+=s;D](a),not DL[C+=p;D](b).";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		DLProgram program = parser.program();

		assertEquals(4, program.getClauses().size());
		for (Clause clause : program.getClauses()) {
			System.out.println(clause);
		}
	}

	// dl-program
	@Test
	public void testProgram004() throws ParseException {
		String text = "";
		text = text + "good(X):-DL[;Super](X,Y), not DL[PapToRev+=paper;Over](Y).";
		text = text + "over(X):-not good(X).";
		text = text + "paper(b,p1).";
		text = text + "paper(b,p2).";

		StringReader reader = new StringReader(text);
		DLProgramParser parser = new DLProgramParser(reader);
		DLProgram program = parser.program();

		assertEquals(4, program.getClauses().size());
		for (Clause clause : program.getClauses()) {
			System.out.println(clause);
		}
	}
}
