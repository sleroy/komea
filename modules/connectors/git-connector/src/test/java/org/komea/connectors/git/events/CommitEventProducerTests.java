package org.komea.connectors.git.events;

import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.BasicEventsListStorage;
import org.komea.events.api.IEventsClient;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.EventsFilter;
import org.komea.events.dto.EventsQuery;
import org.komea.events.dto.KomeaEvent;

public class CommitEventProducerTests extends AbstractLocalGitTest {

    private static class NotWorkingStorage implements IEventsClient {

        int counter;

        @Override
        public void clearEventsOfType(final String _eventType) {

            // TODO Auto-generated method stub
        }

        /*
         * (non-Javadoc)
         * @see
         * org.komea.event.storage.IEventStorage#declareEventType(java.lang.
         * String)
         */
        @Override
        public void declareEventType(final String _type) {
            //

        }

        @Override
        public void pushEvent(final KomeaEvent _event) {
            counter++;
            throw new RuntimeException(
                    "Storage is throwing exeception for test purpose.");
        }

        @Override
        public void clearAllEvents() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long countEventsOfType(String eventType) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Map<String, Number> executeQuery(EventsQuery eventsQuery) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<KomeaEvent> getEventsOfType(String eventType) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<KomeaEvent> getEventsOfTypeOnPeriod(String eventType, DateInterval interval) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<String> getEventTypes() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String testConnection() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<KomeaEvent> getEventsByFilter(EventsFilter filter) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public long countAllEvents() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<KomeaEvent> getAllEventsOnPeriod(DateInterval period, int limit) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    @Test
    public void testEventsProduction() {

        final BasicEventsListStorage storage = new BasicEventsListStorage();

        final CommitEventProducer producer = new CommitEventProducer(storage);

        repository.processAllCommits(producer);
        Assert.assertEquals(getExpectedNumberOfCommits(), storage.getEvents()
                .size());

        final KomeaEvent event = storage.getEvents().get(0);
        final String author = (String) event.getProperties().get("author");
        Assert.assertNotNull(author);
    }

    @Test(expected = RuntimeException.class)
    public void testExceptionInEventsProduction() {

        final NotWorkingStorage storage = new NotWorkingStorage();

        final CommitEventProducer producer = new CommitEventProducer(storage);

        repository.processAllCommits(producer);
    }
}
