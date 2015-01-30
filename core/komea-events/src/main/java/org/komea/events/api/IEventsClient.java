package org.komea.events.api;

import java.util.List;
import java.util.Map;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.EventsFilter;
import org.komea.events.dto.EventsQuery;
import org.komea.events.dto.KomeaEvent;

public interface IEventsClient {

    void clearAllEvents();

    void clearEventsOfType(String eventType);

    long countAllEvents();

    long countEventsOfType(String eventType);

    void declareEventType(String eventType);

    Map<String, Number> executeQuery(EventsQuery eventsQuery);

    List<KomeaEvent> getAllEventsOnPeriod(DateInterval period, int limit);

    List<KomeaEvent> getEventsByFilter(EventsFilter filter);

    List<KomeaEvent> getEventsOfType(String eventType);

    List<KomeaEvent> getEventsOfTypeOnPeriod(String eventType, DateInterval interval);

    List<String> getEventTypes();

    void pushEvent(KomeaEvent event);

    String testConnection();

}
