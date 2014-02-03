
package org.komea.product.backend.service.esper;



import java.util.List;

import org.komea.product.service.dto.AlertTypeStatistic;



public interface IAlertStatisticsService
{
    
    
    public List<AlertTypeStatistic> getReceivedAlertTypesIn24Hours();
}
