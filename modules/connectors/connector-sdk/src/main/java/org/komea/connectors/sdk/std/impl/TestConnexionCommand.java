package org.komea.connectors.sdk.std.impl;

import org.komea.events.api.IEventsClient;

public class TestConnexionCommand extends AbstractEventoryCommand {

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.main.IConnectorCommand#action()
     */
    @Override
    public String action() {
        return "test";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.main.IConnectorCommand#description()
     */
    @Override
    public String description() {
        return "Test the server connection.";
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
    protected void runCommand(IEventsClient eventsClient) throws Exception {
        eventsClient.testConnection();
    }

}
