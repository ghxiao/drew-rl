package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.DLInputSignature;


public class DLInputSignatureParserTest {

	@Test 
	public void testDLInputOperation001() throws ParseException{
		StringReader reader = new StringReader("S1+=p1,S2-=p2");
		DLProgramParser parser = new DLProgramParser(reader);
		DLInputSignature result = parser.dlInputSignature();
		assertEquals("S1 += p1,S2 -= p2", result.toString());
	}
	

}
