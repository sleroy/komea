package org.komea.event.storage.api;

import org.komea.core.exceptions.KomeaRuntimeException;

public class InvalidEventException extends KomeaRuntimeException {
	public InvalidEventException() {
		super("An invalid event has been provided and rejected.");
	}
}
