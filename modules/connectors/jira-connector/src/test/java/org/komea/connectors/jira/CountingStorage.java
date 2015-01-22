package org.komea.connectors.jira;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;

public class CountingStorage implements IEventStorage {

    public Map<String, Integer> counters;

    public CountingStorage() {

        counters = Maps.newHashMap();
    }

    @Override
    public void clearEventsOfType(final String _eventType) {

        // TODO Auto-generated method stub
    }

    @Override
    public void close() throws IOException {

        // TODO Auto-generated method stub
    }

    @Override
    public void storeEvent(final KomeaEvent _event) {
        increment(_event.getEventType());
    }

    @Override
    public void declareEventType(final String type) {

        // TODO Auto-generated method stub
    }

    private void increment(final String type) {

        Integer current = counters.get(type);
        if (current == null) {
            current = 0;
        }
        counters.put(type, current + 1);
    }

    @Override
    public IEventDB getEventDB(String eventType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
