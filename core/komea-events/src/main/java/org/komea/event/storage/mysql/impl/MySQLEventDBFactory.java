package org.komea.event.storage.mysql.impl;

import java.io.IOException;
import java.sql.SQLException;

import org.komea.event.storage.EventStorageException;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.mysql.kryo.impl.KryoMySQLEventDB;
import org.skife.jdbi.v2.tweak.ConnectionFactory;

public class MySQLEventDBFactory implements IEventDBFactory {

	private ConnectionFactory	dataSource;
	private String	          tableName;

	public MySQLEventDBFactory(final ConnectionFactory _dataSource, String _defaultTableName) {
		super();
		this.dataSource = _dataSource;
		tableName = _defaultTableName;

	}

	@Override
	public void close() throws IOException {
		this.dataSource = null;

	}

	

	@Override
	public IEventDB getEventDB(final String _eventType) {
		return new KryoMySQLEventDB(this.dataSource, tableName, _eventType);
	}

	public ConnectionFactory getDataSource() {
	    return dataSource;
    }

	public void setDataSource(ConnectionFactory _dataSource) {
	    dataSource = _dataSource;
    }

}
