package misc;

import org.apache.log4j.Logger;

public class Log4jExample {

	static Logger logger = Logger.getLogger(Log4jExample.class);

	public static void main(String[] args) {
		logger.debug("Did it again!");
	}

}
