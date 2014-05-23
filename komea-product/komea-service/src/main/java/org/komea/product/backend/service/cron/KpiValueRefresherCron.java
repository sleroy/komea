/**
 * 
 */

package org.komea.product.backend.service.cron;



import java.util.List;

import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * This cron refresh current values of a kpi.
 * 
 * @author sleroy
 */
public class KpiValueRefresherCron implements Job
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-refresh-current-values");
    
    
    @Autowired
    private IKPIService         kpiService;
    
    
    @Autowired
    private IStatisticsAPI      statisticsAPI;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        final List<Kpi> selectAll = kpiService.selectAll();
        LOGGER.info("-- refreshing current values of kpis --");
        int i = 0;
        for (final Kpi kpi : selectAll) {
            LOGGER.info("[{}/{}] obtaining value for the kpi {}", i, selectAll.size(), kpi.getKey());
            final KpiResult evaluateTheCurrentKpiValue =
                    statisticsAPI.evaluateTheCurrentKpiValues(kpi.getId());
            LOGGER.debug("[{}/{}] {} returned {}", i, selectAll.size(), kpi.getKey(),
                    evaluateTheCurrentKpiValue);
            i++;
        }
        LOGGER.info("-- refreshed finished --");
        
    }
    
    
    /**
     * Returns the value of the field kpiService.
     * 
     * @return the kpiService
     */
    public IKPIService getKpiService() {
    
    
        return kpiService;
    }
    
    
    /**
     * Returns the value of the field statisticsAPI.
     * 
     * @return the statisticsAPI
     */
    public IStatisticsAPI getStatisticsAPI() {
    
    
        return statisticsAPI;
    }
    
    
    /**
     * Sets the field kpiService with the value of _kpiService.
     * 
     * @param _kpiService
     *            the kpiService to set
     */
    public void setKpiService(final IKPIService _kpiService) {
    
    
        kpiService = _kpiService;
    }
    
    
    /**
     * Sets the field statisticsAPI with the value of _statisticsAPI.
     * 
     * @param _statisticsAPI
     *            the statisticsAPI to set
     */
    public void setStatisticsAPI(final IStatisticsAPI _statisticsAPI) {
    
    
        statisticsAPI = _statisticsAPI;
    }
}
