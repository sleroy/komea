/**
 *
 */

package org.komea.product.backend.service.olap;



import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiMathService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.TimeSerie;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.ObjectValidation;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.KpiGoal;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.model.timeserie.EntityIdValue;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.service.dto.EntityKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */
@Service
@Transactional
public class StatisticsService implements IStatisticsAPI
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("statistics-service");
    
    @Autowired
    private IEventEngineService engineService;
    
    @Autowired
    private IKpiMathService     kpiMathService;
    
    @Autowired
    private IQueryService       kpiQueryService;
    
    @Autowired
    private IKPIService         kpiService;
    
    @Autowired
    private MeasureDao          measureDao;
    
    
    
    @Override
    public void backupKpiValuesIntoHistory(final BackupDelay _backupDelay) {
    
    
        LOGGER.debug("Backup all kpis into the history...");
        for (final Kpi kpi : kpiService.selectByCriteriaWithBLOBs(new KpiCriteria())) {
            final FormulaID of = FormulaID.of(kpi);
            final IQuery query = engineService.getQuery(of);
            if (query == null) {
                LOGGER.error(
                        "Kpi implements a formula thought this query has not been created and registered in the query service : formula {}\n\t Queries present {}",
                        kpi.getKpiKey(), of, Arrays.toString(engineService.getQueryNames()));
                continue;
            }
            
            if (_backupDelay.equals(query.getBackupDelay())) {
                LOGGER.debug("Kpi {} is backuping...", kpi.getKey());
                storeActualValueInHistory(kpi.getId(), _backupDelay);
            }
            LOGGER.debug("Kpi {} backup finished", kpi.getKey());
            
        }
        LOGGER.debug("Backup finished for all kpis");
        
    }
    
    
    @Cacheable("buildGlobalPeriodTimeSeries")
    @Override
    public TimeSerie buildGlobalPeriodTimeSeries(final PeriodTimeSerieOptions _timeSerieOptions) {
    
    
        Validate.notNull(_timeSerieOptions);
        new ObjectValidation().validateObject(_timeSerieOptions);
        LOGGER.debug("buildGlobalPeriodTimeSeries : {}", _timeSerieOptions);
        
        final PeriodTimeSerieOptions options = generateFormulaID(_timeSerieOptions);
        return new TimeSerieImpl(measureDao.buildGlobalPeriodTimeSeries(options));
    }
    
    
    @Cacheable("buildPeriodTimeSeries")
    @Override
    public TimeSerie buildPeriodTimeSeries(
            final PeriodTimeSerieOptions _timeSerieOptions,
            final EntityKey _entityKey) {
    
    
        Validate.notNull(_timeSerieOptions);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        new ObjectValidation().validateObject(_timeSerieOptions);
        LOGGER.debug("buildPeriodTimeSeries : {}", _timeSerieOptions, _entityKey);
        final PeriodTimeSerieOptions serieOptions = generateFormulaID(_timeSerieOptions);
        return new TimeSerieImpl(measureDao.buildPeriodTimeSeries(serieOptions, _entityKey),
                _entityKey);
    }
    
    
    @Cacheable("buildTimeSeries")
    @Override
    public TimeSerie buildTimeSeries(
            final TimeSerieOptions _timeSerieOptions,
            final EntityKey _entityKey) {
    
    
        Validate.notNull(_timeSerieOptions);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        new ObjectValidation().validateObject(_timeSerieOptions);
        LOGGER.debug("buildTimeSeries : {}", _timeSerieOptions, _entityKey);
        final TimeSerieOptions options = generateFormulaID(_timeSerieOptions);
        return new TimeSerieImpl(measureDao.buildTimeSeries(options, _entityKey), _entityKey);
    }
    
    
    @Override
    public int deleteMesuresOfEntity(final IEntity _entity) {
    
    
        final List<Kpi> kpis = kpiService.getAllKpisOfEntityType(_entity.entityType());
        final List<String> formulaIds = Lists.newArrayList();
        for (final Kpi kpi : kpis) {
            formulaIds.add(FormulaID.of(kpi).getId());
        }
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.createCriteria().andEntityIDEqualTo(_entity.getId()).andIdKpiIn(formulaIds);
        return measureDao.deleteByCriteria(measureCriteria);
    }
    
    
    @Override
    public Double evaluateKpiGoalValue(final KpiGoal _kpiGoal) {
    
    
        final Kpi kpiPerId = kpiService.selectByPrimaryKeyOrFail(_kpiGoal.getIdKpi());
        final TimeScale timeScale = TimeScale.valueOf(_kpiGoal.getFrequency());
        final KpiResult kpiLastValue = evaluateKpiLastValue(kpiPerId, timeScale);
        if (_kpiGoal.isAssociatedToAnEntity()) {
            return kpiLastValue.getDoubleValue(EntityKey.of(kpiPerId.getEntityType(),
                    _kpiGoal.getEntityID()));
        } else {
            return kpiLastValue.computeUniqueValue(kpiPerId.getGroupFormula());
        }
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#evaluateKpiLastValue(org.komea.product.database.model.Kpi,
     * org.komea.product.model.timeserie.TimeScale)
     */
    @Override
    public KpiResult evaluateKpiLastValue(final Kpi _kpi, final TimeScale _timeScale) {
    
    
        final Kpi kpiPerId = kpiService.selectByPrimaryKeyOrFail(_kpi.getId());
        final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions(kpiPerId);
        timeSerieOptions.fromLastTimeScale(_timeScale);
        timeSerieOptions.untilNow();
        return evaluateKpiValuesOnPeriod(timeSerieOptions);
    }
    
    
    @Cacheable("evaluateKpiValue")
    @Override
    public Double evaluateKpiValue(final TimeSerieOptions _options, final EntityKey _entityKey) {
    
    
        Validate.notNull(_options);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        new ObjectValidation().validateObject(_options);
        LOGGER.debug("evaluateKpiValue : {}", _options, _entityKey);
        return measureDao.evaluateKpiValue(generateFormulaID(_options), _entityKey);
    }
    
    
    @Cacheable("evaluateKpiValueOnPeriod")
    @Override
    public Double evaluateKpiValueOnPeriod(
            final PeriodTimeSerieOptions _options,
            final EntityKey _entityKey) {
    
    
        Validate.notNull(_options);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        new ObjectValidation().validateObject(_options);
        
        final PeriodTimeSerieOptions generateFormulaID = generateFormulaID(_options);
        final Double evaluateKpiValueOnPeriod =
                measureDao.evaluateKpiValueOnPeriod(generateFormulaID, _entityKey);
        LOGGER.debug("evaluateKpiValueOnPeriod : {} ekey {}, return {}", _options, _entityKey,
                evaluateKpiValueOnPeriod);
        return evaluateKpiValueOnPeriod;
    }
    
    
    @Cacheable("evaluateKpiValues")
    @Override
    public KpiResult evaluateKpiValues(final TimeSerieOptions _options) {
    
    
        Validate.notNull(_options);
        new ObjectValidation().validateObject(_options);
        LOGGER.debug("evaluateKpiValues : {}", _options);
        final Kpi findKpiPerId = kpiService.selectByPrimaryKeyOrFail(_options.getKpiID());
        return new KpiResult().fill(measureDao.evaluateKpiValues(generateFormulaID(_options)),
                findKpiPerId.getEntityType());
    }
    
    
    @Cacheable("evaluateKpiValuesOnPeriod")
    @Override
    public KpiResult evaluateKpiValuesOnPeriod(final PeriodTimeSerieOptions _options) {
    
    
        Validate.notNull(_options);
        new ObjectValidation().validateObject(_options);
        LOGGER.debug("evaluateKpiValuesOnPeriod : {}", _options);
        final Kpi findKpiPerId = kpiService.selectByPrimaryKeyOrFail(_options.getKpiID());
        final PeriodTimeSerieOptions periodTimeSerieOptions = generateFormulaID(_options);
        final List<EntityIdValue> evaluateKpiValuesOnPeriod =
                measureDao.evaluateKpiValuesOnPeriod(periodTimeSerieOptions);
        return new KpiResult().fill(evaluateKpiValuesOnPeriod, findKpiPerId.getEntityType());
        
    }
    
    
    public Double getLastButOneStoredValueInHistory(final HistoryKey _key) {
    
    
        Validate.notNull(_key.hasEntityReference());
        final PeriodTimeSerieOptions periodTimeSerieOptions = new PeriodTimeSerieOptions();
        periodTimeSerieOptions.setTimeScale(TimeScale.PER_DAY);
        periodTimeSerieOptions.toLastTimeScale(TimeScale.PER_DAY);
        periodTimeSerieOptions.fromNbLastTimeScale(TimeScale.PER_DAY, 2);
        periodTimeSerieOptions.setGroupFormula(GroupFormula.AVG_VALUE);
        periodTimeSerieOptions.setKpiID(_key.getKpiID());
        return evaluateKpiValueOnPeriod(periodTimeSerieOptions, _key.getEntityKey());
    }
    
    
    @Override
    public Double getRemainingEffort(final KpiGoal _kpiGoal) {
    
    
        final Double goalValue = _kpiGoal.getValue();
        final Double realValue = evaluateKpiGoalValue(_kpiGoal);
        final Double remainEffort = 100.0d * ((realValue - goalValue) / (goalValue + 1.0d));
        return remainEffort;
        
    }
    
    
    @Override
    public Period getRemainingTime(final KpiGoal _kpiGoal) {
    
    
        Validate.notNull(_kpiGoal);
        if (_kpiGoal.getUntilDate() == null) {
            return new Period();
        }
        
        return new Period(new DateTime(), new DateTime(_kpiGoal.getUntilDate()));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#returnsTheLastValue(org.komea.product.backend.service.history.HistoryKey)
     */
    @Override
    public Double returnsTheLastValue(final HistoryKey _lastKey) {
    
    
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.setOrderByClause("date DESC");
        measureCriteria.createCriteria().andIdKpiEqualTo(generateKpiFormulaID(_lastKey.getKpiID()))
                .andEntityIDEqualTo(_lastKey.getEntityKey().getId());
        final Measure measure =
                CollectionUtil.firstElement(measureDao.selectByCriteria(measureCriteria));
        return measure == null ? Double.NaN : measure.getValue();
    }
    
    
    /**
     * Stores actual value in history.
     *
     * @param _kpiID
     *            the kpi ID
     * @param _backupDelay
     *            the backup delay.
     * @throws KPINotFoundException
     */
    public void storeActualValueInHistory(final Integer _kpiID, final BackupDelay _backupDelay)
            throws KPINotFoundException {
    
    
        Validate.notNull(_kpiID);
        LOGGER.debug("storeActualValueInHistory : {}", _kpiID);
        final Kpi findKPI = kpiService.selectByPrimaryKeyOrFail(_kpiID);
        final KpiResult queryResult = kpiQueryService.evaluateRealTimeValues(findKPI.getKey());// FIXME:/Performance
        Validate.notNull(queryResult);
        
        LOGGER.info("Storing all values[{}] of the kpi {} into the database.", queryResult.size(),
                findKPI.getKey());
        for (final Entry<EntityKey, Number> kpiLineValue : queryResult.getMap().entrySet()) {
            if (kpiLineValue.getValue() == null) {
                LOGGER.warn("Entity {} has not value for the kpi {}", findKPI);
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
    
    
    @Override
    public void storeValueInHistory(final HistoryKey _kpiKey, final Double _value) {
    
    
        storeValueInHistory(_kpiKey, _value, new DateTime());
        
    }
    
    
    @Override
    public void storeValueInHistory(
            final HistoryKey _historyKey,
            final Double _value,
            final DateTime _actualTime) {
    
    
        LOGGER.debug("storeValueInHistory : {}", _historyKey, _value, _actualTime);
        Validate.isTrue(_historyKey.hasEntityReference());
        Validate.notNull(_value);
        Validate.notNull(_actualTime);
        
        final Measure measure = newMeasure(_historyKey, _value);
        measure.setDateTime(_actualTime);
        measureDao.insert(measure);
        
    }
    
    
    private <T extends TimeSerieOptions> T generateFormulaID(final T _timeSerieOptions) {
    
    
        _timeSerieOptions.setUniqueID(generateKpiFormulaID(_timeSerieOptions.getKpiID()));
        return _timeSerieOptions; // FIXME :: Should produce a clone.
    }
    
    
    private String generateKpiFormulaID(final Integer _kpiID) {
    
    
        final Kpi find = kpiService.selectByPrimaryKeyOrFail(_kpiID);
        final String idFromKpiFormula = FormulaID.of(find).getId();
        return idFromKpiFormula;
    }
    
    
    private Measure newMeasure(final HistoryKey _historyKey, final Double _value) {
    
    
        final String idFromKpiFormula = generateKpiFormulaID(_historyKey.getKpiID());
        final Measure measure =
                Measure.initializeMeasure(idFromKpiFormula, _historyKey.getEntityKey().getId());
        measure.setValue(_value);
        return measure;
    }
    
    
}
