package org.komea.connectors.sdk.main.impl;

public class ConnectorCommandCliException extends RuntimeException {

	public ConnectorCommandCliException(final String _command, final Throwable _throwable) {
		super("IConnector " + _command + " could not be launch, the arguments are invalid.");
	}

}
