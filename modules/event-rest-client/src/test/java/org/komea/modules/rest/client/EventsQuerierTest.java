package org.komea.modules.rest.client;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.queries.formulas.FormulaDto;
import org.komea.event.queries.formulas.FormulaType;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventsQuerierTest {

    @Mock
    private QueriesRestClient client;

    @Test
    public void test() {
        final EventsQuery query = new EventsQuery("new_commit", "author", new FormulaDto(FormulaType.COUNT));
        final EventsQuerier eventsQuerier = new EventsQuerier("http://localhost:8081", "HOUR", query);
        eventsQuerier.setClient(client);

        eventsQuerier.getResult();
        Mockito.verify(client, Mockito.times(1)).execute(query);

        eventsQuerier.setBackupDelay("DAY");
        eventsQuerier.getResult();
        Mockito.verify(client, Mockito.times(2)).execute(query);

        eventsQuerier.setBackupDelay("WEEK");
        eventsQuerier.getResult();
        Mockito.verify(client, Mockito.times(3)).execute(query);

        eventsQuerier.setBackupDelay("MONTH");
        eventsQuerier.getResult();
        Mockito.verify(client, Mockito.times(4)).execute(query);

        eventsQuerier.getResult(new DateTime().minusDays(1), new DateTime());
        Mockito.verify(client, Mockito.times(5)).execute(query);
    }
}
