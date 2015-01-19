package org.komea.connectors.sdk.std.impl;

import org.kohsuke.args4j.Option;
import org.komea.connectors.sdk.main.IConnectorCommand;
import org.komea.connectors.sdk.rest.impl.EventoryClientAPI;

public class TestConnexionCommand implements IConnectorCommand {

	@Option(name = "-url", usage = "URL of the event server")
	private String serverURL;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#action()
	 */
	@Override
	public String action() {
		return "test";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#description()
	 */
	@Override
	public String description() {
		return "Test the server connection.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#init()
	 */
	@Override
	public void init() {
		//

	}

	@Override
	public void run() throws Exception {
		try (EventoryClientAPI eventoryClientAPI = new EventoryClientAPI()) {

			eventoryClientAPI.setServerBaseURL(serverURL);
			eventoryClientAPI.testConnexion();
		}
	}

	public void setServerURL(final String _serverURL) {
		serverURL = _serverURL;
	}

}
