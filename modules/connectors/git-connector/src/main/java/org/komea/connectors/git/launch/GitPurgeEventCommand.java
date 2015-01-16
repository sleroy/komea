/**
 *
 */
package org.komea.connectors.git.launch;

import org.komea.connectors.git.IGitEvent;
import org.komea.connectors.sdk.std.impl.PurgeEventsCommand;

/**
 * @author sleroy
 *
 */
public final class GitPurgeEventCommand extends PurgeEventsCommand {

	/**
	 * @param _eventTypes
	 */
	private GitPurgeEventCommand() {
		super(IGitEvent.COMMIT, IGitEvent.UPDATE, IGitEvent.TAG);
	}
}