package org.komea.product.web.rest.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.validation.Valid;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.Severity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/events")
public class EventsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    private IEventPushService eventPushService;
    @Autowired
    private IEventViewerService eventService;

    /**
     * This method find events which have been stored into komea
     *
     * @param _searchEvent looking for event of some entities and of dome type
     * @return an event list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public List<IEvent> findEvents(@Valid
            @RequestBody
            final SearchEventDto _searchEvent) {

        LOGGER.debug("call rest method /events/find to find events {}", _searchEvent);
        final List<IEvent> globalActivity = eventService.getGlobalActivity();
        Collections.sort(globalActivity, new Comparator<IEvent>() {

            @Override
            public int compare(final IEvent o1, final IEvent o2) {

                return o2.getDate().compareTo(o1.getDate());
            }
        });
        final List<IEvent> events = new ArrayList<IEvent>(_searchEvent.getMaxEvents());
        final Iterator<IEvent> iterator = globalActivity.iterator();
        while (iterator.hasNext() && events.size() < _searchEvent.getMaxEvents()) {
            final IEvent event = iterator.next();
            if (eventMatches(event, _searchEvent)) {
                events.add(event);
            }
        }
        return events;
    }

    public IEventPushService getEventPushService() {

        return eventPushService;
    }

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get")
    @ResponseBody
    public List<IEvent> getEvents() {

        LOGGER.debug("call rest method to obtain global activity");
        return eventService.getGlobalActivity();
    }

    /**
     * This method find events
     *
     * @param _severityMin the severity min of the events to return
     * @param _number the number of event to return
     * @return an event list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{severityMin}/{number}")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public List<IEvent> getEvents(@PathVariable(value = "severityMin")
            final String _severityMin, @PathVariable(value = "number")
            final int _number) {

        LOGGER.debug(
                "call rest method /events/get/{severityMin}/{number} to find {} events with severity min = {}",
                _number, _severityMin);
        return findEvents(new SearchEventDto(Severity.valueOf(_severityMin), _number, null,
                Collections.EMPTY_LIST, Collections.EMPTY_LIST));
    }

    public IEventViewerService getEventService() {

        return eventService;
    }

    /**
     * This method push a new event into komea
     *
     * @param _event the event to push
     */
    @RequestMapping(method = RequestMethod.POST, value = "/push")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushEvent(@Valid
            @RequestBody
            final EventSimpleDto _event) {

        LOGGER.debug("call rest method /events/push to push event {}", _event.getMessage());
        // TODO
        eventPushService.sendEventDto(_event);
    }

    public void setEventPushService(final IEventPushService _eventPushService) {

        eventPushService = _eventPushService;
    }

    public void setEventService(final IEventViewerService _eventService) {

        eventService = _eventService;
    }

    private boolean eventMatches(final IEvent event, final SearchEventDto searchEvent) {

        final Severity severity = searchEvent.getSeverityMin();
        final List<String> eventTypeKeys = searchEvent.getEventTypeKeys();
        final List<String> entityKeys = searchEvent.getEntityKeys();
        final EntityType entityType = searchEvent.getEntityType();
        if ((eventTypeKeys.isEmpty() || eventTypeKeys.contains(event.getEventType().getEventKey()))
                && (entityType == null || entityType.equals(event.getEventType().getEntityType()))
                && event.getEventType().getSeverity().compareTo(severity) >= 0) {
            if (entityType == null) {
                return true;
            }
            final String entityKey;
            switch (entityType) {
                case TEAM:
                case DEPARTMENT:
                    entityKey
                            = event.getPersonGroup() == null ? null : event.getPersonGroup()
                            .getPersonGroupKey();
                    break;
                case PERSON:
                    entityKey = event.getPerson() != null ? event.getPerson().getLogin() : "";
                    break;
                case PROJECT:
                    entityKey
                            = event.getProject() == null ? null : event.getProject().getProjectKey();
                    break;
                default:
                    entityKey = null;
            }
            if (entityKeys.isEmpty() || entityKeys.contains(entityKey)) {
                return true;
            }
        }
        return false;
    }
}
