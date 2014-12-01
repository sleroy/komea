package org.komea.core.model.query.impl;

public class KomeaQueryException extends RuntimeException {

	public KomeaQueryException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public KomeaQueryException(final String message, final Throwable cause) {
		super(message, cause);

	}

	public KomeaQueryException(final String message) {
		super(message);

	}

	public KomeaQueryException(final Throwable cause) {
		super(cause);

	}

}
