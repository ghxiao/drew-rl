package org.semanticweb.drew.dlprogram.parser;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;
import org.semanticweb.drew.dlprogram.DLAtomPredicate;
import org.semanticweb.drew.dlprogram.parser.DLProgramParser;
import org.semanticweb.drew.dlprogram.parser.ParseException;



public class DLAtomPredicateParserTest {

	@Test 
	public void testDLAtomPredicate001() throws ParseException{
		StringReader reader = new StringReader("DL[S1+=p1,S1-=p1;Q]");
		DLProgramParser parser = new DLProgramParser(reader);
		DLAtomPredicate result = parser.dlAtomPredicate();
		assertEquals("DL[S1 += p1,S1 -= p1;Q]", result.toString());
	}
	

}
