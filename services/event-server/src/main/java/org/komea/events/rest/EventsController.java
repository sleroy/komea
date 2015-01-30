package org.komea.events.rest;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.EventsFilter;
import org.komea.events.dto.EventsQuery;
import org.komea.events.dto.KomeaEvent;
import org.komea.events.queries.EventsQueryExecutor;
import org.komea.events.service.IEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/events")
public class EventsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class.getName());

    @Autowired
    private IEventsService eventsService;

    @RequestMapping(method = RequestMethod.POST, value = "/executeQuery",
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map<String, Number> executeQuery(@RequestBody @Valid final EventsQuery eventsQuery) {
        LOGGER.info("EventsController - executeQuery : " + eventsQuery);
        declareEventType(eventsQuery.getFilter().getEventType());
        final EventsQueryExecutor eventsQueryExecutor = new EventsQueryExecutor(eventsService, eventsQuery);
        return eventsQueryExecutor.execute();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getEventsByFilter",
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<KomeaEvent> getEventsByFilter(@RequestBody @Valid final EventsFilter filter) {
        LOGGER.info("EventsController - getEventsByFilter : " + filter);
        declareEventType(filter.getEventType());
        return EventsQueryExecutor.filterEvents(filter, eventsService);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/clearEventsOfType/{eventType}")
    @ResponseStatus(HttpStatus.OK)
    public void clearEventsOfType(@PathVariable final String eventType) {
        LOGGER.info("EventsController - clearEventsOfType : " + eventType);
        declareEventType(eventType);
        eventsService.clearEventsOfType(eventType);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/countAllEvents")
    @ResponseBody
    public long countEventsOfType() {
        LOGGER.info("EventsController - countAllEvents");
        return eventsService.countAllEvents();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/countEventsOfType/{eventType}")
    @ResponseBody
    public long countEventsOfType(@PathVariable final String eventType) {
        LOGGER.info("EventsController - countEventsOfType : " + eventType);
        declareEventType(eventType);
        return eventsService.countEventsOfType(eventType);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/clearAllEvents")
    @ResponseStatus(HttpStatus.OK)
    public void clearAllEvents() {
        LOGGER.info("EventsController - clearAllEvents");
        eventsService.clearAllEvents();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/declareEventType")
    @ResponseStatus(HttpStatus.OK)
    public void declareEventType(@RequestBody @Nonnull @Size(min = 1) final String eventType) {
        LOGGER.info("EventsController - declareEventType : " + eventType);
        eventsService.declareEventType(eventType);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pushEvent",
            consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void pushEvent(@RequestBody @Valid final KomeaEvent event) {
        LOGGER.info("EventsController - pushEvent : " + event);
        declareEventType(event.getEventType());
        eventsService.storeEvent(event);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getAllEventsOnPeriod/{limit}",
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<KomeaEvent> getAllEventsOnPeriod(
            @PathVariable final int limit, final DateInterval interval) {
        LOGGER.info("EventsController - getAllEventsOnPeriod : " + interval + " - " + limit);
        return eventsService.getAllEventsOnPeriod(interval, limit);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getEventTypes")
    @ResponseBody
    public List<String> getEventTypes() {
        LOGGER.info("EventsController - getEventTypes");
        return eventsService.getEventTypes();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    @ResponseBody
    public String testConnection() {
        return "Komea Eventory Server";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getEventsOfType/{eventType}",
            produces = "application/json")
    @ResponseBody
    public List<KomeaEvent> getEventsOfType(@PathVariable final String eventType) {
        LOGGER.info("EventsController - getEventsOfType : " + eventType);
        declareEventType(eventType);
        final Iterator<KomeaEvent> iterator = eventsService.loadEventsOfType(eventType);
        return Lists.newArrayList(iterator);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getEventsOfTypeOnPeriod/{eventType}",
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<KomeaEvent> getEventsOfTypeOnPeriod(@PathVariable final String eventType,
            @RequestBody @Valid final DateInterval interval) {
        LOGGER.info("EventsController - getEventsOfTypeOnPeriod : " + eventType);
        declareEventType(eventType);
        final Iterator<KomeaEvent> iterator
                = eventsService.loadEventsOfTypeOnPeriod(eventType, interval);
        return Lists.newArrayList(iterator);
    }

    public void setEventsService(final IEventsService eventsService) {
        this.eventsService = eventsService;
    }

}
