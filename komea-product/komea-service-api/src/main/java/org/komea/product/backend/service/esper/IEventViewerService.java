
package org.komea.product.backend.service.esper;



import java.util.List;

import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.SearchEventDto;



public interface IEventViewerService
{
    
    
    /**
     * This method find event list which respect some criterias
     * 
     * @param _searchEvent
     *            the event criterias as as severityMin, event key or event types
     * @return the event list
     */
    public List<IEvent> findEvents(SearchEventDto _searchEvent);
    
    
    /**
     * This method return a n event which has a severity min > _severityMin
     * 
     * @param _severityMin
     *            all events to return must have severiyy > _severityMin
     * @param _number
     *            the number of events to return
     * @return the event list
     */
    public List<IEvent> getEvents(String _severityMin, int _number);
    
    
    /**
     * Return an instant view from an EPL Statement
     * 
     * @param _eplStatement
     *            the esper statement
     * @return an instant view.
     * @since 28 juin 2013
     */
    List<IEvent> getInstantView(String _eplStatement);
}
