package org.komea.core.exceptions;

public class KomeaException extends Exception {

	private static final String	DEFAULT_MESSAGE	= "Komea encountered an error : ";

	public KomeaException() {
		super(DEFAULT_MESSAGE);
	}

	public KomeaException(final String message) {
		super(DEFAULT_MESSAGE + message);
	}

	public KomeaException(final String message, final Throwable cause) {
		super(DEFAULT_MESSAGE + message, cause);
	}

	public KomeaException(final Throwable t) {
		super();
		this.initCause(t);
	}

}
