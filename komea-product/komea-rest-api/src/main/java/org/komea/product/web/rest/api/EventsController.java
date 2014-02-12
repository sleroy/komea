
package org.komea.product.web.rest.api;


import java.util.List;

import javax.validation.Valid;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.SearchEventDto;
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
    private IEventViewerService eventService;
    @Autowired
    private IEventPushService   eventPushService;
    
    /**
     * This method find events which have been stored into komea
     * 
     * @param _searchEvent
     *            looking for event of some entities and of dome type
     * @return an event list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public List<IEvent> findEvents(@Valid @RequestBody final SearchEventDto _searchEvent) {
    
        LOGGER.debug("call rest method /events/find to find event {}", _searchEvent.getEntityKeys());
        return eventService.findEvents(_searchEvent);
    }
    
    public IEventPushService getEventPushService() {
    
        return eventPushService;
    }
    
    /**
     * This method find events
     * 
     * @param _severityMin
     *            the severity min of the events to return
     * @param _number
     *            the number of event to return
     * @return an event list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{severityMin}/{number}")
    @ResponseBody
    public List<IEvent> getEvents(@PathVariable(value = "severityMin") final String _severityMin,
            @PathVariable(value = "number") final int _number) {
    
        LOGGER.debug("call rest method /events/get/{severityMin}/{number} to find {} events with severity min = {}", _number, _severityMin);
        return eventService.getEvents(_severityMin, _number);
    }
    
    public IEventViewerService getEventService() {
    
        return eventService;
    }
    
    /**
     * This method push a new event into komea
     * 
     * @param _event
     *            the event to push
     */
    @RequestMapping(method = RequestMethod.POST, value = "/push")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushEvent(@Valid @RequestBody final EventSimpleDto _event) {
    
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
}
