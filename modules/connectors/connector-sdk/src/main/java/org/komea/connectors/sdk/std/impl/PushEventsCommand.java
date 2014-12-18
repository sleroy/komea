package org.komea.connectors.sdk.std.impl;

import org.komea.connectors.sdk.main.ConnectorCommand;

@ConnectorCommand(value = "push", description = "clear the events in the database")
public abstract class PushEventsCommand {

	private final IEventTypeInformations	eventTypeInformations;

	public PushEventsCommand(final IEventTypeInformations _eventTypeInformations) {
		this.eventTypeInformations = _eventTypeInformations;

	}

	public abstract void run();
}
