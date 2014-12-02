package org.komea.orientdb.session.impl;

/**
 * This class defines a database connection where OrientDB will specifically
 * creates a memory storage.
 *
 * @author sleroy
 *
 */
public class MemoryDatabaseConfiguration extends DatabaseConfiguration {

	public MemoryDatabaseConfiguration(final String _databaseName) {
		this.setUsername("admin");
		this.setPassword("admin");
		this.setUrl("memory:" + _databaseName);
		this.setKeepOpen(false);
	}

}
