package org.komea.connectors.sdk.std.impl;

import org.kohsuke.args4j.Option;
import org.komea.connectors.sdk.main.ConnectorCommand;
import org.komea.connectors.sdk.rest.impl.EventoryClientAPI;

@ConnectorCommand(value = "purge", description = "clear the events in the database")
public abstract class AbstractPurgeEventsCommand {

	@Option(name = "-url", usage = "URL of the event server")
	private String	serverURL;

	public abstract String[] getEventTypes();

	public void run() throws Exception {
		final EventoryClientAPI eventoryClientAPI = new EventoryClientAPI();

		eventoryClientAPI.setServerBaseURL(this.serverURL);
		for (final String eventType : this.getEventTypes()) {
			eventoryClientAPI.purgeEvents(eventType);
		}

	}

	public String getServerURL() {
	    return serverURL;
    }

	public void setServerURL(String _serverURL) {
	    serverURL = _serverURL;
    }
}
