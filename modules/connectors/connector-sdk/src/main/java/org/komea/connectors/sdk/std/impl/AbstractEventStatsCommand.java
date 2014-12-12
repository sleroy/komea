package org.komea.connectors.sdk.std.impl;

import org.kohsuke.args4j.Option;
import org.komea.connectors.sdk.main.ConnectorCommand;
import org.komea.connectors.sdk.rest.impl.EventoryClientAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ConnectorCommand(value = "stats", description = "Computes stats about the events into the database.")
public abstract class AbstractEventStatsCommand {

	@Option(name = "-url", usage = "URL of the event server")
	private String	            serverURL;

	private static final Logger	LOGGER	= LoggerFactory.getLogger(AbstractEventStatsCommand.class);

	public abstract String[] getEventTypes();

	public void run() throws Exception {
		try (EventoryClientAPI eventoryClientAPI = new EventoryClientAPI()) {

			eventoryClientAPI.setServerBaseURL(this.serverURL);
			for (final String eventType : this.getEventTypes()) {
				LOGGER.info("Number of events of type ## {} ## => {}", eventType,
						eventoryClientAPI.countEvents(eventType));
			}
		}

	}

	public void setServerURL(final String _serverURL) {
		this.serverURL = _serverURL;
	}
}
