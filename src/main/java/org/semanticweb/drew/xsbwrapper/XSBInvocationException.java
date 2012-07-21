package org.semanticweb.drew.xsbwrapper;

import java.io.IOException;

public class XSBInvocationException extends RuntimeException {

	public XSBInvocationException(IOException e) {
		super(e);
	}

}
