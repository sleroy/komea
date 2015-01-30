package org.komea.events.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.KomeaEvent;
import org.komea.events.dto.SerializerType;
import org.komea.events.serializer.EventsSerializer;

public class EventsDaoTest extends DBTestCase {

    private EventsDao dao;

    public EventsDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:events;MODE=MYSQL");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        final InputStream inputStream = this.getClass().getResourceAsStream("/dataset.xml");
        return new FlatXmlDataSetBuilder().build(inputStream);
    }

    @Before
    @Override
    public void setUp() {
        dao = new EventsDao();
        final EventsSerializer serializer = new EventsSerializer();
        serializer.setSerializerType(SerializerType.JACKSON);
        dao.setSerializer(serializer);
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:events;MODE=MYSQL");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        dao.setDataSource(dataSource);
    }

    @After
    @Override
    public void tearDown() throws Exception {
        dao.close();
    }

    @Test
    public void test() {
        final DateTime now = DateTime.now();
        final DateTime yesterday = now.minusDays(1);
        final DateTime tomorrow = now.plusDays(1);

        final String eventType = "build";

        dao.declareEventType(eventType);
        assertTrue(dao.getEventTypes().contains(eventType));

        final KomeaEvent event = new KomeaEvent("jenkins", eventType, new Date());
        dao.putEvent(event);
        Iterator<KomeaEvent> iterator = dao.loadEventsOfType(eventType);
        List<KomeaEvent> events = Lists.newArrayList(iterator);
        assertEquals(1, events.size());
        assertEquals(event.getProvider(), events.get(0).getProvider());
        assertEquals(event.getDate(), events.get(0).getDate());
        assertEquals(event.getEventType(), events.get(0).getEventType());

        assertEquals(1, dao.countEventsOfType(eventType));

        dao.clearEventsOfType(eventType);
        assertEquals(0, dao.countEventsOfType(eventType));

        dao.putEvent(new KomeaEvent("jenkins", eventType, yesterday.toDate()));
        dao.putEvent(new KomeaEvent("jenkins", eventType, now.toDate()));
        dao.putEvent(new KomeaEvent("jenkins", eventType, tomorrow.toDate()));
        DateInterval period = new DateInterval(yesterday.plusSeconds(1).toDate(),
                tomorrow.minusSeconds(1).toDate());

        iterator = dao.loadEventsOfTypeOnPeriod(eventType, period);
        events = Lists.newArrayList(iterator);
        assertEquals(1, events.size());
        assertEquals(now.toDate(), events.get(0).getDate());

        period = new DateInterval(yesterday.plusSeconds(1).toDate(), null);
        iterator = dao.loadEventsOfTypeOnPeriod(eventType, period);
        events = Lists.newArrayList(iterator);
        assertEquals(2, events.size());
        assertEquals(tomorrow.toDate(), events.get(0).getDate());
        assertEquals(now.toDate(), events.get(1).getDate());

        period = new DateInterval(null, tomorrow.minusSeconds(1).toDate());
        iterator = dao.loadEventsOfTypeOnPeriod(eventType, period);
        events = Lists.newArrayList(iterator);
        assertEquals(2, events.size());
        assertEquals(now.toDate(), events.get(0).getDate());
        assertEquals(yesterday.toDate(), events.get(1).getDate());
    }

    @Test
    public void testGetAllEventsOnPeriod() {
        dao.declareEventType("type_1");
        dao.declareEventType("type_2");
        dao.declareEventType("type_3");
        dao.putEvent(new KomeaEvent("jenkins", "type_1", new Date(114, 5, 2)));
        dao.putEvent(new KomeaEvent("jenkins", "type_3", new Date(114, 5, 28)));
        dao.putEvent(new KomeaEvent("jenkins", "type_3", new Date(114, 6, 3)));
        dao.putEvent(new KomeaEvent("jenkins", "type_1", new Date(114, 6, 4)));
        dao.putEvent(new KomeaEvent("jenkins", "type_1", new Date(114, 6, 5)));
        dao.putEvent(new KomeaEvent("jenkins", "type_2", new Date(114, 6, 6)));
        dao.putEvent(new KomeaEvent("jenkins", "type_2", new Date(114, 6, 7)));
        dao.putEvent(new KomeaEvent("jenkins", "type_2", new Date(114, 6, 8)));
        dao.putEvent(new KomeaEvent("jenkins", "type_1", new Date(114, 6, 9)));
        dao.putEvent(new KomeaEvent("jenkins", "type_3", new Date(114, 6, 10)));
        dao.putEvent(new KomeaEvent("jenkins", "type_3", new Date(114, 6, 11)));
        dao.putEvent(new KomeaEvent("jenkins", "type_1", new Date(114, 6, 12)));
        dao.putEvent(new KomeaEvent("jenkins", "type_2", new Date(114, 6, 13)));
        dao.putEvent(new KomeaEvent("jenkins", "type_2", new Date(114, 6, 14)));
        dao.putEvent(new KomeaEvent("jenkins", "type_3", new Date(114, 6, 16)));

        final Date from = new Date(114, 6, 1);
        final Date to = new Date(114, 6, 15);
        final List<KomeaEvent> events = dao.getAllEventsOnPeriod(new DateInterval(from, to), 10);
        assertEquals(10, events.size());
        Date date = null;
        final Map<String, Integer> map = Maps.newHashMap();
        for (final KomeaEvent event : events) {
            final String eventType = event.getEventType();
            if (!map.containsKey(eventType)) {
                map.put(eventType, 0);
            }
            map.put(eventType, map.get(eventType) + 1);
            assertTrue(date == null || event.getDate().before(date));
            assertTrue(event.getDate().after(from));
            assertTrue(event.getDate().before(to));
            date = event.getDate();
        }
        assertEquals(3, map.get("type_1").intValue());
        assertEquals(5, map.get("type_2").intValue());
        assertEquals(2, map.get("type_3").intValue());
    }

}
