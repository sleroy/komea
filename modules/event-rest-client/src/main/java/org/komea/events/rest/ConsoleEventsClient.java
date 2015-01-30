package org.komea.events.rest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.komea.events.api.IEventsClient;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.EventsFilter;
import org.komea.events.dto.EventsQuery;
import org.komea.events.dto.KomeaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleEventsClient implements IEventsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleEventsClient.class.getName());

    @Override
    public Map<String, Number> executeQuery(final EventsQuery eventsQuery) {
        LOGGER.info("executeQuery : " + eventsQuery);
        return Collections.emptyMap();
    }

    @Override
    public void clearEventsOfType(final String eventType) {
        LOGGER.info("clearEventsOfType : " + eventType);
    }

    @Override
    public void clearAllEvents() {
        LOGGER.info("clearAllEvents");
    }

    @Override
    public void declareEventType(final String eventType) {
        LOGGER.info("declareEventType : " + eventType);
    }

    @Override
    public List<String> getEventTypes() {
        LOGGER.info("getEventTypes");
        return Collections.emptyList();
    }

    @Override
    public List<KomeaEvent> getEventsOfType(final String eventType) {
        LOGGER.info("getEventsOfType : " + eventType);
        return Collections.emptyList();
    }

    @Override
    public long countEventsOfType(final String eventType) {
        LOGGER.info("countEventsOfType : " + eventType);
        return 0;
    }

    @Override
    public List<KomeaEvent> getEventsByFilter(final EventsFilter filter) {
        LOGGER.info("getEventsByFilter : " + filter);
        return Collections.emptyList();
    }

    @Override
    public List<KomeaEvent> getEventsOfTypeOnPeriod(final String eventType, final DateInterval interval) {
        LOGGER.info("getEventsOfTypeOnPeriod : " + eventType + " - " + interval);
        return Collections.emptyList();
    }

    @Override
    public void pushEvent(final KomeaEvent event) {
        LOGGER.info("pushEvent : " + event);
    }

    @Override
    public String testConnection() {
        LOGGER.info("testConnection");
        return "Komea Console Client";
    }

    @Override
    public long countAllEvents() {
        LOGGER.info("countAllEvents");
        return 0;
    }

    @Override
    public List<KomeaEvent> getAllEventsOnPeriod(DateInterval period, int limit) {
        LOGGER.info("getAllEventsOnPeriod : " + period + " - " + limit);
        return Collections.emptyList();
    }
}
