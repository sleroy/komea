/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.io.Serializable;

import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;



/**
 * This interface defines the methods to push alerts.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface IEventPushService
{
    
    
    /**
     * Sends a custom event
     * 
     * @param _customEvent
     *            the custom event.
     */
    void sendCustomEvent(Serializable _customEvent);
    
    
    /**
     * Send the event : alerts
     * 
     * @param _event
     *            the alert.
     */
    void sendEvent(IEvent _event);
    
    
    /**
     * Send the event dto.
     * 
     * @param _dto
     *            the dto.
     */
    void sendEventDto(EventSimpleDto _dto);
    
    
}
