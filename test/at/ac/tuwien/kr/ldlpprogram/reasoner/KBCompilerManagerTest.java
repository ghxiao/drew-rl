package at.ac.tuwien.kr.ldlpprogram.reasoner;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.DLInputSignature;
import at.ac.tuwien.kr.dlprogram.parser.DLProgramParser;
import at.ac.tuwien.kr.dlprogram.parser.ParseException;

public class KBCompilerManagerTest {

	@Test
	public void testGetSubscript() throws ParseException {
		KBCompilerManager manager = KBCompilerManager.getInstance();
		DLInputSignature lambda1 = new DLProgramParser(new StringReader(
				"S1 += p1, S2 += p1")).dlInputSignature();
		DLInputSignature lambda2 = new DLProgramParser(new StringReader(
				"S1 += p1, S1 += p2")).dlInputSignature();
		assertEquals("1", manager.getSubscript(lambda1));
		assertEquals("2", manager.getSubscript(lambda2));
	}

}
