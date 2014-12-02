package org.komea.connectors.bugzilla.events;

public interface IBugzillaConnectorInformations {
	/**
	 * Name of the event sent for newly created bugs
	 */
	public static final String EVENT_NEW_BUG = "new_bug";

	/**
	 * Name of the event for bug recently updated
	 */
	public static final String EVENT_UPDATED_BUG = "updated_bug";
	/**
	 * Provider name
	 */
	public static final String PROVIDER_BUG = "bugzilla";
}
