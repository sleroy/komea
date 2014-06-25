/**
 *
 */

package org.komea.product.backend.service.olap;



import java.util.Map.Entry;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IMeasureStorageService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.service.dto.EntityKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This class provides the necessary mechanism to update/create an entry into the measure table to store the current value of a kpi. Every
 * given time, a cron triggers this method to store the current value into the table;
 *
 * @author sleroy
 */
@Service
@Transactional
public class MeasureStorageService implements IMeasureStorageService
{


    private static final Logger LOGGER = LoggerFactory.getLogger(MeasureStorageService.class);
    @Autowired
    private IEventEngineService engineService;

    @Autowired
    private IQueryService       kpiQueryService;
    @Autowired
    private IKPIService         kpiService;


    @Autowired
    private MeasureDao          measureDao;



    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.olap.IMeasureStorageService#storeActualValueInHistory(java.lang.Integer,
     * org.komea.eventory.api.cache.BackupDelay)
     */

    @Override
    public void storeActualValueInHistory(final Integer _kpiID, final BackupDelay _backupDelay)
            throws KPINotFoundException {


        Validate.notNull(_kpiID);
        LOGGER.debug("storeActualValueInHistory : {}", _kpiID);
        final Kpi findKPI = kpiService.selectByPrimaryKeyOrFail(_kpiID);
        final KpiResult queryResult = kpiQueryService.evaluateRealTimeValues(findKPI.getKey());// FIXME:/Performance
        Validate.notNull(queryResult);

        LOGGER.info("Storing all values[{}] of the kpi -> {} into the database.",
                queryResult.size(), findKPI.getKey());
        for (final Entry<EntityKey, Number> kpiLineValue : queryResult.getMap().entrySet()) {
            if (kpiLineValue.getValue() == null) {
                LOGGER.warn("Entity {} has not value for the kpi -> {}", findKPI.getDisplayName());
                continue;
            }
            Validate.notNull(kpiLineValue.getKey());
            Validate.isTrue(kpiLineValue.getKey().isEntityReferenceKey());

            HistoryKey.of(findKPI, kpiLineValue.getKey());
            Measure measure = new Measure();
            measure.setIdKpi(FormulaID.of(findKPI).getId());
            new ComputeDateForMeasureRefresh(_backupDelay, measure, "")
            .initializeMeasureWithDate(new DateTime());
            measure.setEntityID(kpiLineValue.getKey().getId());
            // Find or replace
            final MeasureCriteria measureCriteria = new MeasureCriteria();
            measureCriteria.createCriteria().andYearEqualTo(measure.getYear())
            .andMonthEqualTo(measure.getMonth()).andWeekEqualTo(measure.getWeek())
            .andDayEqualTo(measure.getDay()).andHourEqualTo(measure.getHour())
            .andEntityIDEqualTo(measure.getEntityID()).andIdKpiEqualTo(measure.getIdKpi());

            final Measure oldMeasure =
                    CollectionUtil.singleOrNull(measureDao.selectByCriteria(measureCriteria));
            if (oldMeasure != null) {
                measure = oldMeasure;
            }

            // Simply refresh the value.
            measure.setDateTime(new DateTime());
            measure.setValue(kpiLineValue.getValue().doubleValue());
            if (oldMeasure == null) {
                measureDao.insert(measure);
            } else {
                measureDao.updateByPrimaryKey(measure);
            }

        }
    }

}
