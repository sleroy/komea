package org.komea.events.dao;

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.KomeaEvent;

public interface IEventsDao extends Closeable {

    void declareEventType(String eventType);

    List<String> getEventTypes();

    List<KomeaEvent> getAllEventsOnPeriod(DateInterval interval, int limit);

    Iterator<KomeaEvent> loadEventsOfType(String eventType);

    Iterator<KomeaEvent> loadEventsOfTypeOnPeriod(String eventType, DateInterval period);

    void putEvent(KomeaEvent event);

    void clearEventsOfType(String eventType);

    long countEventsOfType(String eventType);

}
