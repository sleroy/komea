package org.komea.connectors.sdk.std.impl;

import org.komea.connectors.sdk.main.ConnectorCommand;

@ConnectorCommand(value = "push", description = "push some events in the database")
public abstract class AbstractPushEventsCommand {

	public abstract void run();
}
