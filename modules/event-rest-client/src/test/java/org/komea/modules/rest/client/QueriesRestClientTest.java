package org.komea.modules.rest.client;

import java.util.Map;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.queries.formulas.FormulaDto;
import org.komea.event.queries.formulas.FormulaType;
import org.komea.event.model.DateInterval;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class QueriesRestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void test() {
        final QueriesRestClient client = new QueriesRestClient("http://localhost:8081");
        client.setRestTemplate(restTemplate);
        final String eventType = "new_commit";
        final String groupBy = "provider";
        final EventsQuery eventsQuery = new EventsQuery(eventType, groupBy, new FormulaDto(FormulaType.COUNT));
        eventsQuery.setInterval(new DateInterval(DateTime.now().minusDays(1).toDate(), DateTime.now().toDate()));
        client.execute(eventsQuery);

        Mockito.verify(restTemplate, Mockito.times(1)).
                postForObject("http://localhost:8081/rest/queries/execute", eventsQuery, Map.class);
    }
}
