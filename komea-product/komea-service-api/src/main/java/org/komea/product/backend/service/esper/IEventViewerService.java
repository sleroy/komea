
package org.komea.product.backend.service.esper;



import java.util.List;

import org.komea.product.database.alert.IEvent;



public interface IEventViewerService
{
    
    
    /**
     * Returns the events provided in a day.
     * 
     * @return the day events.
     */
    List<IEvent> getDayEvents();
    
    
    /**
     * Returns the events provided in a hour.
     * 
     * @return the day events.
     */
    List<IEvent> getHourEvents();
    
    
    /**
     * Return an instant view from an EPL Statement
     * 
     * @param _eplStatement
     *            the esper statement
     * @return an instant view.
     * @since 28 juin 2013
     */
    List<IEvent> getInstantView(String _eplStatement);
    
    
    /**
     * Returns the last 100 events.
     * 
     * @return the last 1000 events.
     */
    List<IEvent> getLastEvents();
}
