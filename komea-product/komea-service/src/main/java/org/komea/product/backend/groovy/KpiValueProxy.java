/**
 * 
 */

package org.komea.product.backend.groovy;



import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.model.Kpi;
import org.thymeleaf.util.Validate;



/**
 * @author sleroy
 */
public class KpiValueProxy
{
    
    
    private final Kpi            kpi;
    private final IStatisticsAPI statisticsAPI;
    
    
    
    public KpiValueProxy(final Kpi _kpi, final IStatisticsAPI _statisticsAPI) {
    
    
        kpi = _kpi;
        statisticsAPI = _statisticsAPI;
        Validate.notNull(_kpi, "A valid kpi name is expected");
        Validate.notNull(statisticsAPI, "Statistics API cannot be reached");
    }
}
