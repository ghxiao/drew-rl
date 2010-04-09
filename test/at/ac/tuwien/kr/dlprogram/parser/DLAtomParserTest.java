package at.ac.tuwien.kr.dlprogram.parser;


import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

import at.ac.tuwien.kr.dlprogram.DLAtomPredicate;
import at.ac.tuwien.kr.dlprogram.Literal;

public class DLAtomParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test	
	public void testDLAtomParser001() throws ParseException{
		StringReader reader = new StringReader("DL[S1+=p1,S1-=p1;Q](X)");
		DLProgramParser parser = new DLProgramParser(reader);
		Literal result = parser.literal();
		assertEquals("DL[S1 += p1,S1 -= p1;Q](X)", result.toString());
	}
}
