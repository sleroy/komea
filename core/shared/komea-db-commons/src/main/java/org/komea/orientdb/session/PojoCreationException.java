package org.komea.orientdb.session;

public class PojoCreationException extends RuntimeException {

	public PojoCreationException(final Exception _e) {
		super("Could not instantiate a pojo", _e);
	}

}
