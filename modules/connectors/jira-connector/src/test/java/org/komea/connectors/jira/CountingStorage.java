package org.komea.connectors.jira;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.komea.events.api.IEventsClient;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.EventsFilter;
import org.komea.events.dto.EventsQuery;
import org.komea.events.dto.KomeaEvent;

public class CountingStorage implements IEventsClient {

    public Map<String, Integer> counters;

    public CountingStorage() {

        counters = Maps.newHashMap();
    }

    @Override
    public void clearEventsOfType(final String _eventType) {

        // TODO Auto-generated method stub
    }

    @Override
    public void pushEvent(final KomeaEvent _event) {
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
