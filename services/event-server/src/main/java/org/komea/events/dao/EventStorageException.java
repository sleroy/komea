package org.komea.events.dao;

import java.sql.SQLException;

import org.komea.events.exceptions.KomeaRuntimeException;

public class EventStorageException extends KomeaRuntimeException {

	public EventStorageException(final String _message, final Exception _sqlException) {
		super(_message, _sqlException);

	}
}
