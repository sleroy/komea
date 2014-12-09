package org.komea.event.storage;

public interface IEventTypeSchemaUpdater {

	/**
	 * Update schema with event type
	 *
	 * @param _eventType
	 *            the event type
	 */
	public abstract void updateSchemaWithEvent(String _eventType);

}