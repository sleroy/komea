package org.komea.connectors.sdk.std;

import org.komea.connectors.sdk.main.ConnectorCommand;

@ConnectorCommand(value = "push", description = "push some events in the database")
public interface IPushEventsCommand {

	public void run();
}
