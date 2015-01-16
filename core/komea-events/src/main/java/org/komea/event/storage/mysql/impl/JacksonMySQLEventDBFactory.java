package org.komea.event.storage.mysql.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.komea.event.storage.IEventDB;
import org.skife.jdbi.v2.tweak.ConnectionFactory;

public class JacksonMySQLEventDBFactory extends AbstractEventDBFactory {

	private ConnectionFactory dataSource;
	private final String tableName;

	public JacksonMySQLEventDBFactory(final ConnectionFactory _dataSource,
			final String _defaultTableName) {
		super();
		dataSource = _dataSource;
		tableName = _defaultTableName;

	}

	public JacksonMySQLEventDBFactory(final DataSource _dataSource,
			final String _defaultTableName) {
		super();
		dataSource = new ConnectionFactory() {

			@Override
			public Connection openConnection() throws SQLException {
				return _dataSource.getConnection();
			}
		};
		tableName = _defaultTableName;

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
	protected void closeStorage() throws IOException {
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
		return new JacksonMySQLEventDB(dataSource, tableName, _eventType);
	}

}
