package org.semanticweb.drew.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;
import org.semanticweb.drew.dlprogram.DLInputSignature;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;



public class DLInputSignatureParserTest {

	@Test 
	public void testDLInputOperation001() throws ParseException{
		StringReader reader = new StringReader("S1+=p1,S2-=p2");
		DLProgramParser parser = new DLProgramParser(reader);
		DLInputSignature result = parser.dlInputSignature();
		assertEquals("S1 += p1,S2 -= p2", result.toString());
	}
	

}
