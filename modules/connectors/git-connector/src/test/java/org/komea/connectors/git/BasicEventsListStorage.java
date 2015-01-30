package org.komea.connectors.git;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import org.komea.events.api.IEventsClient;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.EventsFilter;
import org.komea.events.dto.EventsQuery;
import org.komea.events.dto.KomeaEvent;

public class BasicEventsListStorage implements IEventsClient {

    List<KomeaEvent> events = Lists.newArrayList();

    @Override
    public void clearEventsOfType(final String _eventType) {

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
    public void pushEvent(final KomeaEvent _event) {
        events.add(_event);
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
