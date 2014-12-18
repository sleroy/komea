package org.komea.connectors.sdk.std.impl;

import java.util.List;

import org.kohsuke.args4j.Option;
import org.komea.connectors.sdk.main.IConnectorCommand;
import org.komea.connectors.sdk.rest.impl.EventoryClientAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public final class EventStatsCommand implements IConnectorCommand {

	@Option(name = "-url", usage = "URL of the event server")
	private String	            serverURL;

	private static final Logger	LOGGER	= LoggerFactory.getLogger(EventStatsCommand.class);

	private final List<String>	eventTypes;

	/**
	 * Builds the command for a fixed list of event types;
	 *
	 * @param _eventTypes
	 */
	public EventStatsCommand(final List<String> _eventTypes) {
		super();
		this.eventTypes = _eventTypes;
	}

	public EventStatsCommand(final String... _eventTypes) {
		this(Lists.newArrayList(_eventTypes));
	}

	@Override
	public String action() {
		return "stats";
	}

	@Override
	public String description() {
		return "Computes stats about the events into the database.";
	}

	@Override
	public void run() throws Exception {
		try (EventoryClientAPI eventoryClientAPI = new EventoryClientAPI()) {

			eventoryClientAPI.setServerBaseURL(this.serverURL);
			for (final String eventType : this.eventTypes) {
				LOGGER.info("Number of events of type ## {} ## => {}", eventType,
				        eventoryClientAPI.countEvents(eventType));
			}
		}

	}

	public void setServerURL(final String _serverURL) {
		this.serverURL = _serverURL;
	}
}
