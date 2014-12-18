package org.komea.connectors.sdk.main.impl;

public class InvalidConnectorCommandException extends RuntimeException {
	public InvalidConnectorCommandException(final Class<?> _implementation) {
		super("Invalid connector command " + _implementation.getName()
				+ " provided, ensures you have provided an annotation @ConnectorCommand on it.");
	}

}
