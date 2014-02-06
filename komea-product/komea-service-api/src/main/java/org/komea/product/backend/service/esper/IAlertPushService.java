/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.product.database.alert.IAlert;



/**
 * This interface defines the methods to push alerts.
 * 
 * @author sleroy
 */
public interface IAlertPushService
{
    
    
    /**
     * Send the event : alerts
     * 
     * @param _alert
     *            the alert.
     */
    void sendEvent(IAlert _alert);
    
    
    /**
     * Send an event without validation.
     * 
     * @param _alert
     *            the alert.
     */
    void sendEventWithoutValidation(IAlert _alert);
}
