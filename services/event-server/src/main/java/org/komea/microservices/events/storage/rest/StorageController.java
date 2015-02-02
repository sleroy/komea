package org.komea.microservices.events.storage.rest;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.impl.DateInterval;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.queries.executor.EventsFilter;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.storage.IEventStorage;
import org.komea.microservices.events.database.model.ValueEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/storage")
public class StorageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageController.class);

    @Autowired
    private IEventStorage eventStorageService;
    @Autowired
    private IMessageSender messageSender;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{eventType}/clear")
    @ResponseStatus(value = HttpStatus.OK)
    public void clearEvent(@PathVariable final String eventType) {
        if (!this.eventStorageService.existStorage(eventType)) {
            LOGGER.debug("Ignored query : Event type {} does not exist.",
                    eventType);
            return;
        }
        this.eventStorageService.clearEventsOfType(eventType);

    }

    @Transactional(isolation = Isolation.DEFAULT, readOnly = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{eventType}/count")
    @ResponseStatus(value = HttpStatus.OK)
    public Long countEvent(@PathVariable final String eventType) {
        if (!this.eventStorageService.existStorage(eventType)) {
            LOGGER.debug("Ignored query : Event type {} does not exist.",
                    eventType);
            return 0L;
        }
        return this.eventStorageService.getEventDB(eventType).count();

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @RequestMapping(method = RequestMethod.GET, value = "/{eventType}/declare")
    @ResponseStatus(value = HttpStatus.OK)
    public void declareEvent(@PathVariable final String eventType) {
        this.eventStorageService.declareEventType(eventType);

    }

    @Transactional(isolation = Isolation.DEFAULT, readOnly = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{eventType}/last")
    @ResponseStatus(value = HttpStatus.OK)
    public Date getLastEvent(@PathVariable final String eventType) {
        if (!this.eventStorageService.existStorage(eventType)) {
            LOGGER.debug("Ignored query : Event type {} does not exist.",
                    eventType);
            return null;
        }
        return this.eventStorageService.getEventDB(eventType)
                .getLastEvent()
                .toDate();

    }

    @RequestMapping(method = RequestMethod.POST, value = "/push/post")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushGetEvent(final HttpServletRequest _request) {
        final KomeaEvent komeaEvent = new KomeaEvent();
        final Enumeration<String> attributeNames = _request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attr;
            attr = attributeNames.nextElement();
            komeaEvent.put(attr, _request.getAttribute(attr));
        }
        final Enumeration<String> parameterNames = _request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String attr;
            attr = parameterNames.nextElement();
            komeaEvent.put(attr, _request.getAttribute(attr));
        }
        pushEvent(komeaEvent);
    }

    private void pushEvent(final KomeaEvent event) {
        if (messageSender == null) {
            LOGGER.warn("JMS message sender not initialized, event sending directly to storage.");
            eventStorageService.storeEvent(event);
        } else {
            messageSender.pushEvent(event);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/push/{provider}/{eventName}/{value}")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushGetEvent(@NotEmpty @PathVariable final String provider,
            @NotEmpty @PathVariable final String eventName,
            @NotNull @PathVariable final Double value) {

        final ValueEvent valueEvent = new ValueEvent();

        valueEvent.setEventType(eventName);
        valueEvent.setValue(value);
        valueEvent.setProvider(provider);
        valueEvent.setDate(new Date());
        pushEvent(valueEvent);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/push")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushPostEvent(@RequestBody final KomeaEvent _event) {
        pushEvent(_event);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/executeQuery",
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map<String, Number> executeQuery(@RequestBody @Valid final EventsQuery eventsQuery) {
        return this.eventStorageService.executeQuery(eventsQuery);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getEventsByFilter",
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<KomeaEvent> getEventsByFilter(@RequestBody @Valid final EventsFilter filter) {
        return this.eventStorageService.getEventsByFilter(filter);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/countAllEvents")
    @ResponseBody
    public long countAllEvents() {
        return this.eventStorageService.countAllEvents();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/clearAllEvents")
    @ResponseStatus(HttpStatus.OK)
    public void clearAllEvents() {
        this.eventStorageService.clearAllEvents();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getAllEventsOnPeriod/{limit}",
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<KomeaEvent> getAllEventsOnPeriod(
            @PathVariable final int limit, final DateInterval interval) {
        return this.eventStorageService.getAllEventsOnPeriod(interval, limit);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getEventTypes")
    @ResponseBody
    public List<String> getEventTypes() {
        return this.eventStorageService.getEventTypes();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getEventsOfType/{eventType}",
            produces = "application/json")
    @ResponseBody
    public List<KomeaEvent> getEventsOfType(@PathVariable final String eventType) {
        return Lists.newArrayList(this.eventStorageService.loadEventsOfType(eventType));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getEventsOfTypeOnPeriod/{eventType}",
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<KomeaEvent> getEventsOfTypeOnPeriod(@PathVariable final String eventType,
            @RequestBody @Valid final DateInterval interval) {
        return Lists.newArrayList(this.eventStorageService.loadEventsOfTypeOnPeriod(eventType, interval));
    }
}
