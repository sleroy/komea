package org.komea.event.storage.mysql.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.komea.event.storage.IEventDB;
import org.skife.jdbi.v2.tweak.ConnectionFactory;

public class JacksonMySQLAdvancedEventDBFactory extends AbstractEventDBFactory {

	private ConnectionFactory dataSource;
	private String sourceSchema;

	public JacksonMySQLAdvancedEventDBFactory(
			final ConnectionFactory _dataSource) {
		super();
		dataSource = _dataSource;

	}

	public JacksonMySQLAdvancedEventDBFactory(final DataSource _dataSource,
			final String _sourceSchema) {
		super();
		/**
		 * Contains the name of schema to autocreate tables
		 */
		sourceSchema = _sourceSchema;
		dataSource = new ConnectionFactory() {

			@Override
			public Connection openConnection() throws SQLException {
				return _dataSource.getConnection();
			}
		};

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.event.storage.IEventDBFactory#declareEventType(java.lang.String
	 * )
	 */
	@Override
	public void declareEventType(final String _type) {
		// TODO Auto-generated method stub

	}

	public ConnectionFactory getDataSource() {
		return dataSource;
	}

	public void setDataSource(final ConnectionFactory _dataSource) {
		dataSource = _dataSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.event.storage.mysql.impl.AbstractEventDBFactory#closeStorage()
	 */
	@Override
	protected void closeStorage() {
		dataSource = null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.event.storage.mysql.impl.AbstractEventDBFactory#newEventDB(
	 * java.lang.String)
	 */
	@Override
	protected IEventDB newEventDB(final String _eventType) {
		return new JacksonMySQLAdvancedEventDB(dataSource, _eventType,
				sourceSchema);
	}

}
