package at.ac.tuwien.kr.dlvwrapper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.code.regex.NamedMatcher;
import com.google.code.regex.NamedPattern;

public class DLVWrapperRegexTest {
	String predicateRegex = "(?<predicate>[a-z][a-zA-Z\\d_]*)";

	String termRegex = "(?<term>[a-z][a-zA-Z\\d_]*)";

	String literalRegex = String.format("(?<literal>%s(\\((%s(,%s)*)?\\))?)",
			predicateRegex, termRegex, termRegex);

	String literalListRegex = String.format("(?<literalList>%s(,\\s*%s)*)",
			literalRegex, literalRegex);

//	String lineRegex = String.format("True:\\s*(\\{%s\\})\\s*",
//			literalListRegex);

	String lineRegex = String.format("(?<true>True:)?\\s*(\\{%s\\})\\s*",
			literalListRegex);
	
	@Test
	public void testPredicate() {
		NamedPattern pattern = NamedPattern.compile(predicateRegex);
		String line = "p";
		NamedMatcher matcher = pattern.matcher(line);
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
	public void testPredicateNamed() {
		NamedPattern pattern = NamedPattern.compile(predicateRegex);
		String line = "p";
		NamedMatcher matcher = pattern.matcher(line);
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
		NamedPattern pattern = NamedPattern.compile(termRegex);
		String line = "p";
		NamedMatcher matcher = pattern.matcher(line);
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
		NamedPattern pattern = NamedPattern.compile(literalRegex);
		String line = "p";
		NamedMatcher matcher = pattern.matcher(line);
		boolean valid = matcher.matches();
		assertTrue(valid);
		line = "p1()";
		assertTrue(pattern.matcher(line).matches());

		assertTrue(pattern.matcher("p1_1(a,b)").matches());
		assertFalse(pattern.matcher("1_1(a)").matches());
		assertTrue(pattern.matcher("p1").matches());
	}

	@Test
	public void testLiteralGroup() {
		NamedPattern pattern = NamedPattern.compile(literalRegex);
		System.out.println(literalRegex);
		// String line = "p,p1(),p(a,b),p_1";
		String line = "p1(a,b) p2()";
		NamedMatcher matcher = pattern.matcher(line);
		while (matcher.find()) {

			
			String lit = matcher.group("literal");
			System.out.println("literal " + lit);
			String predicate = matcher.group("predicate");
			System.out.println("predicate " + predicate);
			String term = matcher.group("term");
			System.out.println("term " + term);

			// Map<String, String> namedGroups = matchResult.namedGroups();
			// for (Entry<String, String> e : namedGroups.entrySet()) {
			// System.out.println(String.format("%s -> %s",
			// e.getKey(),e.getValue()));
			// }
		}
	}

	@Test
	public void testLiteralList() {
		NamedPattern pattern = NamedPattern.compile(literalListRegex);
		System.out.println(literalListRegex);
		// String line = "p,p1(),p(a,b),p_1";
		String line = "p1(a,b), p2()";
		line = "q, r, u(a,b), s";
		NamedMatcher matcher = pattern.matcher(line);
		while (matcher.find()) {

			String lits = matcher.group("literalList");
			System.out.println("literalList " + lits);
			String lit = matcher.group("literal");
			System.out.println("literal " + lit);
			String predicate = matcher.group("predicate");
			System.out.println("predicate " + predicate);
			String term = matcher.group("term");
			System.out.println("term " + term);

			// Map<String, String> namedGroups = matchResult.namedGroups();
			// for (Entry<String, String> e : namedGroups.entrySet()) {
			// System.out.println(String.format("%s -> %s",
			// e.getKey(),e.getValue()));
			// }
		}
	}

	@Test
	public void testLine() {
		String line = "True: {q, r, u(a,b), s}";
		NamedPattern linePattern = NamedPattern.compile(lineRegex);
		NamedMatcher lineMatcher = linePattern.matcher(line);
		if (lineMatcher.find()) {
			String lits = lineMatcher.group("literalList");
			System.out.println("literalList " + lits);
			NamedPattern literalPattern = NamedPattern.compile(literalRegex);
			NamedMatcher literalMatcher = literalPattern.matcher(lits);
			while (literalMatcher.find()) {
				String lit = literalMatcher.group("literal");
				System.out.println("literal " + lit);
			}
		}
	}
}
