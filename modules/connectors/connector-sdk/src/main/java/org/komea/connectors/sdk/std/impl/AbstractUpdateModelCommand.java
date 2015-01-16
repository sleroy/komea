/**
 *
 */
package org.komea.connectors.sdk.std.impl;

import org.komea.connectors.sdk.main.IConnectorCommand;

/**
 * @author sleroy
 *
 */
public abstract class AbstractUpdateModelCommand implements IConnectorCommand {
	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#action()
	 */
	@Override
	public String action() {

		return "update_model";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#description()
	 */
	@Override
	public String description() {
		return "Update the organisational model on the server";
	}

}
