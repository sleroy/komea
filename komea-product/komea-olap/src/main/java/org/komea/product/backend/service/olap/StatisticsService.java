/**
 *
 */
package org.komea.product.backend.service.olap;

import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.komea.product.backend.api.IKpiMathService;
import org.komea.product.backend.api.IKpiValueService;
import org.komea.product.backend.criterias.FindKpiPerId;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.TimeSerie;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.BackupDelay;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.database.utils.MeasureUtils;
import org.komea.product.model.timeserie.EntityIdValue;
import org.komea.product.model.timeserie.GroupFormula;
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

    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-statistics");

    @Autowired
    private KpiDao kpiDao;

    @Autowired
    private IKpiMathService kpiMathService;

    @Autowired
    private IKpiValueService kpiValueService;

    @Autowired
    private MeasureDao measureDao;

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiValueService#
     * backupKpiValuesIntoHistory()
     */
    @Override
    public void backupKpiValuesIntoHistory(final BackupDelay _backupDelay) {

        LOGGER.debug("Backup all kpis into the history...");
        for (final Kpi kpi : kpiDao.selectByCriteria(new KpiCriteria())) {
            if (_backupDelay.isAppliedTo(kpi)) {
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
    public TimeSerie buildPeriodTimeSeries(final PeriodTimeSerieOptions _timeSerieOptions, final EntityKey _entityKey) {

        Validate.notNull(_timeSerieOptions);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        Validate.isTrue(_timeSerieOptions.isValid());
        LOGGER.debug("buildPeriodTimeSeries : {}", _timeSerieOptions, _entityKey);
        final PeriodTimeSerieOptions serieOptions = generateFormulaID(_timeSerieOptions);
        return new TimeSerieImpl(measureDao.buildPeriodTimeSeries(serieOptions, _entityKey), _entityKey);
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
    public TimeSerie buildTimeSeries(final TimeSerieOptions _timeSerieOptions, final EntityKey _entityKey) {

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
        return valueOrZero(measureDao.evaluateKpiValue(generateFormulaID(_options), _entityKey));
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
    public Double evaluateKpiValueOnPeriod(final PeriodTimeSerieOptions _options, final EntityKey _entityKey) {

        Validate.notNull(_options);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        Validate.isTrue(_options.isValid());

        final Double evaluateKpiValueOnPeriod = measureDao.evaluateKpiValueOnPeriod(generateFormulaID(_options), _entityKey);
        LOGGER.debug("evaluateKpiValueOnPeriod : {} ekey {}, return {}", _options, _entityKey, evaluateKpiValueOnPeriod);
        return valueOrZero(evaluateKpiValueOnPeriod);
    }

    @Cacheable("evaluateKpiValues")
    @Override
    public KpiResult evaluateKpiValues(final TimeSerieOptions _options) {

        Validate.notNull(_options);
        Validate.isTrue(_options.isValid());
        LOGGER.debug("evaluateKpiValues : {}", _options);
        final Kpi findKpiPerId = new FindKpiPerId(_options.getKpiID(), kpiDao).find();
        return new KpiResult().fill(measureDao.evaluateKpiValues(generateFormulaID(_options)), findKpiPerId.getEntityType());
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
        final List<EntityIdValue> evaluateKpiValuesOnPeriod = measureDao.evaluateKpiValuesOnPeriod(periodTimeSerieOptions);
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
        return valueOrZero(evaluateTheCurrentKpiValues(_kpiKeys.getKpiID()).getDoubleValue(_kpiKeys.getEntityKey()));
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#getTheCurrentKpiValues
     * (java.lang.String)
     */
    @Override
    public KpiResult evaluateTheCurrentKpiValues(final Integer _kpiName) {

        LOGGER.debug("evaluateTheCurrentKpiValues : {}", _kpiName);
        return kpiValueService.getRealTimeValue(_kpiName);
    }

    /**
     * Generates the unique id for the formula of a kPI.
     *
     * @param kpiID the kpi
     */
    public int generateKpiFormulaID(final Integer kpiID) {

        final Kpi find = new FindKpiPerId(kpiID, kpiDao).find();
        return find.getEsperRequest().hashCode();
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
    public KpiResult getKpiValuesAverageOnPeriod(final Integer _kpiName, final DateTime _previousTime) {

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
        return valueOrZero(evaluateKpiValueOnPeriod(periodTimeSerieOptions, _key.getEntityKey()));
    }

    public void setKpiDao(final KpiDao _kpiDao) {

        kpiDao = _kpiDao;
    }

    public void storeActualValueInHistory(final HistoryKey _historyKey) throws KPINotFoundException {

        Validate.notNull(_historyKey);
        LOGGER.debug("storeActualValueInHistory : {}", _historyKey);
        final Kpi findKPI = kpiDao.selectByPrimaryKey(_historyKey.getKpiID());
        final KpiResult queryResult = kpiValueService.getRealTimeValue(findKPI.getKey());// FIXME:/Performance
        // problem
        // Store all data
        final DateTime actualTime = new DateTime();
        for (final Entry<EntityKey, Number> kpiLineValue : queryResult.getMap().entrySet()) {
            Validate.isTrue(kpiLineValue.getKey().isEntityReferenceKey());
            final HistoryKey hKey = HistoryKey.of(findKPI, kpiLineValue.getKey());
            storeValueInHistory(hKey, kpiLineValue.getValue().doubleValue(), actualTime);

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
    public void storeValueInHistory(final HistoryKey _historyKey, final Double _value, final DateTime _actualTime) {

        LOGGER.debug("storeValueInHistory : {}", _historyKey, _value, _actualTime);
        Validate.isTrue(_historyKey.hasEntityReference());

        final int idFromKpiFormula = generateKpiFormulaID(_historyKey.getKpiID());
        final Measure measure = Measure.initializeMeasure(String.valueOf(idFromKpiFormula), _historyKey.getEntityKey().getId());
        measure.setValue(_value);
        MeasureUtils.setMeasureDateTime(measure, _actualTime);
        measureDao.insert(measure);

    }

    private <T extends TimeSerieOptions> T generateFormulaID(final T _timeSerieOptions) {

        _timeSerieOptions.setUniqueID(generateKpiFormulaID(_timeSerieOptions.getKpiID()));
        return _timeSerieOptions; // FIXME :: Should produce a clone.
    }

    private Double valueOrZero(final Double _value) {

        return _value == null ? 0d : _value;
    }
}
