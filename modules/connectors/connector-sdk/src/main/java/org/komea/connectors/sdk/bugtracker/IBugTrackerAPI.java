/**
 *
 */
package org.komea.connectors.sdk.bugtracker;

/**
 * @author sleroy
 *
 */
public interface IBugTrackerAPI {
	/**
	 * Name of the event sent for newly created bugs
	 */
	public static final String EVENT_NEW_BUG = "new_bug";

	/**
	 * Name of the event for bug recently updated
	 */
	public static final String EVENT_UPDATED_BUG = "updated_bug";

}
