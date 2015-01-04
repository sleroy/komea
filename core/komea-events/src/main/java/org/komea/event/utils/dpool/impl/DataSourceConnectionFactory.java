package org.komea.event.utils.dpool.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.skife.jdbi.v2.tweak.ConnectionFactory;

/**
 * Converts a datasource into a connection factory.
 *
 * @author sleroy
 */
public class DataSourceConnectionFactory implements ConnectionFactory
{
	private final DataSource	dataSource;
	
	public DataSourceConnectionFactory(final DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public Connection openConnection() throws SQLException
	{
		return dataSource.getConnection();
	}
	
}
