package org.komea.connectors.sdk.std.impl;

import org.komea.connectors.sdk.main.IConnectorCommand;
import org.komea.connectors.sdk.std.IEventTypeInformations;

public abstract class AbstractPushEventsCommand implements IConnectorCommand {

	private final IEventTypeInformations eventTypeInformations;

	public AbstractPushEventsCommand(
			final IEventTypeInformations _eventTypeInformations) {
		eventTypeInformations = _eventTypeInformations;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#action()
	 */
	@Override
	public String action() {

		return "push";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#description()
	 */
	@Override
	public String description() {
		return "Push events to the server";
	}

}
