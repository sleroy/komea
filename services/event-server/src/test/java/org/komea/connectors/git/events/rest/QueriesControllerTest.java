package org.komea.connectors.git.events.rest;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.queries.formulas.FormulaDto;
import org.komea.event.queries.formulas.FormulaType;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;
import org.komea.microservices.events.rest.QueriesController;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.skife.jdbi.v2.ResultIterator;

@RunWith(MockitoJUnitRunner.class)
public class QueriesControllerTest {

    private static final String AUTHOR_KEY = "author";
    private static final String AUTHOR_1 = "author_1";
    private static final String AUTHOR_2 = "author_2";
    private static final String AUTHOR_3 = "author_3";
    private static final List<KomeaEvent> events = Lists.newArrayList();

    @BeforeClass
    public static void beforeClass() {
        addEvent(null);
        addEvent(AUTHOR_1);
        addEvent(AUTHOR_2);
        addEvent(AUTHOR_2);
        addEvent(AUTHOR_3);
        addEvent(AUTHOR_3);
        addEvent(AUTHOR_3);
        addEvent(AUTHOR_3);
    }

    private static void addEvent(final String author) {
        final KomeaEvent event = new KomeaEvent();
        event.put(AUTHOR_KEY, author);
        events.add(event);
    }

    @Mock
    private IEventStorage eventStorage;
    @Mock
    private IEventDB eventDB;

    @Test
    public void test() throws Exception {
        Mockito.when(eventStorage.getEventDB(Mockito.anyString())).thenReturn(eventDB);
        Mockito.when(eventDB.loadAll()).thenReturn(getResultIterator(events));

        final String eventType = "new_commit";
        final String groupBy = AUTHOR_KEY;
        final FormulaDto formula = new FormulaDto(FormulaType.COUNT);
        final QueriesController controller = new QueriesController();
        controller.setEventStorage(eventStorage);
        final EventsQuery query = new EventsQuery(eventType, groupBy, formula);
        final Map<String, Number> map = controller.execute(query);
        Assert.assertEquals(3, map.size());
        Assert.assertEquals(1, map.get(AUTHOR_1).intValue());
        Assert.assertEquals(2, map.get(AUTHOR_2).intValue());
        Assert.assertEquals(4, map.get(AUTHOR_3).intValue());
    }

    private ResultIterator<KomeaEvent> getResultIterator(final List<KomeaEvent> events) {
        final Iterator<KomeaEvent> iterator = events.iterator();
        return new ResultIterator<KomeaEvent>() {

            @Override
            public void close() {
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public KomeaEvent next() {
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

}
