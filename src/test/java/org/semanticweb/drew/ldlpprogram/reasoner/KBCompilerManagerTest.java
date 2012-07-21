package org.semanticweb.drew.ldlpprogram.reasoner;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;
import org.semanticweb.drew.dlprogram.DLInputSignature;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;
import org.semanticweb.drew.ldlpprogram.reasoner.KBCompilerManager;


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
