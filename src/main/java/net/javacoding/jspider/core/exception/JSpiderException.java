package net.javacoding.jspider.core.exception;

/**
 * $Id: JSpiderException.java,v 1.1 2002/11/26 20:16:32 vanrogu Exp $
 */
public class JSpiderException extends RuntimeException{

	private static final long serialVersionUID = -6432029115131713425L;

	public JSpiderException() {
		super();
	}

	public JSpiderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JSpiderException(String message, Throwable cause) {
		super(message, cause);
	}

	public JSpiderException(String message) {
		super(message);
	}

	public JSpiderException(Throwable cause) {
		super(cause);
	}
	
}
