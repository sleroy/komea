/**
 *
 */
package org.komea.microservices.events.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sleroy
 *
 */
@ConfigurationProperties(prefix = "org.komea.microservices.events.storage")
public class EventStorageSettings {

	private StorageMode mode;

	private String tableName;

	private String eventTableSchema;

	/**
	 *
	 * Required Schema to autocreate tables when a new event type is provided
	 * 
	 * @return
	 */
	public String getEventTableSchema() {
		return eventTableSchema;
	}

	public StorageMode getMode() {
		return mode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setEventTableSchema(final String _eventTableSchema) {
		eventTableSchema = _eventTableSchema;
	}

	public void setMode(final StorageMode _mode) {
		mode = _mode;
	}

	public void setTableName(final String _tableName) {
		tableName = _tableName;
	}
}
