package org.komea.connectors.sdk.std;

import org.komea.connectors.sdk.main.ConnectorCommand;

@ConnectorCommand(value = "schema", description = "Upgrades the schema with modifications")
public interface IUpgradeSchemaCommand {

	public void run();
}
