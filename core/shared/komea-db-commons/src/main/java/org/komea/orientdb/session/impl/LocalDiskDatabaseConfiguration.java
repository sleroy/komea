package org.komea.orientdb.session.impl;

import java.io.File;

/**
 * This class defines a database connection where OrientDB will specifically
 * creates a local disk storage.
 *
 * @author sleroy
 *
 */
public class LocalDiskDatabaseConfiguration extends DatabaseConfiguration {

	public LocalDiskDatabaseConfiguration(final String _databaseStoragePath, final String _databaseName) {
		this.setUsername("admin");
		this.setPassword("admin");
		this.setUrl("plocal:" + _databaseStoragePath + File.separatorChar + _databaseName);
		this.setKeepOpen(false);
	}

}
