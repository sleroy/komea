package org.komea.connectors.sdk.std.impl;

import java.net.ConnectException;
import java.net.URISyntaxException;
import java.rmi.ServerException;
import java.util.List;

import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;

import com.google.common.collect.Lists;

public final class EventStatsCommand extends AbstractEventoryCommand {

	private final List<String> eventTypes;

	/**
	 * Builds the command for a fixed list of event types;
	 *
	 * @param _eventTypes
	 */
	public EventStatsCommand(final List<String> _eventTypes) {
		super();
		eventTypes = _eventTypes;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#init()
	 */
	@Override
	public void init() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.connectors.sdk.std.impl.AbstractEventoryCommand#runCommand(
	 * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI)
	 */
	@Override
	protected void runCommand(final IEventoryClientAPI _eventoryClientAPI)
			throws ConnectException, URISyntaxException, ServerException {
		_eventoryClientAPI.setServerBaseURL(getServerURL());
		for (final String eventType : eventTypes) {
			LOGGER.info("Number of events of type ## {} ## => {}", eventType,
					_eventoryClientAPI.countEvents(eventType));
		}

	}
}
