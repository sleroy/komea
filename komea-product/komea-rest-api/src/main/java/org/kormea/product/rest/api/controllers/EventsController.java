
package org.kormea.product.rest.api.controllers;


import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.dto.EventDto;
import org.komea.product.database.dto.SearchEventsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/events")
public class EventsController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);
    
    /**
     * This method push a new event into komea
     * 
     * @param _event
     *            the event to push
     */
    @RequestMapping(method = RequestMethod.POST, value = "/push")
    public void pushEvent(@RequestBody final EventDto _event) {
    
        LOGGER.debug("call rest method /events/push to push event {}", _event.getMessage());
        // TODO
    }
    
    /**
     * This method find events which have been stored into komea
     * 
     * @param _searchEvent
     *            looking for event of some entities and of dome type
     * @return an event list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/find")
    public List<EventDto> findEvents(@RequestBody final SearchEventsDto _searchEvent) {
    
        LOGGER.debug("call rest method /events/find to find event {}", _searchEvent.getEntityKeys());
        // TODO
        return new ArrayList<EventDto>();
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
    public List<EventDto> getEvents(@RequestParam(value = "severityMin") final String _severityMin,
            @RequestParam(value = "number") final int _number) {
    
        LOGGER.debug("call rest method /events/get/{severityMin}/{number} to find {} events with severity min = {}", _number, _severityMin);
        // TODO
        return new ArrayList<EventDto>();
    }
}
