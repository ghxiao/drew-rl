package org.semanticweb.drew.helper;


import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.drew.helper.OSDetector;

public class OSDetectorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsWindows() {
		final char separatorChar = File.separatorChar;
		
		System.out.println("separatorChar = " + separatorChar);
		
		if(separatorChar == '\\') {
			assertTrue(OSDetector.isWindows());
		}else {
			assertFalse(OSDetector.isWindows());
		}
	}
	
	@Test
	@Ignore
	public void testIsUnix() {
		final char separatorChar = File.separatorChar;
		
		System.out.println("separatorChar = " + separatorChar);
		
		if(separatorChar == '/') {
			assertTrue(OSDetector.isUnix());
		}else {
			assertFalse(OSDetector.isUnix());
		}
	}
}
