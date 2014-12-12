package org.komea.connectors.sdk.std.impl;

import org.komea.connectors.sdk.main.ConnectorCommand;

@ConnectorCommand(value = "stats", description = "Computes stats about the events into the database.")
public class EventStatsCommand {

	private final IEventTypeInformations	eventTypeInformations;

	public EventStatsCommand(final IEventTypeInformations _eventTypeInformations) {
		this.eventTypeInformations = _eventTypeInformations;
	}

	public void run() {

	}
}
