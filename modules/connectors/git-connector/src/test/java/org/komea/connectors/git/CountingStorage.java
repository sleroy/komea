package org.komea.connectors.git;

import java.io.IOException;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;

public class CountingStorage implements IEventStorage {

    public int count;

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

        count++;
    }

    @Override
    public void declareEventType(final String type) {

        // TODO Auto-generated method stub
    }

    @Override
    public IEventDB getEventDB(String eventType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
