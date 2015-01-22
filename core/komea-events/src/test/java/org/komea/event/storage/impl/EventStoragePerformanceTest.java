package org.komea.event.storage.impl;

import com.google.common.collect.Lists;
import com.tocea.frameworks.bench4j.BenchmarkOptions;
import com.tocea.frameworks.bench4j.IBenchReport;
import com.tocea.frameworks.bench4j.impl.BenchRule;
import com.tocea.frameworks.bench4j.reports.jfreechart.JFreeChartBenchmarkReport;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.komea.event.model.KomeaEvent;
import org.komea.event.queries.factory.EventStorageFactory;
import org.komea.event.queries.factory.Impl;
import org.skife.jdbi.v2.ResultIterator;

@RunWith(Parameterized.class)
public class EventStoragePerformanceTest {

    public static enum EventsNumber {

        TEST_NUMBER(10), PICO_NUMBER(100), TINY_NUMBER(1000), VERY_SMALL_NUMBER(
                10000), MEDIUM_NUMBER(100000), BIG_NUMBER(1000000);

        public int value;

        /**
         *
         */
        private EventsNumber(final int _value) {
            value = _value;
        }
    }

    public static enum EventsTypeNumber {

        MANY_EVENT_TYPES(10), SINGLE_EVENT_TYPE(1),;

        public int value;

        /**
         *
         */
        private EventsTypeNumber(final int _value) {
            value = _value;
        }
    }

    public static enum ThreadNumber {

        MULTITHREAD(10), MONOTHREAD(1);

        public final int value;

        private ThreadNumber(final int _value) {
            value = _value;
        }
    }

    @Parameters(name = "number_events={0},type_events={1},threads={2},impl={3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {EventsNumber.PICO_NUMBER, EventsTypeNumber.MANY_EVENT_TYPES,
                ThreadNumber.MULTITHREAD, Impl.H2_MEM_JACKSON},
            {EventsNumber.PICO_NUMBER, EventsTypeNumber.MANY_EVENT_TYPES,
                ThreadNumber.MULTITHREAD, Impl.H2_DISK_JACKSON},
            {EventsNumber.PICO_NUMBER, EventsTypeNumber.MANY_EVENT_TYPES,
                ThreadNumber.MULTITHREAD, Impl.H2_MEM_KRYO,},
            {EventsNumber.PICO_NUMBER, EventsTypeNumber.MANY_EVENT_TYPES,
                ThreadNumber.MULTITHREAD, Impl.H2_DISK_KRYO,},});
    }

    private static final int BENCH = 2;

    private static final int WARMUP = 1;

    public static final IBenchReport report = new JFreeChartBenchmarkReport(
            new File("build/charts"), 1024, 768, true);

    /**
     * Enables the benchmark rule.
     */
    @Rule
    public BenchRule benchmarkRun = new BenchRule(report);

    @Parameter(value = 0)
    public EventsNumber NUMBER_EVENTS;

    @Parameter(value = 1)
    public EventsTypeNumber TYPE_EVENTS;

    @Parameter(value = 2)
    public ThreadNumber NUMBER_THREADS;

    @Parameter(value = 3)
    public Impl impl;

    public final List<KomeaEvent> DEMO_EVENT = Lists.newArrayList();

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void before() {
        DEMO_EVENT.clear();
        for (int t = 0; t < TYPE_EVENTS.value; ++t) {
            final KomeaEvent flatEvent = new KomeaEvent();
            flatEvent.setProvider("bugzilla");
            flatEvent.setEventType(getEventType(t));
            flatEvent.put("bug_id", 12);
            flatEvent.put("message", "Call to XXX creates premature exception");
            flatEvent.put("author", "sleroy");
            flatEvent.put("reporter", "rgalerme");
            DEMO_EVENT.add(flatEvent);
        }
    }

    public void performInsertion(final EventStorage _eFactory)
            throws InterruptedException {
        performDeletion(_eFactory);
        final ExecutorService executorService = Executors
                .newFixedThreadPool(NUMBER_THREADS.value);

        for (int i = 0; i < NUMBER_THREADS.value; ++i) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    int res = 0;
                    for (int j = 0; j < NUMBER_EVENTS.value; ++j) {
                        for (final KomeaEvent fe : DEMO_EVENT) {
                            res++;

                            _eFactory.storeEvent(fe);
                        }
                    }
                    System.out.println("Inserted " + res);
                }
            });
        }
        executorService.shutdown();
        final boolean awaitTermination = executorService.awaitTermination(10,
                TimeUnit.MINUTES);
        assertTrue(awaitTermination);

        for (int t = 0; t < TYPE_EVENTS.value; ++t) {
            final long count = _eFactory.getEventDB(getEventType(t))
                    .count();
            System.out.println("Number of events : " + count);
            assertEquals(NUMBER_EVENTS.value * NUMBER_THREADS.value, count);

        }

    }

    @BenchmarkOptions(warmupRounds = WARMUP, benchmarkRounds = BENCH)
    @Test
    public void testInsertion() throws Exception {
        EventStorage eventStorage = null;
        try {
            eventStorage = EventStorageFactory.get().newEventStorage(impl);
            declareEventTypes(eventStorage);
            performInsertion(eventStorage);
            performFetchAll(eventStorage);
            performDeletion(eventStorage);
        } finally {
            IOUtils.closeQuietly(eventStorage);
        }
    }

    /**
     * @param _eventStorage
     */
    private void declareEventTypes(final EventStorage _eventStorage) {
        for (int t = 0; t < TYPE_EVENTS.value; ++t) {
            _eventStorage.declareEventType(getEventType(t));
        }

    }

    private String getEventType(final int t) {

        return "eventType" + t;
    }

    /**
     * @param _eFactory
     */
    private void performDeletion(final EventStorage _eFactory) {

        for (int t = 0; t < TYPE_EVENTS.value; ++t) {
            final String eventType = getEventType(t);
            _eFactory.getEventDB(eventType).removeAll();
            assertEquals(0, _eFactory.getEventDB(eventType).count());
        }

    }

    /**
     * @param _eFactory
     * @throws InterruptedException
     */
    private void performFetchAll(final EventStorage _eFactory)
            throws InterruptedException {
        final ExecutorService executorService = Executors
                .newFixedThreadPool(NUMBER_THREADS.value);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int t = 0; t < TYPE_EVENTS.value; ++t) {
                    final String eventType = getEventType(t);
                    int read = 0;
                    final long count = _eFactory.getEventDB(eventType).count();
                    try (final ResultIterator<KomeaEvent> loadAll
                            = _eFactory.getEventDB(eventType).loadAll()) {
                        while (loadAll.hasNext()) {
                            assertNotNull(loadAll.next());
                            read++;
                        }
                        assertEquals(count, read);
                    }
                }
            }
        });
        executorService.shutdown();
        final boolean awaitTermination = executorService.awaitTermination(10,
                TimeUnit.MINUTES);
        assertTrue(awaitTermination);
    }
}
