/**
 *
 */

package org.komea.product.backend.service.cron;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
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
    
    
        kpiService.selectAll();
        LOGGER.info("-- refreshing current values of kpis --");
        LOGGER.info("-- Refreshing monthly kpi values --");
        statisticsAPI.backupKpiValuesIntoHistory(BackupDelay.MONTH);
        LOGGER.info("-- Refreshing weekly kpi values --");
        statisticsAPI.backupKpiValuesIntoHistory(BackupDelay.WEEK);
        LOGGER.info("-- Refreshing daily kpi values --");
        statisticsAPI.backupKpiValuesIntoHistory(BackupDelay.DAY);
        LOGGER.info("-- Refreshing hourly kpi values --");
        statisticsAPI.backupKpiValuesIntoHistory(BackupDelay.HOUR);
        
        
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
