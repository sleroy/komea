
package org.komea.product.backend.service.esper;



import java.util.List;

import org.komea.product.service.dto.AlertTypeStatistic;



public interface IAlertStatisticsService
{
    
    
    public List<AlertTypeStatistic> getReceivedAlertTypesIn24LastHours();
    
    
    /**
     * Returns the number of received alerts in the 24 last hours.
     * 
     * @return
     */
    long getReceivedAlertsIn24LastHours();
}
