/**
 *
 */
package org.komea.connectors.sdk.std.impl;

import java.net.ConnectException;
import java.net.URISyntaxException;

import org.kohsuke.args4j.Option;
import org.komea.connectors.sdk.main.IConnectorCommand;
import org.komea.connectors.sdk.rest.impl.ConsoleEventoryClientAPI;
import org.komea.connectors.sdk.rest.impl.EventoryClientAPI;
import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sleroy
 *
 */
public abstract class AbstractEventoryCommand implements IConnectorCommand {

	@Option(name = "-url", usage = "URL of the event server", required = true)
	private String serverURL;

	@Option(name = "-m", usage = "Mock the connection")
	private boolean mock;

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractPushEventsCommand.class);

	/**
	 *
	 */
	public AbstractEventoryCommand() {
		super();
	}

	public final String getServerURL() {
		return serverURL;
	}

	/**
	 * Initializes the client API.
	 *
	 * @return
	 */
	public IEventoryClientAPI initClientAPI() {
		IEventoryClientAPI client = null;

		if (mock) {
			client = new ConsoleEventoryClientAPI();
		} else {
			client = new EventoryClientAPI();
		}

		return client;
	}

	public final boolean isMock() {
		return mock;
	}

	@Override
	public void run() throws Exception {
		try (IEventoryClientAPI eventoryClientAPI = initClientAPI()) {

			eventoryClientAPI.setServerBaseURL(getServerURL());
			runCommand(eventoryClientAPI);

		}

	}

	public final void setMock(final boolean _mock) {
		mock = _mock;
	}

	public final void setServerURL(final String _serverURL) {
		serverURL = _serverURL;
	}

	/**
	 * @param _eventoryClientAPI
	 * @throws URISyntaxException
	 * @throws ConnectException
	 */
	protected abstract void runCommand(
			final IEventoryClientAPI _eventoryClientAPI) throws Exception;

}