package org.komea.event.storage.mysql.impl;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.SerializerType;
import org.skife.jdbi.v2.tweak.ConnectionFactory;

public class EventDBFactory implements IEventDBFactory {

    private ConnectionFactory connectionFactory;
    private SerializerType serializer;

    private final ThreadLocal<Map<String, IEventDB>> eventsDB = new ThreadLocal<Map<String, IEventDB>>() {
        @Override
        protected java.util.Map<String, IEventDB> initialValue() {
            return Maps.newHashMap();
        }
    };

    public EventDBFactory() {
    }

    public EventDBFactory(final ConnectionFactory connectionFactory, final SerializerType serializer) {
        this.connectionFactory = connectionFactory;
        this.serializer = serializer;
    }

    public EventDBFactory(final DataSource dataSource, final SerializerType serializer) {
        this(new ConnectionFactory() {

            @Override
            public Connection openConnection() throws SQLException {
                return dataSource.getConnection();
            }
        }, serializer);
    }

    public EventDBFactory(final ConnectionFactory connectionFactory) {
        this(connectionFactory, SerializerType.DEFAULT_SERIALIZER);
    }

    public EventDBFactory(final DataSource dataSource) {
        this(dataSource, SerializerType.DEFAULT_SERIALIZER);
    }

    @Override
    public void close() throws IOException {
        final Map<String, IEventDB> map = eventsDB.get();
        for (final IEventDB db : map.values()) {
            db.close();
        }
        eventsDB.remove();
        closeStorage();
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

    @Override
    public void declareEventType(final String _type) {
        // TODO Auto-generated method stub
    }

    public ConnectionFactory getDataSource() {
        return connectionFactory;
    }

    public void setDataSource(final ConnectionFactory _dataSource) {
        connectionFactory = _dataSource;
    }

    protected void closeStorage() throws IOException {
        connectionFactory = null;
    }

    /**
     * Creates a new event db
     *
     * @param _eventType the event type
     * @return the new event db.
     */
    protected IEventDB newEventDB(final String _eventType) {
        return new EventDB(connectionFactory, _eventType, serializer);
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public SerializerType getSerializer() {
        return serializer;
    }

    public void setSerializer(SerializerType serializer) {
        this.serializer = serializer;
    }

}
