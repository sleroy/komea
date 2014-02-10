
package org.komea.product.backend.service;


import java.util.List;

import org.komea.product.database.dao.EventTypeDao;
import org.komea.product.database.dto.EventDto;
import org.komea.product.database.dto.MeasureDTODto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;

public interface IEventTypeService {
    
    public EventTypeDao getEventTypeDAO();
    
    /**
     * Registers an event
     * 
     * @param _provider
     *            the provider
     * @param _eventType
     *            the event type.
     */
    public void registerEvent(Provider _provider, EventType _eventType);
    
    /**
     * This method return a n event which has a severity min > _severityMin
     * 
     * @param _severityMin
     *            all events to return must have severiyy > _severityMin
     * @param _number
     *            the number of events to return
     * @return the event list
     */
    public List<EventDto> getEvents(String _severityMin, int _number);
    
    /**
     * This method find event list which respect some criterias
     * 
     * @param _searchEvent
     *            the event criterias as as severityMin, event key or event types
     * @return the event list
     */
    public List<EventDto> findEvents(MeasureDTODto _searchEvent);
    
    /**
     * This method push a new event
     * 
     * @param _event
     *            the new event
     */
    public void pushEvent(EventDto _event);
    
}
