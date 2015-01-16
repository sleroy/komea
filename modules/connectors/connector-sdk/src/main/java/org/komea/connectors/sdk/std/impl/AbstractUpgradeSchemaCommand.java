/**
 *
 */
package org.komea.connectors.sdk.std.impl;

import org.komea.connectors.sdk.main.IConnectorCommand;

/**
 * @author sleroy
 *
 */
public abstract class AbstractUpgradeSchemaCommand implements IConnectorCommand {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#action()
	 */
	@Override
	public String action() {

		return "upgrade_schema";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#description()
	 */
	@Override
	public String description() {
		return "Upgrade the organisational schema on the server";
	}

}
