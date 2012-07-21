package org.semanticweb.drew.datalog;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.junit.Test;
import org.semanticweb.drew.datalog.DLVReasoner;
import org.semanticweb.drew.dlprogram.DLProgram;
import org.semanticweb.drew.dlprogram.Literal;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;


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
