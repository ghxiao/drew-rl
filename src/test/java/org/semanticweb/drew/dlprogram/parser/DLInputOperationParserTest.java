package org.semanticweb.drew.dlprogram.parser;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;
import org.semanticweb.drew.dlprogram.DLInputOperation;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;



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
