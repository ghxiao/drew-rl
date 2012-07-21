package org.semanticweb.drew.helper;

import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSDetector {

	static final Logger logger = LoggerFactory.getLogger(OSDetector.class);

	public static boolean isWindows() {

		final String osName = System.getProperty("os.name");

		logger.debug("os.name = " + osName);

		return osName.contains("Windows");
	}

	@Ignore("os x is a Unix")
	public static boolean isUnix() {
		final String osName = System.getProperty("os.name");

		logger.debug("os.name = " + osName);

		return osName.contains("Unix") || osName.contains("Linux");
	}

}
