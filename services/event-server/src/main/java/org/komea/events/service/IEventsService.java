package org.komea.events.service;

import java.util.Iterator;
import java.util.List;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.KomeaEvent;

public interface IEventsService {

    void clearAllEvents();

    void clearEventsOfType(String eventType);

    long countAllEvents();

    long countEventsOfType(String eventType);

    void declareEventType(String eventType);

    List<String> getEventTypes();

    List<KomeaEvent> getAllEventsOnPeriod(DateInterval interval, int limit);

    Iterator<KomeaEvent> loadEventsOfType(String eventType);

    Iterator<KomeaEvent> loadEventsOfTypeOnPeriod(String eventType, DateInterval interval);

    void storeEvent(KomeaEvent event);

}
