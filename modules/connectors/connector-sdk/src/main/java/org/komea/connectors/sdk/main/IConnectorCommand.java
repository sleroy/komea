package org.komea.connectors.sdk.main;

public interface IConnectorCommand {
	/**
	 * Indicates the name of the command to launch.
	 *
	 * @return the name of the command
	 */
	String action();

	/**
	 * Provides an extra definition of the command.
	 *
	 * @return the description.
	 */
	String description();

	/**
	 * Executes the command.
	 * @throws Exception 
	 */
	void run() throws Exception;
}
