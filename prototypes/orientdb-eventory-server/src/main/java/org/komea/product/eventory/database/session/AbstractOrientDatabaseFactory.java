package org.komea.product.eventory.database.session;

import javax.annotation.PostConstruct;

import org.springframework.util.Assert;

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

	/** Default minimum pool size. */
	public static final int DEFAULT_MIN_POOL_SIZE = 1;

	/** Default maximum pool size. */
	public static final int DEFAULT_MAX_POOL_SIZE = 20;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The min pool size. */
	private int minPoolSize = DEFAULT_MIN_POOL_SIZE;

	/** The max pool size. */
	private int maxPoolSize = DEFAULT_MAX_POOL_SIZE;

	private String url;

	private P pool;

	private T db;

	public final T getDatabaseSession() {
		return (T) ODatabaseRecordThreadLocal.INSTANCE.get().getDatabaseOwner();
	}

	/**
	 * Gets the max pool size.
	 *
	 * @return the max pool size
	 */
	public int getMaxPoolSize() {
		return this.maxPoolSize;
	}

	/**
	 * Gets the min pool size.
	 *
	 * @return the min pool size
	 */
	public int getMinPoolSize() {
		return this.minPoolSize;
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

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Gets the database url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	@PostConstruct
	public void init() {
		Assert.notNull(this.url);
		Assert.notNull(this.username);
		Assert.notNull(this.password);

		final ODatabase createdDB = this.newDatabase();
		this.createDatabase(createdDB);
		this.createPool();
	}

	/**
	 * Sets the max pool size.
	 *
	 * @param maxPoolSize
	 *            the new max pool size
	 */
	public void setMaxPoolSize(final int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	/**
	 * Sets the min pool size.
	 *
	 * @param minPoolSize
	 *            the new min pool size
	 */
	public void setMinPoolSize(final int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Sets the database url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the new username
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	protected void createDatabase(final ODatabase _database) {
		if (!this.getUrl().startsWith("remote:")) {
			if (!_database.exists()) {
				_database.create();
				_database.close();
			}
		}
	}

	protected void createPool() {
		this.pool = this.doCreatePool();
		this.pool.setup(this.minPoolSize, this.maxPoolSize);
	}

	/**
	 * Do create pool.
	 *
	 * @return the o database pool base
	 */
	protected abstract P doCreatePool();

	protected abstract T newDatabase();

	public P getPool() {
		return pool;
	}

	public void setPool(P pool) {
		this.pool = pool;
	}

}