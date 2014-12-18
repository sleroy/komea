package org.komea.connectors.sdk.std.impl;

import org.kohsuke.args4j.Option;
import org.komea.connectors.sdk.main.ConnectorCommand;
import org.komea.connectors.sdk.rest.impl.EventoryClientAPI;

@ConnectorCommand(value = "test", description = "Test the server connection.")
public class TestConnexionCommand {

	@Option(name = "-url", usage = "URL of the event server")
	private String	serverURL;

	public void run() throws Exception {
		try (EventoryClientAPI eventoryClientAPI = new EventoryClientAPI()) {

			eventoryClientAPI.setServerBaseURL(this.serverURL);
			eventoryClientAPI.testConnexion();
		}
	}

	public void setServerURL(final String _serverURL) {
		this.serverURL = _serverURL;
	}

}
