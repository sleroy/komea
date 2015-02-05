package org.komea.event.queries.factory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;
import org.junit.Test;
import org.komea.event.storage.sql.impl.EventDBFactory;
import org.komea.event.utils.dpool.impl.DataSourceConnectionFactory;
import org.skife.jdbi.v2.tweak.ConnectionFactory;

public class EventDBFactoryTest {

    @Test
    public void testEventDBFactory() {
        final DataSource dataSource = new MysqlDataSource();
        final ConnectionFactory connectionFactory = new DataSourceConnectionFactory(dataSource);
        new EventDBFactory();
        new EventDBFactory(dataSource);
        final EventDBFactory factory = new EventDBFactory(connectionFactory);
        factory.setConnectionFactory(factory.getConnectionFactory());
        factory.setDataSource(factory.getDataSource());
        factory.setSerializer(factory.getSerializer());
    }
}
