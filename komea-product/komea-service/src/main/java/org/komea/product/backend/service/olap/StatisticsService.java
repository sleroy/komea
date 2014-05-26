/**
 *
 */
package org.komea.product.backend.service.olap;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiMathService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.criterias.FindKpiPerId;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.TimeSerie;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.database.utils.MeasureUtils;
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

/**
 * @author sleroy
 */
@Service
@Transactional
public class StatisticsService implements IStatisticsAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger("statistics-service");

    @Autowired
    private IEventEngineService engineService;

    @Autowired
    private KpiDao kpiDao;

    @Autowired
    private IKpiMathService kpiMathService;

    @Autowired
    private IQueryService kpiQueryService;

    @Autowired
    private MeasureDao measureDao;

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiQueryService#
     * backupKpiValuesIntoHistory()
     */
    @Override
    public void backupKpiValuesIntoHistory(final BackupDelay _backupDelay) {

        LOGGER.debug("Backup all kpis into the history...");
        for (final Kpi kpi : kpiDao.selectByCriteriaWithBLOBs(new KpiCriteria())) {
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
                storeActualValueInHistory(HistoryKey.of(kpi));
            }
            LOGGER.debug("Kpi {} backup finished", kpi.getKey());

        }
        LOGGER.debug("Backup finished for all kpis");

    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#
     * buildGlobalPeriodTimeSeries(org.komea.product.database.dao.timeserie.
     * PeriodTimeSerieOptions)
     */
    @Cacheable("buildGlobalPeriodTimeSeries")
    @Override
    public TimeSerie buildGlobalPeriodTimeSeries(final PeriodTimeSerieOptions _timeSerieOptions) {

        Validate.notNull(_timeSerieOptions);
        Validate.isTrue(_timeSerieOptions.isValid());
        LOGGER.debug("buildGlobalPeriodTimeSeries : {}", _timeSerieOptions);

        final PeriodTimeSerieOptions options = generateFormulaID(_timeSerieOptions);
        return new TimeSerieImpl(measureDao.buildGlobalPeriodTimeSeries(options));
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#buildPeriodTimeSeries
     * (org.komea.product.database.dao.timeserie. PeriodTimeSerieOptions,
     * org.komea.product.service.dto.EntityKey)
     */
    @Cacheable("buildPeriodTimeSeries")
    @Override
    public TimeSerie buildPeriodTimeSeries(
            final PeriodTimeSerieOptions _timeSerieOptions,
            final EntityKey _entityKey) {

        Validate.notNull(_timeSerieOptions);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        Validate.isTrue(_timeSerieOptions.isValid());
        LOGGER.debug("buildPeriodTimeSeries : {}", _timeSerieOptions, _entityKey);
        final PeriodTimeSerieOptions serieOptions = generateFormulaID(_timeSerieOptions);
        return new TimeSerieImpl(measureDao.buildPeriodTimeSeries(serieOptions, _entityKey),
                _entityKey);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#buildTimeSeries(
     * org.komea.product.database.dao.timeserie.TimeSerieOptions,
     * org.komea.product.service.dto.EntityKey)
     */
    @Cacheable("buildTimeSeries")
    @Override
    public TimeSerie buildTimeSeries(
            final TimeSerieOptions _timeSerieOptions,
            final EntityKey _entityKey) {

        Validate.notNull(_timeSerieOptions);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        Validate.isTrue(_timeSerieOptions.isValid());
        LOGGER.debug("buildTimeSeries : {}", _timeSerieOptions, _entityKey);
        final TimeSerieOptions options = generateFormulaID(_timeSerieOptions);
        return new TimeSerieImpl(measureDao.buildTimeSeries(options, _entityKey), _entityKey);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#
     * computeAverageFromMeasures(java.util.List)
     */
    @Override
    public double computeAverageFromMeasures(final List<Measure> _kpiMeasures) {

        LOGGER.debug("computeAverageFromMeasures : {}", _kpiMeasures);
        return kpiMathService.computeAverageFromMeasures(_kpiMeasures);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#computeSumFromMeasures
     * (java.util.List)
     */
    @Override
    public double computeSumFromMeasures(final List<Measure> _kpiMeasures) {

        LOGGER.debug("computeSumFromMeasures : {}", _kpiMeasures);
        return kpiMathService.computeSumFromMeasures(_kpiMeasures);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#getKpiValues(org
     * .komea.product.database.dao.timeserie.TimeSerieOptions,
     * org.komea.product.service.dto.EntityKey)
     */
    @Cacheable("evaluateKpiValue")
    @Override
    public Double evaluateKpiValue(final TimeSerieOptions _options, final EntityKey _entityKey) {

        Validate.notNull(_options);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        Validate.isTrue(_options.isValid());
        LOGGER.debug("evaluateKpiValue : {}", _options, _entityKey);
        return measureDao.evaluateKpiValue(generateFormulaID(_options), _entityKey);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#getKpiOnPeriodValues
     * (org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions ,
     * org.komea.product.service.dto.EntityKey)
     */
    @Cacheable("evaluateKpiValueOnPeriod")
    @Override
    public Double evaluateKpiValueOnPeriod(
            final PeriodTimeSerieOptions _options,
            final EntityKey _entityKey) {

        Validate.notNull(_options);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        Validate.isTrue(_options.isValid());

        final PeriodTimeSerieOptions generateFormulaID = generateFormulaID(_options);
        final Double evaluateKpiValueOnPeriod
                = measureDao.evaluateKpiValueOnPeriod(generateFormulaID, _entityKey);
        LOGGER.debug("evaluateKpiValueOnPeriod : {} ekey {}, return {}", _options, _entityKey,
                evaluateKpiValueOnPeriod);
        return evaluateKpiValueOnPeriod;
    }

    @Cacheable("evaluateKpiValues")
    @Override
    public KpiResult evaluateKpiValues(final TimeSerieOptions _options) {

        Validate.notNull(_options);
        Validate.isTrue(_options.isValid());
        LOGGER.debug("evaluateKpiValues : {}", _options);
        final Kpi findKpiPerId = new FindKpiPerId(_options.getKpiID(), kpiDao).find();
        return new KpiResult().fill(measureDao.evaluateKpiValues(generateFormulaID(_options)),
                findKpiPerId.getEntityType());
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#getKpiOnPeriodValues
     * (org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions )
     */
    @Cacheable("evaluateKpiValuesOnPeriod")
    @Override
    public KpiResult evaluateKpiValuesOnPeriod(final PeriodTimeSerieOptions _options) {

        Validate.notNull(_options);
        Validate.isTrue(_options.isValid());
        LOGGER.debug("evaluateKpiValuesOnPeriod : {}", _options);
        final Kpi findKpiPerId = new FindKpiPerId(_options.getKpiID(), kpiDao).find();
        final PeriodTimeSerieOptions periodTimeSerieOptions = generateFormulaID(_options);
        final List<EntityIdValue> evaluateKpiValuesOnPeriod
                = measureDao.evaluateKpiValuesOnPeriod(periodTimeSerieOptions);
        return new KpiResult().fill(evaluateKpiValuesOnPeriod, findKpiPerId.getEntityType());

    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#getTheCurrentKpiValue
     * (org.komea.product.service.dto.KpiKey)
     */
    @Override
    public Double evaluateTheCurrentKpiValue(final HistoryKey _kpiKeys) {

        Validate.isTrue(_kpiKeys.getEntityKey().isEntityReferenceKey());
        LOGGER.debug("evaluateTheCurrentKpiValue : {}", _kpiKeys);
        return evaluateTheCurrentKpiValues(_kpiKeys.getKpiID()).getDoubleValue(
                _kpiKeys.getEntityKey());
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#getTheCurrentKpiValues
     * (java.lang.String)
     */
    @Override
    public KpiResult evaluateTheCurrentKpiValues(final Integer _kpiID) {

        LOGGER.debug("evaluateTheCurrentKpiValues : {}", _kpiID);
        return kpiQueryService.evaluateRealTimeValues(_kpiID);
    }

    public KpiDao getKpiDao() {

        return kpiDao;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#
     * getKpiValuesAverageOnPeriod(java.lang.String, org.joda.time.DateTime)
     */
    @Cacheable("getKpiValuesAverageOnPeriod")
    @Override
    public KpiResult getKpiValuesAverageOnPeriod(
            final Integer _kpiName,
            final DateTime _previousTime) {

        LOGGER.debug("getKpiValuesAverageOnPeriod : {}", _kpiName, _previousTime);

        final PeriodTimeSerieOptions periodTimeSerieOptions = new PeriodTimeSerieOptions();
        periodTimeSerieOptions.untilNow();
        periodTimeSerieOptions.setFromPeriod(_previousTime);
        periodTimeSerieOptions.pickBestGranularity();
        periodTimeSerieOptions.setGroupFormula(GroupFormula.AVG_VALUE);
        return evaluateKpiValuesOnPeriod(periodTimeSerieOptions);
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#
     * getLastStoredValueInHistory
     * (org.komea.product.backend.service.history.HistoryKey )
     */
    @Cacheable("getLastStoredValueInHistory")
    @Override
    public Double getLastStoredValueInHistory(final HistoryKey _key) {

        Validate.notNull(_key.hasEntityReference());
        LOGGER.debug("getLastStoredValueInHistory : {}", _key);
        final PeriodTimeSerieOptions periodTimeSerieOptions = new PeriodTimeSerieOptions();
        periodTimeSerieOptions.setTimeScale(TimeScale.PER_DAY);
        periodTimeSerieOptions.untilNow();
        periodTimeSerieOptions.fromLastTimeScale(TimeScale.PER_DAY);
        periodTimeSerieOptions.setGroupFormula(GroupFormula.AVG_VALUE);
        periodTimeSerieOptions.setKpiID(_key.getKpiID());
        LOGGER.debug("getLastStoredValueInHistory : period {}", periodTimeSerieOptions);
        return evaluateKpiValueOnPeriod(periodTimeSerieOptions, _key.getEntityKey());
    }

    @Override
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

    public void setKpiDao(final KpiDao _kpiDao) {

        kpiDao = _kpiDao;
    }

    public void storeActualValueInHistory(final HistoryKey _historyKey) throws KPINotFoundException {

        Validate.notNull(_historyKey);
        LOGGER.debug("storeActualValueInHistory : {}", _historyKey);
        final Kpi findKPI = new FindKpiPerId(_historyKey.getKpiID(), kpiDao).find();
        final KpiResult queryResult = kpiQueryService.evaluateRealTimeValues(findKPI.getKey());// FIXME:/Performance
        Validate.notNull(queryResult);
        // problem
        // Store all data
        final DateTime actualTime = new DateTime();
        if (_historyKey.hasEntityReference()) {
            storeASingleEntityValue(_historyKey, findKPI, queryResult, actualTime);

        } else {
            storeAllValuesOfAKpi(findKPI, queryResult);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#storeValueInHistory
     * (org.komea.product.backend.service.history.HistoryKey, java.lang.Double)
     */
    @Override
    public void storeValueInHistory(final HistoryKey _kpiKey, final Double _value) {

        storeValueInHistory(_kpiKey, _value, new DateTime());

    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#storeValueInHistory
     * (org.komea.product.backend.service.history.HistoryKey, java.lang.Double,
     * org.joda.time.DateTime)
     */
    @Override
    public void storeValueInHistory(
            final HistoryKey _historyKey,
            final Double _value,
            final DateTime _actualTime) {

        LOGGER.debug("storeValueInHistory : {}", _historyKey, _value, _actualTime);
        Validate.isTrue(_historyKey.hasEntityReference());
        Validate.notNull(_value);
        Validate.notNull(_actualTime);

        final String idFromKpiFormula = generateKpiFormulaID(_historyKey.getKpiID());
        final Measure measure
                = Measure.initializeMeasure(idFromKpiFormula, _historyKey.getEntityKey().getId());
        measure.setValue(_value);
        MeasureUtils.setMeasureDateTime(measure, _actualTime);
        measureDao.insert(measure);

    }

    private <T extends TimeSerieOptions> T generateFormulaID(final T _timeSerieOptions) {

        _timeSerieOptions.setUniqueID(generateKpiFormulaID(_timeSerieOptions.getKpiID()));
        return _timeSerieOptions; // FIXME :: Should produce a clone.
    }

    private String generateKpiFormulaID(final Integer _kpiID) {

        final Kpi find = new FindKpiPerId(_kpiID, kpiDao).find();
        final String idFromKpiFormula = FormulaID.of(find).getId();
        return idFromKpiFormula;
    }

    private void storeAllValuesOfAKpi(final Kpi findKPI, final KpiResult queryResult) {

        LOGGER.info("Storing all values[{}] of the kpi {} into the database.", queryResult.size(),
                findKPI.getKey());
        for (final Entry<EntityKey, Number> kpiLineValue : queryResult.getMap().entrySet()) {
            if (kpiLineValue.getValue() == null) {
                LOGGER.debug("Entity {} has not value for the kpi {}", findKPI);
                continue;
            }

            Validate.notNull(kpiLineValue.getKey());
            Validate.isTrue(kpiLineValue.getKey().isEntityReferenceKey());
            final HistoryKey hKey = HistoryKey.of(findKPI, kpiLineValue.getKey());
            storeValueInHistory(hKey, kpiLineValue.getValue() == null ? null : kpiLineValue
                    .getValue().doubleValue());

        }
    }

    private void storeASingleEntityValue(
            final HistoryKey _historyKey,
            final Kpi findKPI,
            final KpiResult queryResult,
            final DateTime actualTime) {

        LOGGER.info("Storing values of the kpi {} into the database for the reference {}",
                findKPI.getKey(), _historyKey.getEntityKey());
        Validate.isTrue(_historyKey.getEntityKey().isEntityReferenceKey());
        if (queryResult.hasKey(_historyKey.getEntityKey())) {
            final Double value = queryResult.getDoubleValue(_historyKey.getEntityKey());
            if (value != null) {
                storeValueInHistory(_historyKey, value, actualTime);
            }
        }
    }

}
