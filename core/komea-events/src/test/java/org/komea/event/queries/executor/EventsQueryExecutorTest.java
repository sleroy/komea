package org.komea.event.queries.executor;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.model.impl.DateInterval;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.queries.formulas.FormulaDto;
import org.komea.event.queries.formulas.FormulaType;
import org.komea.event.queries.predicates.PredicateDto;
import org.komea.event.queries.predicates.PredicateType;
import org.komea.event.storage.IEventStorage;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.skife.jdbi.v2.ResultIterator;

@RunWith(MockitoJUnitRunner.class)
public class EventsQueryExecutorTest {

    @Mock
    private IEventStorage eventStorage;

    private List<KomeaEvent> events;
    private final String EVENT_TYPE = "new_commit";
    private final String AUTHOR_KEY = "author";

    @Before
    public void before() {
        events = Lists.newArrayList();
        addEvent(new DateTime(), "scarreau");
        addEvent(new DateTime(), "sleroy");
        addEvent(new DateTime(), null);
        events.add(null);
    }

    private void addEvent(final DateTime date, final String author) {
        final KomeaEvent event = new KomeaEvent("scm", EVENT_TYPE, date.toDate());
        event.put(AUTHOR_KEY, author);
        events.add(event);
    }

    @Test
    public void test() {
        Mockito.when(eventStorage.loadEventsOfType(Mockito.anyString())).thenReturn(fromCollection(events));
        Mockito.when(eventStorage.loadEventsOfTypeOnPeriod(Mockito.anyString(),
                Mockito.any(DateInterval.class), Mockito.anyInt())).thenReturn(fromCollection(events));
        final EventsFilter filter = new EventsFilter(EVENT_TYPE, null, null);
        final EventsQuery query = new EventsQuery(filter, AUTHOR_KEY, FormulaDto.of(FormulaType.COUNT));
        final EventsQueryExecutor executor = new EventsQueryExecutor(eventStorage, query);
        executor.execute();
        filter.setInterval(new Date(), new Date());
        filter.setWhere(PredicateDto.of(PredicateType.STRING_EQUALS, "eventType", EVENT_TYPE));
        executor.execute();
    }

    @Test
    public void testEventsQuery() {
        final EventsQuery query = new EventsQuery();
        query.setFilter(query.getFilter());
        query.setFormula(query.getFormula());
        query.setGroupBy(query.getGroupBy());
        query.toString();
    }

    private <T> ResultIterator<T> fromCollection(final Collection<T> collection) {
        final Iterator<T> iterator = collection.iterator();
        return new ResultIterator<T>() {

            @Override
            public void close() {
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

}
