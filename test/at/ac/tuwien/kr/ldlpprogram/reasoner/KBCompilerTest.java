package at.ac.tuwien.kr.ldlpprogram.reasoner;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.DLInputSignature;
import at.ac.tuwien.kr.dlprogram.parser.DLProgramParser;
import at.ac.tuwien.kr.dlprogram.parser.ParseException;

public class KBCompilerTest {

	
	
	@Test
	public void testCompileSignature() throws ParseException {
		DLInputSignature lambda1 = new DLProgramParser(new StringReader(
				"S1 += p1, S2 += p1")).dlInputSignature();
		DLInputSignature lambda2 = new DLProgramParser(new StringReader(
				"S1 += p1, S1 += p2")).dlInputSignature();

		KBCompiler compiler = new KBCompiler();
		compiler.compileSignature(lambda1);
		compiler.compileSignature(lambda2);
	}

	@Test
	public void testCompileDLProgramKB() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompileOWLAxiom() {
		fail("Not yet implemented");
	}

}
