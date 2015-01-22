package org.komea.event.storage;

import java.io.Closeable;
import org.komea.event.model.KomeaEvent;

public interface IEventStorage extends Closeable {

    /**
     * Clear of all the events of the type
     *
     * @param _eventType the event type;
     */
    public void clearEventsOfType(String _eventType);

    public void declareEventType(String type);

    /**
     * Stores a event with a flattened structure.
     *
     * @param _event the event.
     */
    public void storeEvent(KomeaEvent _event);

    IEventDB getEventDB(String eventType);
}
