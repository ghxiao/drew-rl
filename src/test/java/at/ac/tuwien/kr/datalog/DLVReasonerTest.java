package at.ac.tuwien.kr.datalog;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.Literal;
import at.ac.tuwien.kr.dlprogram.parser.DLProgramParser;
import at.ac.tuwien.kr.dlprogram.parser.ParseException;

public class DLVReasonerTest {

	@Test
	public void testQuery001() throws ParseException {
		StringReader reader = new StringReader("p(a). p(b):-p(a).");
		DLProgramParser parser = new DLProgramParser(reader);
		DLProgram program = parser.program();

		reader = new StringReader("p(a)");
		parser = new DLProgramParser(reader);
		Literal query = parser.literal();
		DLVReasoner reasoner = new DLVReasoner();
		assertTrue(reasoner.isEntailed(program, query));
	}

	@Test
	public void testQuery002() throws ParseException {
		StringReader reader = new StringReader("p(a). p(b):-p(a).");
		DLProgramParser parser = new DLProgramParser(reader);
		DLProgram program = parser.program();

		reader = new StringReader("p(c)");
		parser = new DLProgramParser(reader);
		Literal query = parser.literal();

		DLVReasoner reasoner = new DLVReasoner();
		assertFalse(reasoner.isEntailed(program, query));
	}

}
