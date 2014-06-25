/**
 *
 */

package org.komea.product.backend.service.olap;



import org.apache.commons.lang.Validate;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.api.IKpiRefreshingScheduler;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.cron.CronRefreshAndStoreKpiValue;
import org.komea.product.database.model.Kpi;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class KpiRefreshingScheduler implements IKpiRefreshingScheduler
{


    private static final Logger  LOGGER = LoggerFactory.getLogger(KpiRefreshingScheduler.class);


    @Autowired
    private ICronRegistryService cronRegistryService;



    public KpiRefreshingScheduler() {


        super();

    }


    /* (non-Javadoc)
     * @see org.komea.product.backend.service.olap.IKpiRefreshingScheduler#deleteCron(org.komea.product.database.model.Kpi)
     */
    @Override
    public void deleteCron(final Kpi _kpiName) {


        LOGGER.debug("Removing cron associated to the kpi {}", _kpiName.getDisplayName());
        cronRegistryService.removeCronTask(getCronName(_kpiName));
    }


    /* (non-Javadoc)
     * @see org.komea.product.backend.service.olap.IKpiRefreshingScheduler#submitKpiRefresh(org.komea.product.database.model.Kpi, org.komea.eventory.api.cache.BackupDelay)
     */
    @Override
    public void submitKpiRefresh(final Kpi _kpi, final BackupDelay _backupDelay) {


        Validate.notNull(_kpi);
        Validate.notNull(_backupDelay);
        LOGGER.debug("Adding cron associated to the kpi {}", _kpi.getDisplayName());
        final String cronName = getCronName(_kpi);
        if (cronRegistryService.existCron(cronName)) {
            return;
        }

        final JobDataMap properties = new JobDataMap();
        properties.put("kpi", _kpi);
        properties.put("backupDelay", _backupDelay);
        cronRegistryService.registerCronTask(cronName, "0 0/1 * 1/1 * ? *",
                CronRefreshAndStoreKpiValue.class, properties);


    }


    /* (non-Javadoc)
     * @see org.komea.product.backend.service.olap.IKpiRefreshingScheduler#triggerKpiCron(org.komea.product.database.model.Kpi)
     */
    @Override
    public void triggerKpiCron(final Kpi _kpi) {


        final String cronName = getCronName(_kpi);
        if (!cronRegistryService.existCron(cronName)) {
            LOGGER.error("Could not find cron with name {}", cronName);
            return;
        }
        cronRegistryService.forceNow(cronName);

    }


    private String getCronName(final Kpi _kpiName) {


        return "KPICRON_" + _kpiName.getKey();
    }

}
