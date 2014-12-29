package org.komea.event.storage.mysql.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.skife.jdbi.v2.tweak.ConnectionFactory;

/**
 * Converts a datasource into a connection factory.
 * @author sleroy
 *
 */
public class DataSourceConnectionFactory implements ConnectionFactory
{
    private DataSource dataSource;

    public DataSourceConnectionFactory(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public Connection openConnection() throws SQLException
    {
        return dataSource.getConnection();
    }
}
