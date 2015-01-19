package org.komea.connectors.sdk.std.impl;

import java.util.List;

import org.komea.connectors.sdk.rest.impl.EventoryClientAPI;
import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;

import com.google.common.collect.Lists;

public class PurgeEventsCommand extends AbstractEventoryCommand {

	private final List<String> eventTypes;

	public PurgeEventsCommand(final List<String> _eventTypes) {
		super();
		eventTypes = _eventTypes;
	}

	public PurgeEventsCommand(final String... _eventTypes) {
		super();
		eventTypes = Lists.newArrayList(_eventTypes);
	}

	@Override
	public String action() {

		return "purge";
	}

	@Override
	public String description() {

		return "Purge events on the server";
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
	public void runCommand(final IEventoryClientAPI _eventoryClientAPI)
			throws Exception {
		try (EventoryClientAPI eventoryClientAPI = new EventoryClientAPI()) {

			eventoryClientAPI.setServerBaseURL(getServerURL());
			for (final String eventType : eventTypes) {
				eventoryClientAPI.getEventStorage()
						.clearEventsOfType(eventType);
			}
		}

	}

}
