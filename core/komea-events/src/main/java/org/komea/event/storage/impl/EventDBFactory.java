package org.komea.event.storage.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.model.SerializerType;
import org.skife.jdbi.v2.tweak.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class EventDBFactory implements IEventDBFactory {

	private ConnectionFactory connectionFactory;
	private SerializerType serializer;
	private final Set<String> createdTables = Sets.newConcurrentHashSet();

	private final ThreadLocal<Map<String, IEventDB>> eventsDB = new ThreadLocal<Map<String, IEventDB>>() {
		@Override
		protected java.util.Map<String, IEventDB> initialValue() {
			return Maps.newHashMap();
		}
	};

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EventDBFactory.class);

	public EventDBFactory() {
		super();
	}

	public EventDBFactory(final ConnectionFactory connectionFactory) {
		this(connectionFactory, SerializerType.DEFAULT_SERIALIZER);
	}

	public EventDBFactory(final ConnectionFactory connectionFactory,
			final SerializerType serializer) {
		this.connectionFactory = connectionFactory;
		this.serializer = serializer;
	}

	public EventDBFactory(final DataSource dataSource) {
		this(dataSource, SerializerType.DEFAULT_SERIALIZER);
	}

	public EventDBFactory(final DataSource dataSource,
			final SerializerType serializer) {
		this(new ConnectionFactory() {

			@Override
			public Connection openConnection() throws SQLException {
				return dataSource.getConnection();
			}
		}, serializer);
	}

    @Override
    public void close() throws IOException {
        final Map<String, IEventDB> map = eventsDB.get();
        for (final IEventDB db : map.values()) {
            db.close();
        }
        eventsDB.remove();
        createdTables.clear();
        closeStorage();
    }

	@Override
	public synchronized void declareEventType(final String _type) {
		if (createdTables.contains(_type)) {
			LOGGER.debug("Table for {} already created", _type);
		} else {
			EventDB.createTable(connectionFactory, _type);
			createdTables.add(_type);
		}

	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public ConnectionFactory getDataSource() {
		return connectionFactory;
	}

	@Override
	public IEventDB getEventDB(final String _eventType) {
		final Map<String, IEventDB> map = eventsDB.get();
		IEventDB iEventDB = map.get(_eventType);
		if (iEventDB == null) {
			final IEventDB newEventDB = newEventDB(_eventType);
			map.put(_eventType, newEventDB);
			iEventDB = newEventDB;
		}
		return iEventDB;
	}

	public SerializerType getSerializer() {
		return serializer;
	}

	public void setConnectionFactory(final ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void setDataSource(final ConnectionFactory _dataSource) {
		connectionFactory = _dataSource;
	}

	public void setSerializer(final SerializerType serializer) {
		this.serializer = serializer;
	}

	protected void closeStorage() throws IOException {
		connectionFactory = null;
	}

	/**
	 * Creates a new event db
	 *
	 * @param _eventType
	 *            the event type
	 * @return the new event db.
	 */
	protected IEventDB newEventDB(final String _eventType) {
		return new EventDB(connectionFactory, _eventType, serializer);
	}

}
