package org.komea.connectors.git;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;

public class BasicEventsListStorage implements IEventStorage {

    List<KomeaEvent> events = Lists.newArrayList();

    @Override
    public void clearEventsOfType(final String _eventType) {

    }

    @Override
    public void close() throws IOException {

    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.event.storage.IEventStorage#declareEventType(java.lang.String)
     */
    @Override
    public void declareEventType(final String _type) {
        // TODO Auto-generated method stub

    }

    public List<KomeaEvent> getEvents() {

        return events;
    }

    @Override
    public void storeEvent(final KomeaEvent _event) {
        events.add(_event);
    }

    @Override
    public IEventDB getEventDB(String eventType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
