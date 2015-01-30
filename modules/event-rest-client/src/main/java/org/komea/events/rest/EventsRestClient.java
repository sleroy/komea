package org.komea.events.rest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.komea.events.api.IEventsClient;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.EventsFilter;
import org.komea.events.dto.EventsQuery;
import org.komea.events.dto.KomeaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsRestClient extends AbstractRestClient implements IEventsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsRestClient.class.getName());
    private static final String BASE_REST_URL = "/events";

    public EventsRestClient(final String eventoryUrl) {
        super(eventoryUrl + BASE_REST_URL);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Number> executeQuery(final EventsQuery eventsQuery) {
        LOGGER.info("EventsClient - executeQuery : " + eventsQuery);
        return post("executeQuery", eventsQuery, Map.class);
    }

    @Override
    public void clearEventsOfType(final String eventType) {
        LOGGER.info("EventsClient - clearEventsOfType : " + eventType);
        delete("clearEventsOfType/" + eventType);
    }

    @Override
    public void clearAllEvents() {
        LOGGER.info("EventsClient - clearAllEvents");
        delete("clearAllEvents");
    }

    @Override
    public void declareEventType(final String eventType) {
        LOGGER.info("EventsClient - declareEventType : " + eventType);
        post("declareEventType", eventType, Void.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<KomeaEvent> getAllEventsOnPeriod(final DateInterval period, final int limit) {
        LOGGER.info("EventsClient - getAllEventsOnPeriod : " + period + " - " + limit);
        return Arrays.asList(post("getAllEventsOnPeriod/" + limit, period, KomeaEvent[].class));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getEventTypes() {
        LOGGER.info("EventsClient - getEventTypes");
        return Arrays.asList(get("getEventTypes", String[].class));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<KomeaEvent> getEventsByFilter(final EventsFilter filter) {
        LOGGER.info("EventsClient - getEventsByFilter : " + filter);
        return Arrays.asList(post("getEventsByFilter", filter, KomeaEvent[].class));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<KomeaEvent> getEventsOfType(final String eventType) {
        LOGGER.info("EventsClient - getEventsOfType : " + eventType);
        return Arrays.asList(get("getEventsOfType/" + eventType, KomeaEvent[].class));
    }

    @Override
    public long countAllEvents() {
        LOGGER.info("EventsClient - countAllEvents");
        return get("countAllEvents", Long.class);
    }

    @Override
    public long countEventsOfType(final String eventType) {
        LOGGER.info("EventsClient - countEventsOfType : " + eventType);
        return get("countEventsOfType/" + eventType, Long.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<KomeaEvent> getEventsOfTypeOnPeriod(final String eventType, final DateInterval interval) {
        LOGGER.info("EventsClient - getEventsOfTypeOnPeriod : " + eventType + " - " + interval);
        return Arrays.asList(post("getEventsOfTypeOnPeriod/" + eventType, interval, KomeaEvent[].class));
    }

    @Override
    public void pushEvent(final KomeaEvent event) {
        LOGGER.info("EventsClient - pushEvent : " + event);
        post("pushEvent", event, Void.class);
    }

    @Override
    public String testConnection() {
        LOGGER.info("EventsClient - testConnection");
        return get("hello", String.class);
    }
}
