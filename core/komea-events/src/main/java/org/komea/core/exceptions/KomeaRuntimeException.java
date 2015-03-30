package org.komea.core.exceptions;

public class KomeaRuntimeException extends RuntimeException {

	private static final String	DEFAULT_MESSAGE	= "Komea encountered an error : ";

	public KomeaRuntimeException() {
		super(DEFAULT_MESSAGE);
	}

	public KomeaRuntimeException(final String message) {
		super(DEFAULT_MESSAGE + message);
	}

	public KomeaRuntimeException(final String message, final Throwable cause) {
		super(DEFAULT_MESSAGE + message, cause);
	}

	public KomeaRuntimeException(final Throwable t) {
		super();
		this.initCause(t);
	}

}
