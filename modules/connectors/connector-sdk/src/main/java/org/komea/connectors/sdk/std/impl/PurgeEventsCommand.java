package org.komea.connectors.sdk.std.impl;

import java.util.List;

import org.kohsuke.args4j.Option;
import org.komea.connectors.sdk.main.IConnectorCommand;
import org.komea.connectors.sdk.rest.impl.EventoryClientAPI;

import com.google.common.collect.Lists;

public class PurgeEventsCommand implements IConnectorCommand {

	@Option(name = "-url", usage = "URL of the event server")
	private String serverURL;

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
	public void run() throws Exception {
		try (EventoryClientAPI eventoryClientAPI = new EventoryClientAPI()) {

			eventoryClientAPI.setServerBaseURL(serverURL);
			for (final String eventType : eventTypes) {
				eventoryClientAPI.purgeEvents(eventType);
			}
		}

	}

	public void setServerURL(final String _serverURL) {
		serverURL = _serverURL;
	}
}
