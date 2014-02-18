
package org.komea.product.backend.service.esper;



import java.util.List;

import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.EventTypeStatistic;



public interface IEventStatisticsService
{
    
    
    /**
     * Returns the list of all measures for the number of alerts.
     * 
     * @return the list of all measures.
     */
    public List<Measure> getAllMeasures();
    
    
    /**
     * Returns the number oof received alerts in 24 last hours.
     * 
     * @return the number of alerts.
     */
    public List<EventTypeStatistic> getReceivedAlertTypesIn24LastHours();
    
    
    long getNumberOfAlerts(Severity _criticity);
    
    
    /**
     * Returns the number of received alerts in the 24 last hours.
     * 
     * @return
     */
    long getReceivedAlertsIn24LastHours();
}
