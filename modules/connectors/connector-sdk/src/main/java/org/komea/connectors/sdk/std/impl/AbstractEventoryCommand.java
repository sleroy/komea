/**
 *
 */
package org.komea.connectors.sdk.std.impl;

import org.komea.events.rest.ConsoleEventsClient;
import java.net.ConnectException;
import java.net.URISyntaxException;
import org.joda.time.DateTime;
import org.kohsuke.args4j.Option;
import org.komea.connectors.sdk.main.IConnectorCommand;
import org.komea.events.api.IEventsClient;
import org.komea.events.rest.EventsRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sleroy
 *
 */
public abstract class AbstractEventoryCommand implements IConnectorCommand {

    @Option(name = "-url", usage = "URL of the event server", required = true, metaVar = "EVENT_SERVER_URL")
    private String serverURL;

    @Option(name = "-m", usage = "Mock the connection")
    private boolean mock;

    @Option(name = "-s", usage = "Provides the last execution time (dd/MM/yyyy)", aliases = {"--since"}, handler = DateOptionHandler.class)
    private DateTime since;

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

    public DateTime getSince() {
        return since;
    }

    /**
     * Initializes the client API.
     *
     * @return
     */
    public IEventsClient initClient() {
        IEventsClient client = null;

        if (mock) {
            client = new ConsoleEventsClient();
        } else {
            client = new EventsRestClient(serverURL);
        }

        return client;
    }

    public final boolean isMock() {
        return mock;
    }

    @Override
    public void run() throws Exception {
        final IEventsClient eventsClient = initClient();
        runCommand(eventsClient);
    }

    public final void setMock(final boolean _mock) {
        mock = _mock;
    }

    public final void setServerURL(final String _serverURL) {
        serverURL = _serverURL;
    }

    public void setSince(final DateTime _since) {
        since = _since;
    }

    /**
     * @return
     */
    protected boolean hasLastLaunchDate() {
        return since != null;
    }

    /**
     * @param _eventoryClientAPI
     * @throws URISyntaxException
     * @throws ConnectException
     */
    protected abstract void runCommand(
            final IEventsClient eventsClient) throws Exception;

}
