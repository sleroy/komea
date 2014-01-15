/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.product.database.alert.IAlert;



/**
 * This interface defines the bean that manage the esper engine.
 * 
 * @author sleroy
 */
public interface IAlertService
{
    
    
    /**
     * Tests if a epl query is existing.
     * 
     * @param _metricKey
     *            the metric key.
     * @return the key.
     */
    boolean existEPL(String _metricKey);
    
    
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
