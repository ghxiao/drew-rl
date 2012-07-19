package at.ac.tuwien.kr.xsbwrapper;

import java.io.IOException;

public class XSBInvocationException extends RuntimeException {

	public XSBInvocationException(IOException e) {
		super(e);
	}

}
