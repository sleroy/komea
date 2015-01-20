package org.komea.connectors.git.events;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.BasicEventsListStorage;
import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.model.beans.FlatEvent;
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
        public void storeBasicEvent(final BasicEvent _event) {

            counter++;
            throw new RuntimeException(
                    "Storage is throwing exeception for test purpose.");

        }

        @Override
        public void storeComplexEvent(final ComplexEvent _event) {

            counter++;
            throw new RuntimeException(
                    "Storage is throwing exeception for test purpose.");
        }

        @Override
        public void storeEvent(final AbstractEvent _event) {

            counter++;
            throw new RuntimeException(
                    "Storage is throwing exeception for test purpose.");
        }

        @Override
        public void storeFlatEvent(final FlatEvent _event) {

            throw new RuntimeException(
                    "Storage is throwing exeception for test purpose.");
        }

        @Override
        public void storeMap(final Map<String, Serializable> _fieldMap) {

            counter++;
            throw new RuntimeException(
                    "Storage is throwing exeception for test purpose.");
        }

        @Override
        public void storePojo(final Object _pojo) {

            counter++;
            throw new RuntimeException(
                    "Storage is throwing exeception for test purpose.");
        }

    }

    @Test
    public void testEventsProduction() {

        final BasicEventsListStorage storage = new BasicEventsListStorage();

        final CommitEventProducer producer = new CommitEventProducer(storage);

        repository.processAllCommits(producer);
        Assert.assertEquals(getExpectedNumberOfCommits(), storage.getEvents()
                .size());

        final ComplexEvent event = (ComplexEvent) storage.getEvents().get(0);
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
