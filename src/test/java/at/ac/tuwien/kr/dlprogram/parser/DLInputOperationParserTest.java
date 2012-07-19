package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.DLInputOperation;


public class DLInputOperationParserTest {

	@Test 
	public void testDLInputOperation001() throws ParseException{
		StringReader reader = new StringReader("S1+=p1,S1-=p1");
		DLProgramParser parser = new DLProgramParser(reader);
		DLInputOperation result = parser.dlInputOperation();
		assertEquals("S1 += p1", result.toString());
	}
	
	@Test 
	public void testDLInputOperation002() throws ParseException{
		StringReader reader = new StringReader("S1-=p1");
		DLProgramParser parser = new DLProgramParser(reader);
		DLInputOperation result = parser.dlInputOperation();
		assertEquals("S1 -= p1", result.toString());
	}
}
