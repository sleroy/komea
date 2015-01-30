package org.komea.core.model.query.impl;

import org.komea.events.exceptions.KomeaRuntimeException;

public class KomeaQueryException extends KomeaRuntimeException {

	public KomeaQueryException(final String message, final Throwable cause) {
		super(message, cause);

	}

}
