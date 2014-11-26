package org.komea.orientdb.session.impl;

import org.apache.commons.lang.Validate;

import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.ODatabasePoolBase;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;

/**
 * A base factory for creating {@link ODatabase} objects.
 *
 * @author Dzmitry_Naskou
 * @param <T>
 *            the type of database to handle
 */
public abstract class AbstractOrientDatabaseFactory<T extends ODatabase, P extends ODatabasePoolBase<T>> {

	private P pool;

	private T db;

	public final T getDatabaseSession() {
		return (T) ODatabaseRecordThreadLocal.INSTANCE.get().getDatabaseOwner();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.orm.orient.AbstractOrientDatabaseFactory#openDatabase
	 * ()
	 */
	public final T getOrCreateDatabaseSession() {
		this.db = this.pool.acquire();
		return this.db;
	}

	public P getPool() {
		return this.pool;
	}

	public void init(final DatabaseConfiguration _configuration) {

		Validate.notNull(_configuration, "A database configuration is required");
		final ODatabase createdDB = this.newDatabase(_configuration);
		this.createDatabase(createdDB, _configuration);
		this.createPool(_configuration);
	}

	public void setPool(final P pool) {
		this.pool = pool;
	}

	protected void createDatabase(final ODatabase _database,
			final DatabaseConfiguration _configuration) {
		if (!_configuration.getUrl().startsWith("remote:")) {
			if (!_database.exists()) {
				_database.create();
				_database.close();
			}
		}
	}

	protected void createPool(final DatabaseConfiguration _configuration) {
		this.pool = this.doCreatePool(_configuration);
		this.pool.setup(_configuration.getMinPoolSize(),
				_configuration.getMaxPoolSize());
	}

	/**
	 * Do create pool.
	 *
	 * @return the o database pool base
	 */
	protected abstract P doCreatePool(DatabaseConfiguration _configuration);

	protected abstract T newDatabase(DatabaseConfiguration _configuration);

}