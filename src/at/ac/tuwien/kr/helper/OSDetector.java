package at.ac.tuwien.kr.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSDetector {

	static final Logger logger = LoggerFactory.getLogger(OSDetector.class);

	public static boolean isWindows() {

		final String osName = System.getProperty("os.name");

		logger.debug("os.name = " + osName);

		return osName.contains("Windows");
	}

	public static boolean isUnix() {
		final String osName = System.getProperty("os.name");

		logger.debug("os.name = " + osName);

		return osName.contains("Unix");
	}

}
