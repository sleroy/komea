/**
 *
 */
package org.komea.event.queries.demo;

import com.tocea.frameworks.bench4j.BenchmarkOptions;
import com.tocea.frameworks.bench4j.impl.BenchRule;
import java.io.IOException;
import java.util.Random;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.komea.event.generator.IEventDefinition;
import org.komea.event.generator.KpiRange;
import org.komea.event.generator.impl.EventGenerator;
import org.komea.event.kpi.impl.IKpi;
import org.komea.event.kpi.impl.QueryResult;
import org.komea.event.kpi.impl.QueryResultBuilder;
import org.komea.event.model.IBasicEventInformations;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.executor.impl.EventQuery;
import org.komea.event.queries.executor.impl.EventQueryExecutor;
import org.komea.event.queries.executor.impl.EventQueryResult;
import org.komea.event.queries.factory.EventStorageFactory;
import org.komea.event.queries.factory.Impl;
import org.komea.event.queries.result.impl.AggregateSumResultMapper;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.impl.EventStorage;
import org.komea.event.utils.date.impl.IntervalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sleroy
 */
@BenchmarkOptions(warmupRounds = 10, benchmarkRounds = 20)
public class PersonAddedLinesTest {

    /**
     * @author sleroy
     */
    private final class PersonAddedLinesKpi implements IKpi {

        @Override
        public QueryResult compute(final DateTime _beginPeriod,
                final DateTime _endPeriod, final IEventDBFactory _eventDBFactory) {
            final EventQuery eventQuery = new EventQuery();
            eventQuery.forPeriod(_beginPeriod, _endPeriod);
            eventQuery.eventTypes(NEW_COMMIT);
            eventQuery.groupBy("author");
            eventQuery.returnsResultMapper(new AggregateSumResultMapper(
                    "author", "numberOfAddedLines"));
            final EventQueryExecutor eventQueryExecutor = new EventQueryExecutor(
                    _eventDBFactory, eventQuery);
            final EventQueryResult execute = eventQueryExecutor.execute();
            return new QueryResultBuilder(execute, "author",
                    "numberOfAddedLines").build();
        }

        @Override
        public KpiRange getRange() {

            return KpiRange.DAY;
        }
    }

    @AfterClass
    public static void after() throws IOException {
        eventStorage.close();
    }

    @BeforeClass
    public static void before() throws Exception {
        eventStorage = EventStorageFactory.get().newEventStorage(Impl.H2_DISK_JACKSON);

        eventGenerator = new EventGenerator(eventStorage,
                new IEventDefinition() {

                    private final Random RANDOM = new Random();

                    @Override
                    public void decorateEvent(final DateTime _date,
                            final int _nthEventOfDay, final FlatEvent _flatEvent) {
                        _flatEvent.put(
                                IBasicEventInformations.FIELD_EVENT_TYPE,
                                NEW_COMMIT);
                        _flatEvent.put(IBasicEventInformations.FIELD_PROVIDER,
                                "perforce");
                        _flatEvent.put("project", "KOMEA");
                        _flatEvent.put("numberOfChangedLines", 100);
                        _flatEvent.put("numberOfDeletedLines", 200);
                        _flatEvent.put("numberOfModifiedLines", 100);
                        _flatEvent.put("numberOfAddedLines", 100);
                        _flatEvent.put("commitMessage", "New commit happened");
                        _flatEvent.put("id", RANDOM.nextLong());
                        _flatEvent.put("author", "dev" + _nthEventOfDay
                                % numberDevelopers);

                    }
                });
        final int generatedEvents = eventGenerator.generate(commitPerDay
                * numberDevelopers, range, intervalDate);

        LOGGER.info("Has generated {} events", generatedEvents);
    }

    /**
     * Enables the benchmark rule.
     */
    @Rule
    public BenchRule benchmarkRun = new BenchRule();

    /**
     *
     */
    private static final String NEW_COMMIT = "new_commit";

    public static KpiRange range = KpiRange.DAY;

    public static Interval intervalDate = new Interval(
            new DateTime().minusYears(1), new DateTime());

    public static int numberDevelopers = 1;
    public static int commitPerDay = 1;

    public static EventStorage eventStorage;

    public static EventGenerator eventGenerator;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(PersonAddedLinesTest.class);

    @Test
    public void evaluateBatch() {
        final IKpi iKpi = new PersonAddedLinesKpi();
        DateTime begin = intervalDate.getStart();
        while (begin.isBefore(intervalDate.getEnd())) {
            final DateTime nextPeriod = IntervalUtils.plus(begin,
                    iKpi.getRange());
            iKpi.compute(begin, nextPeriod, eventStorage.getEventDBFactory());
            begin = nextPeriod;
        }
    }

    @Test
    public void evaluateCurrentValue() {
        final IKpi iKpi = new PersonAddedLinesKpi();
        final DateTime now = new DateTime();
        final QueryResult compute = iKpi.compute(
                IntervalUtils.minus(intervalDate.getEnd(), iKpi.getRange()),
                now, eventStorage.getEventDBFactory());
        System.out.println(compute);
    }

    @Test
    public void evaluateGlobalValue() {
        final IKpi iKpi = new PersonAddedLinesKpi();
        final QueryResult compute = iKpi.compute(intervalDate.getStart(),
                intervalDate.getEnd(), eventStorage.getEventDBFactory());
        System.out.println(compute);
    }
}
