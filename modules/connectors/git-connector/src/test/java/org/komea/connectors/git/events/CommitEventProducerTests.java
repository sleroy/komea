package org.komea.connectors.git.events;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.BasicEventsListStorage;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;

public class CommitEventProducerTests extends AbstractLocalGitTest {

    private static class NotWorkingStorage implements IEventStorage {

        int counter;

        @Override
        public void clearEventsOfType(final String _eventType) {

            // TODO Auto-generated method stub
        }

        @Override
        public void close() throws IOException {

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
        public void storeEvent(final KomeaEvent _event) {
            counter++;
            throw new RuntimeException(
                    "Storage is throwing exeception for test purpose.");
        }

        @Override
        public IEventDB getEventDB(String eventType) {
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
