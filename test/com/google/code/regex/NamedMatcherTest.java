package com.google.code.regex;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class NamedMatcherTest {

	private List<NamedPattern> patterns = newArrayList();

	private List<List<NamedMatcher>>
		goodMatchers = newArrayList(),
		badMatchers = newArrayList();

	@Before
	public void setUp() throws Exception {
		for (String pattern : Patterns.patterns) {
			patterns.add(NamedPattern.compile(pattern));
		}

		int i = 0;
		for (List<String> inputs : Patterns.goodInputs) {
			NamedPattern pattern = patterns.get(i++);
			List<NamedMatcher> matchers = newArrayList();
			goodMatchers.add(matchers);
			for (String input : inputs) {
				matchers.add(pattern.matcher(input));
			}
		}

		i = 0;
		for (List<String> inputs : Patterns.badInputs) {
			NamedPattern pattern = patterns.get(i++);
			List<NamedMatcher> matchers = newArrayList();
			badMatchers.add(matchers);
			for (String input : inputs) {
				matchers.add(pattern.matcher(input));
			}
		}

	}

	@Test
	public void testStandardPattern() {
		int i = 0;
		for (NamedPattern pattern : patterns) {
			assertEquals("Standard pattern does not match", Patterns.standardPatterns.get(i++), pattern.standardPattern());
		}
	}

	@Test
	public void testNamedPattern() {
		int i = 0;
		for (NamedPattern pattern : patterns) {
			assertEquals("Named pattern does not match", Patterns.patterns.get(i++), pattern.namedPattern());
		}
	}

	@Test
	public void testMatchesGoodInput() {
		for (List<NamedMatcher> matchers : goodMatchers) {
			for (NamedMatcher matcher : matchers) {
				assertTrue("Matcher does not match", matcher.matches());
			}
		}
	}

	@Test
	public void testMatchesBadInput() {
		for (List<NamedMatcher> matchers : badMatchers) {
			for (NamedMatcher matcher : matchers) {
				assertFalse("Matcher matches but shouldn't", matcher.matches());
			}
		}
	}

	@Test
	public void testOrderedGroups() {
		for (List<NamedMatcher> matchers : goodMatchers) {
			for (NamedMatcher matcher : matchers) {
				assertTrue("Matcher does not match", matcher.matches());
				List<String> orderedGroups = matcher.orderedGroups();
				assertEquals("Group count is not right", matcher.groupCount(), orderedGroups.size());
				int i = 0;
				for (String group : orderedGroups) {
					assertEquals("Group does not match", matcher.group(1+i++), group);
				}
			}
		}
	}

	@Test
	public void testNamedGroups() {
		int k = 0;
		for (List<NamedMatcher> matchers : goodMatchers) {
			List<String> groupNames = Patterns.groupNames.get(k++);
			for (NamedMatcher matcher : matchers) {
				assertTrue("Matcher does not match", matcher.matches());
				Map<String, String> namedGroups = matcher.namedGroups();
				assertEquals("Group count is not right", matcher.groupCount(), namedGroups.size());
				assertTrue("Unknown matching groups", groupNames.containsAll(namedGroups.keySet()));
				int i = 0;
				for (String group : namedGroups.values()) {
					assertEquals("Group does not match", matcher.group(1+i++), group);
				}
			}
		}
	}

	@Test
	public void testGroupString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testStartString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testEndString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testFind() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testToMatchResult() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUsePattern() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testReset() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testResetCharSequence() {
		fail("Not yet implemented"); // TODO
	}

}
