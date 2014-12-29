package org.komea.event.storage.mysql.impl;

import java.io.Closeable;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.komea.event.model.beans.FlatEvent;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.ResultIterator;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ConnectionFactory;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.LongMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Richard on 4/9/14.
 *
 * @modified to remove the key support for primary key.
 */
public abstract class MySQLStorageSupport implements Closeable {

	private static final EmptyResultSetIterator EMPTY_RESULT_SET_ITERATOR = new EmptyResultSetIterator();

	private static final class EmptyResultSetIterator implements ResultIterator<FlatEvent> {
	    @Override
	    public boolean hasNext() {

	        return false;
	    }

	    @Override
	    public FlatEvent next() {
	        throw new UnsupportedOperationException();
	    }

	    @Override
	    public void remove() {
	        
	    }

	    @Override
	    public void close() {
	        
	    }
    }

	protected static final Logger	   LOGGER	= LoggerFactory.getLogger(MySQLStorageSupport.class);
	private DBI	           db;

	protected final String	sqlColumnType;
	protected String	   databaseTable;
	protected String	   insertStatementSQL;
	protected String	   createStatementSQL;
	protected String	   deleteSQL;
	protected String	   countSQL;
	private String	       loadAllSQL;
	private String eventType;

	public MySQLStorageSupport(final ConnectionFactory _dataSource, String _table, String _eventType) {
		Validate.notEmpty(_eventType);
		Validate.notNull(_dataSource, "database connection required");
		eventType = _eventType;
		this.db = new DBI(_dataSource);
		
		this.sqlColumnType = "BLOB";
		createSQL(_table);

	}

	@Override
	public void close() throws IOException {
		//
		db = null;
	}

	/**
	 * Count the number of elements
	 */
	public long count() {
		Long res = 0L;
		Handle open =  null;
		try {
			open = db.open();
			Query<Long> map = open.createQuery(countSQL).map(LongMapper.FIRST);
			Object obj = map.first();
			return  ((Long) obj).longValue();
			
		} catch (Throwable e) {
			handle("Error with count query", (Exception) e);
		} finally {
			IOUtils.closeQuietly(open);
		}
		return res.longValue();
	}

	private void handle(String _message, Exception _e) {
		LOGGER.error(_message, _e);
	    
    }

	public void put(final FlatEvent _entry) {

		this.LOGGER.debug("PUT VALUE {}", _entry);

		Handle open =  null;
		try {
			open = db.open();

			// date, provider, eventType, data
			open.createStatement(insertStatementSQL).bind("date", _entry.getDate()).bind("provider", _entry.getProvider())
			        .bind("eventType", _entry.getEventType()).bind("data", serialize(_entry)).execute();
		} catch (Exception e) {
			handle("Error with put query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}
		

	}

	public abstract byte[] serialize(FlatEvent _entry);

	public abstract FlatEvent unserialize(byte[] _entry);

	public void putAll(final Collection<FlatEvent> values) {

		if (this.LOGGER.isDebugEnabled()) {
			this.LOGGER.info("PUT ALL {}", values);
		}

		Handle open =  null;
		try {
			open = db.open();


			final Collection<FlatEvent> entries = values;
			open.begin();// WRAPS A TRANSACTION IF NOT ALREADY CREATED
			for (final FlatEvent entry : entries) {
				this.put(entry);

			}
			open.commit();
		} catch (Exception e) {
			handle("Error with putAll query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}
	}

	public void createSQL(final String _table) {
		this.insertStatementSQL = "INSERT INTO `" + _table
		        + "` (date, provider, eventType, data) VALUES (:date, :provider, :eventType, :data);";
		this.loadAllSQL = "SELECT data from `" + _table + "` WHERE eventType='" +eventType + "';";
		this.countSQL = "SELECT COUNT(*) from `" + _table + "` WHERE eventType= '"+ eventType + "'";

		this.deleteSQL = "DELETE FROM `" + _table + "` WHERE eventType='" + eventType + "';";

		if (this.LOGGER.isDebugEnabled()) {
			this.LOGGER.info("The following SQL statements will be used {}", new String[] { "insert",
			        this.insertStatementSQL, "LOAD", this.loadAllSQL, "DELETE", this.deleteSQL, "CREATE_TABLE",
			        this.createStatementSQL, "COUNT", this.countSQL });
		}

	}

	/**
	 * Removes all the elements of a given type.
	 * @param _eventType 
	 * 
	 * @throws SQLException
	 */
	public void removeAll() {
		Handle open =  null;
		try {
			open = db.open();

			open.execute(deleteSQL);
		} catch (Exception e) {
			handle("Error with removeAll query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}

	}

	public ResultIterator<FlatEvent> loadAll() {

		this.LOGGER.debug("LOAD ALL  ");
		Handle open =  null;
		try {
			open = db.open();

			return open.createQuery(loadAllSQL).map(new ResultSetMapper<FlatEvent>() {

				@Override
				public FlatEvent map(int _index, ResultSet _r, StatementContext _ctx) throws SQLException {

					return unserialize(_r.getBytes(1));
				}
			}).iterator();

		} catch (Exception e) {
			handle("Error with loadAll query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}
		return EMPTY_RESULT_SET_ITERATOR;
	}

}
