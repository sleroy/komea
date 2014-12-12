package org.komea.connectors.sdk.std;

import org.komea.connectors.sdk.main.ConnectorCommand;

@ConnectorCommand(value = "model", description = "Upgrades the model with modifications")
public interface IUpdateModelCommand {

	public void run();
}
