package org.komea.event.storage.impl;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.komea.event.generator.IEventDefinition;
import org.komea.event.generator.KpiRange;
import org.komea.event.generator.impl.EventGenerator;
import org.komea.event.model.IBasicEventInformations;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.factory.EventStorageFactory;
import org.komea.event.queries.factory.Impl;
import org.komea.event.storage.DateInterval;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;
import org.komea.event.storage.impl.EventStorage;
import org.skife.jdbi.v2.ResultIterator;

public class EventStorageTest {

    private static final Logger LOGGER = Logger.getLogger(EventStorageTest.class.getName());
    private static final KpiRange range = KpiRange.DAY;
    private static final Interval intervalDate = new Interval(
            new DateTime().minusYears(1), new DateTime());
    private static final int numberDevelopers = 1;
    private static final int commitPerDay = 1;
    private static final String NEW_COMMIT = "new_commit";

    @Test
    public void testDbH2MemJackson() throws Exception {
        testDb(Impl.H2_MEM_JACKSON);
    }

    @Test
    public void testDbH2MemKryo() throws Exception {
        testDb(Impl.H2_MEM_KRYO);
    }

    @Test
    public void testDbH2FileJackson() throws Exception {
        testDb(Impl.H2_DISK_JACKSON);
    }

    @Test
    public void testDbH2FileKryo() throws Exception {
        testDb(Impl.H2_DISK_KRYO);
    }

    @Ignore
    @Test
    public void testDbMysqlJackson() throws Exception {
        testDb(Impl.MYSQL_JACKSON);
    }

    @Ignore
    @Test
    public void testDbMysqlKryo() throws Exception {
        testDb(Impl.MYSQL_KRYO);
    }

    public void testDb(final Impl dbImpl) throws Exception {
        EventStorage eventStorage = null;
        try {
            eventStorage = EventStorageFactory.get().newEventStorage(dbImpl);
            final IEventDB eventDB = eventStorage.getEventDBFactory().getEventDB(NEW_COMMIT);
            final EventGenerator eventGenerator = getEventGenerator(eventStorage);
            eventDB.removeAll();
            Assert.assertEquals(0, eventDB.count());
            final int generatedEvents = eventGenerator.generate(numberDevelopers * commitPerDay, range, intervalDate);
            LOGGER.log(Level.INFO, "Has generated {0} events", generatedEvents);

            long nbDays = intervalDate.toDuration().getStandardDays() + 1;
            final long totalEvents = numberDevelopers * commitPerDay * nbDays;
            Assert.assertEquals(totalEvents, generatedEvents);
            Assert.assertEquals(totalEvents, eventDB.count());
            Assert.assertEquals(totalEvents, countEvents(eventDB.loadAll()));

            final DateTime now = DateTime.now();
            final DateTime from = now.minusDays(100);
            final DateTime to = now.minusDays(50);
            final DateInterval dateInterval = new DateInterval(from, to);
            nbDays = new Interval(from, to).toDuration().getStandardDays();
            Assert.assertEquals(50, nbDays);
            final long intervalEvents = numberDevelopers * commitPerDay * nbDays;
            Assert.assertEquals(intervalEvents, countEvents(eventDB.loadOnPeriod(dateInterval)));

            eventDB.removeAll();
            Assert.assertEquals(0, eventDB.count());
        } finally {
            IOUtils.closeQuietly(eventStorage);
        }
    }

    private EventGenerator getEventGenerator(final IEventStorage eventStorage) {
        return new EventGenerator(eventStorage,
                new IEventDefinition() {

                    private final Random RANDOM = new Random();

                    @Override
                    public void decorateEvent(final DateTime _date,
                            final int _nthEventOfDay, final FlatEvent event) {
                        event.put(IBasicEventInformations.FIELD_EVENT_TYPE, NEW_COMMIT);
                        event.put(IBasicEventInformations.FIELD_PROVIDER, "perforce");
                        event.put("project", "KOMEA");
                        event.put("numberOfChangedLines", 100);
                        event.put("numberOfDeletedLines", 200);
                        event.put("numberOfModifiedLines", 100);
                        event.put("numberOfAddedLines", 100);
                        event.put("commitMessage", "New commit happened");
                        event.put("id", RANDOM.nextLong());
                        event.put("author", "dev");
                    }
                });
    }

    private long countEvents(ResultIterator<FlatEvent> iterator) {
        long cpt = 0;
        while (iterator.hasNext()) {
            iterator.next();
            cpt++;
        }
        return cpt;
    }

}
