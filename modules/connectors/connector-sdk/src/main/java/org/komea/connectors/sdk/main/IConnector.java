package org.komea.connectors.sdk.main;

public interface IConnector {

	/**
	 * Runs the connector from a list of arguments.
	 *
	 * @param args
	 *            the arguments.
	 */
	public void run(final String[] args);
}
