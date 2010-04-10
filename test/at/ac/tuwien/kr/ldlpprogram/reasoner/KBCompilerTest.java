package at.ac.tuwien.kr.ldlpprogram.reasoner;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Set;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.DLInputSignature;
import at.ac.tuwien.kr.dlprogram.DLProgram;
import at.ac.tuwien.kr.dlprogram.parser.DLProgramParser;
import at.ac.tuwien.kr.dlprogram.parser.ParseException;

public class KBCompilerTest {

	
	
	@Test
	public void testCompileSignature001() throws ParseException {
		String text = "q :- DL[S1 += p1, S2 += p1;S1](a), DL[S1 += p1, E2 += q2;E2](a,b). p1(c). q2(d).";
		DLProgram program = new DLProgramParser(new StringReader(
		text)).program();
		
		Set<DLInputSignature> dlInputSignatures = program.getDLInputSignatures();
		
		assertEquals(2, dlInputSignatures.size());
		
		KBCompiler compiler = new KBCompiler();
		
		for(DLInputSignature signature:dlInputSignatures){
			compiler.compileSignature(signature);
		}	
	}
	
	@Test
	public void testCompileSignature002() throws ParseException {
		String text = "q :- DL[S1 += p1, S2 += p1;S1](a), DL[;E2](a,b). p1(c). q2(d).";
		DLProgram program = new DLProgramParser(new StringReader(
		text)).program();
		
		Set<DLInputSignature> dlInputSignatures = program.getDLInputSignatures();
		
		assertEquals(2, dlInputSignatures.size());
		
		KBCompiler compiler = new KBCompiler();
		
		for(DLInputSignature signature:dlInputSignatures){
			compiler.compileSignature(signature);
		}	
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
