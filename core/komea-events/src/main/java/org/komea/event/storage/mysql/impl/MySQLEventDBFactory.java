package org.komea.event.storage.mysql.impl;

import java.io.IOException;

import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.mysql.kryo.impl.KryoMySQLEventDB;
import org.skife.jdbi.v2.tweak.ConnectionFactory;

public class MySQLEventDBFactory implements IEventDBFactory {
	
	private ConnectionFactory	dataSource;
	private final String	  tableName;
	
	public MySQLEventDBFactory(final ConnectionFactory _dataSource,
	        final String _defaultTableName) {
		super();
		dataSource = _dataSource;
		tableName = _defaultTableName;
		
	}
	
	@Override
	public void close() throws IOException {
		dataSource = null;
		
	}
	
	/*
	 * (non-Javadoc)
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
	
	@Override
	public IEventDB getEventDB(final String _eventType) {
		return new KryoMySQLEventDB(dataSource, tableName, _eventType);
	}
	
	public void setDataSource(final ConnectionFactory _dataSource) {
		dataSource = _dataSource;
	}
	
}
