package at.ac.tuwien.kr.dlvwrapper;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class DLVWrapperRegexTest {
	String predicateRegex = "[a-z][a-zA-Z\\d_]*";

	String termRegex = "[a-z][a-zA-Z\\d_]*";

	//String literal = String.format("%s{\\(%s{,%s}*\\)}?", predicateRegex, termRegex, termRegex);
	String literal = String.format("%s(\\((%s(,%s)*)?\\))?", predicateRegex, termRegex, termRegex);

	@Test
	public void testPredicate() {
		Pattern pattern = Pattern.compile(predicateRegex);
		String line = "p";
		Matcher matcher = pattern.matcher(line);
		boolean valid = matcher.matches();
		assertTrue(valid);
		line = "p1";
		assertTrue(pattern.matcher(line).matches());
		assertTrue(pattern.matcher("p1_1").matches());
		assertTrue(pattern.matcher("pppppp_12_ABC").matches());
		assertFalse(pattern.matcher("1_1").matches());
		assertFalse(pattern.matcher("1").matches());
	}
	
	@Test
	public void testTerm() {
		Pattern pattern = Pattern.compile(termRegex);
		String line = "p";
		Matcher matcher = pattern.matcher(line);
		boolean valid = matcher.matches();
		assertTrue(valid);
		line = "p1";
		assertTrue(pattern.matcher(line).matches());
		assertTrue(pattern.matcher("p1_1").matches());
		assertTrue(pattern.matcher("pppppp_12_ABC").matches());
		assertFalse(pattern.matcher("1_1").matches());
		assertFalse(pattern.matcher("1").matches());
	}
	
	@Test
	public void testLiteral() {
		Pattern pattern = Pattern.compile(literal);
		String line = "p";
		Matcher matcher = pattern.matcher(line);
		boolean valid = matcher.matches();
		assertTrue(valid);
		line = "p1()";
		assertTrue(pattern.matcher(line).matches());
		
		
		
		assertTrue(pattern.matcher("p1_1(a,b)").matches());
		assertFalse(pattern.matcher("1_1(a)").matches());
		assertTrue(pattern.matcher("p1").matches());
	}
}
