package org.komea.connectors.sdk.rest.impl;

import java.io.IOException;
import java.util.HashMap;
import org.junit.Test;
import org.komea.event.model.impl.DateInterval;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.queries.executor.EventsFilter;
import org.komea.event.queries.executor.EventsQuery;

public class ConsoleEventStorageTest {

    @Test
    public void test() throws IOException {
        final Object objectNotSerializable = new Runnable() {

            @Override
            public void run() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        final ConsoleEventStorage storage = new ConsoleEventStorage();
        storage.clearAllEvents();
        storage.clearEventsOfType("");
        storage.close();
        storage.countAllEvents();
        storage.countEventsOfType("");
        storage.declareEventType("");
        storage.executeQuery(new EventsQuery());
        storage.existStorage("");
        storage.getAllEventsOnPeriod(new DateInterval(), 1);
        storage.getEventDB("");
        storage.getEventTypes();
        storage.getEventsByFilter(new EventsFilter());
        storage.loadEventsOfType("");
        storage.loadEventsOfTypeOnPeriod("", new DateInterval(), 1);
        storage.storeEvent(new KomeaEvent());
        storage.storeEvent(new KomeaEventNotSerializable());
        storage.storeEvent(new HashMap<Object, Object>());
        storage.storeEvent(objectNotSerializable);
    }

    private class KomeaEventNotSerializable extends KomeaEvent {

        private Runnable runnable = new Runnable() {

            @Override
            public void run() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        public Runnable getRunnable() {
            return runnable;
        }

        public void setRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

    }

}
