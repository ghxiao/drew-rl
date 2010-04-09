package at.ac.tuwien.kr.dlprogram.parser;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.DLAtomPredicate;
import at.ac.tuwien.kr.dlprogram.DLInputOperation;


public class DLAtomPredicateParserTest {

	@Test 
	public void testDLAtomPredicate001() throws ParseException{
		StringReader reader = new StringReader("DL[S1+=p1,S1-=p1;Q]");
		DLProgramParser parser = new DLProgramParser(reader);
		DLAtomPredicate result = parser.dlAtomPredicate();
		assertEquals("DL[S1 += p1,S1 -= p1;Q]", result.toString());
	}
	

}
