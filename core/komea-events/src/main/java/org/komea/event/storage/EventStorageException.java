package org.komea.event.storage;

import org.komea.core.exceptions.KomeaRuntimeException;

public class EventStorageException extends KomeaRuntimeException {

	public EventStorageException(final String _message, final Exception _sqlException) {
		super(_message, _sqlException);

	}
}
