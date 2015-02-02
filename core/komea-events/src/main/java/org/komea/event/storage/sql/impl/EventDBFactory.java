package org.komea.event.storage.sql.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.komea.event.model.SerializerType;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
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
		final Map<String, IEventDB> map = this.eventsDB.get();
		for (final IEventDB db : map.values()) {
			db.close();
		}
		this.eventsDB.remove();
		this.createdTables.clear();
		this.closeStorage();
	}

	@Override
	public synchronized void declareEventType(final String _type) {
		if (this.createdTables.contains(_type)) {
			LOGGER.debug("Table for {} already created", _type);
		} else {
			EventDB.createTable(this.connectionFactory, _type);
			this.createdTables.add(_type);
		}

	}

	public ConnectionFactory getConnectionFactory() {
		return this.connectionFactory;
	}

	public ConnectionFactory getDataSource() {
		return this.connectionFactory;
	}

	@Override
	public IEventDB getEventDB(final String _eventType) {
		final Map<String, IEventDB> map = this.eventsDB.get();
		IEventDB iEventDB = map.get(_eventType);
		if (iEventDB == null) {
			final IEventDB newEventDB = this.newEventDB(_eventType);
			map.put(_eventType, newEventDB);
			iEventDB = newEventDB;
		}
		return iEventDB;
	}

	public SerializerType getSerializer() {
		return this.serializer;
	}

	public void setConnectionFactory(final ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void setDataSource(final ConnectionFactory _dataSource) {
		this.connectionFactory = _dataSource;
	}

	public void setSerializer(final SerializerType serializer) {
		this.serializer = serializer;
	}

	protected void closeStorage() throws IOException {
		this.connectionFactory = null;
	}

	/**
	 * Creates a new event db
	 *
	 * @param _eventType
	 *            the event type
	 * @return the new event db.
	 */
	protected IEventDB newEventDB(final String _eventType) {
		return new EventDB(this.connectionFactory, _eventType, this.serializer);
	}

}
