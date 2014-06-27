/**
 *
 */

package org.komea.product.backend.service.olap;



import java.util.List;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiMathService;
import org.komea.product.backend.api.IKpiRefreshingScheduler;
import org.komea.product.backend.api.IQueryService;
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
import org.komea.product.database.model.KpiGoal;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
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

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */
@Service
@Transactional
public class StatisticsService implements IStatisticsAPI
{


    private static final Logger     LOGGER = LoggerFactory.getLogger("statistics-service");

    @Autowired
    private IEventEngineService     engineService;

    @Autowired
    private IKpiMathService         kpiMathService;

    @Autowired
    private IQueryService           kpiQueryService;

    @Autowired
    private IKpiRefreshingScheduler kpiRefreshScheduler;

    @Autowired
    private IKPIService             kpiService;

    @Autowired
    private MeasureDao              measureDao;



    @Cacheable("buildGlobalPeriodTimeSeries")
    @Override
    public TimeSerie buildGlobalPeriodTimeSeries(final PeriodTimeSerieOptions _timeSerieOptions) {


        try {
            Validate.notNull(_timeSerieOptions);
            new ObjectValidation().validateObject(_timeSerieOptions);
            LOGGER.debug("buildGlobalPeriodTimeSeries : {}", _timeSerieOptions);

            final PeriodTimeSerieOptions options = generateFormulaID(_timeSerieOptions);
            return new TimeSerieImpl(measureDao.buildGlobalPeriodTimeSeries(options));
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new TimeSerieImpl();

    }


    @Cacheable("buildPeriodTimeSeries")
    @Override
    public TimeSerie buildPeriodTimeSeries(
            final PeriodTimeSerieOptions _timeSerieOptions,
            final EntityKey _entityKey) {


        try {
            Validate.notNull(_timeSerieOptions);
            Validate.notNull(_entityKey);
            Validate.isTrue(_entityKey.isEntityReferenceKey());
            new ObjectValidation().validateObject(_timeSerieOptions);
            LOGGER.debug("buildPeriodTimeSeries : {}", _timeSerieOptions, _entityKey);
            final PeriodTimeSerieOptions serieOptions = generateFormulaID(_timeSerieOptions);
            return new TimeSerieImpl(measureDao.buildPeriodTimeSeries(serieOptions, _entityKey),
                    _entityKey);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new TimeSerieImpl();

    }


    @Cacheable("buildTimeSeries")
    @Override
    public TimeSerie buildTimeSeries(
            final TimeSerieOptions _timeSerieOptions,
            final EntityKey _entityKey) {


        try {
            Validate.notNull(_timeSerieOptions);
            Validate.notNull(_entityKey);
            Validate.isTrue(_entityKey.isEntityReferenceKey());
            new ObjectValidation().validateObject(_timeSerieOptions);
            LOGGER.debug("buildTimeSeries : {}", _timeSerieOptions, _entityKey);
            final TimeSerieOptions options = generateFormulaID(_timeSerieOptions);
            return new TimeSerieImpl(measureDao.buildTimeSeries(options, _entityKey), _entityKey);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new TimeSerieImpl();

    }


    @Override
    public int deleteMesuresOfEntity(final IEntity _entity) {


        try {
            final List<Kpi> kpis = kpiService.getAllKpisOfEntityType(_entity.entityType());
            final List<String> formulaIds = Lists.newArrayList();
            for (final Kpi kpi : kpis) {
                formulaIds.add(FormulaID.of(kpi).getId());
            }
            final MeasureCriteria measureCriteria = new MeasureCriteria();
            measureCriteria.createCriteria().andEntityIDEqualTo(_entity.getId())
            .andIdKpiIn(formulaIds);
            return measureDao.deleteByCriteria(measureCriteria);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return -1;

    }


    @Override
    public Double evaluateKpiGoalValue(final KpiGoal _kpiGoal) {


        try {
            final Kpi kpiPerId = kpiService.selectByPrimaryKeyOrFail(_kpiGoal.getIdKpi());
            final TimeScale timeScale = TimeScale.valueOf(_kpiGoal.getFrequency());
            final KpiResult kpiLastValue = evaluateKpiLastValue(kpiPerId, timeScale);
            if (_kpiGoal.isAssociatedToAnEntity()) {
                return kpiLastValue.getDoubleValue(EntityKey.of(kpiPerId.getEntityType(),
                        _kpiGoal.getEntityID()));
            } else {
                return kpiLastValue.computeUniqueValue(kpiPerId.getGroupFormula());
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;


    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#evaluateKpiLastValue(org.komea.product.database.model.Kpi,
     * org.komea.product.model.timeserie.TimeScale)
     */
    @Override
    public KpiResult evaluateKpiLastValue(final Kpi _kpi, final TimeScale _timeScale) {


        try {
            final Kpi kpiPerId = kpiService.selectByPrimaryKeyOrFail(_kpi.getId());
            final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions(kpiPerId);
            timeSerieOptions.fromLastTimeScale(_timeScale);
            timeSerieOptions.untilNow();
            return evaluateKpiValuesOnPeriod(timeSerieOptions);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new KpiResult();

    }


    @Cacheable("evaluateKpiValue")
    @Override
    public Double evaluateKpiValue(final TimeSerieOptions _options, final EntityKey _entityKey) {


        try {
            Validate.notNull(_options);
            Validate.notNull(_entityKey);
            Validate.isTrue(_entityKey.isEntityReferenceKey());
            new ObjectValidation().validateObject(_options);
            LOGGER.debug("evaluateKpiValue : {}", _options, _entityKey);
            return measureDao.evaluateKpiValue(generateFormulaID(_options), _entityKey);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;

    }


    @Cacheable("evaluateKpiValueOnPeriod")
    @Override
    public Double evaluateKpiValueOnPeriod(
            final PeriodTimeSerieOptions _options,
            final EntityKey _entityKey) {


        try {
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
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;

    }


    @Cacheable("evaluateKpiValues")
    @Override
    public KpiResult evaluateKpiValues(final TimeSerieOptions _options) {


        try {
            Validate.notNull(_options);
            new ObjectValidation().validateObject(_options);
            LOGGER.debug("evaluateKpiValues : {}", _options);
            final Kpi findKpiPerId = kpiService.selectByPrimaryKeyOrFail(_options.getKpiID());
            return new KpiResult().fill(measureDao.evaluateKpiValues(generateFormulaID(_options)),
                    findKpiPerId.getEntityType());
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new KpiResult();

    }


    @Cacheable("evaluateKpiValuesOnPeriod")
    @Override
    public KpiResult evaluateKpiValuesOnPeriod(final PeriodTimeSerieOptions _options) {


        try {
            Validate.notNull(_options);
            new ObjectValidation().validateObject(_options);
            LOGGER.debug("evaluateKpiValuesOnPeriod : {}", _options);
            final Kpi findKpiPerId = kpiService.selectByPrimaryKeyOrFail(_options.getKpiID());
            final PeriodTimeSerieOptions periodTimeSerieOptions = generateFormulaID(_options);
            final List<EntityIdValue> evaluateKpiValuesOnPeriod =
                    measureDao.evaluateKpiValuesOnPeriod(periodTimeSerieOptions);
            return new KpiResult().fill(evaluateKpiValuesOnPeriod, findKpiPerId.getEntityType());
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new KpiResult();


    }


    public Double getLastButOneStoredValueInHistory(final HistoryKey _key) {


        try {

            Validate.notNull(_key.hasEntityReference());
            final PeriodTimeSerieOptions periodTimeSerieOptions = new PeriodTimeSerieOptions();
            periodTimeSerieOptions.setTimeScale(TimeScale.PER_DAY);
            periodTimeSerieOptions.toLastTimeScale(TimeScale.PER_DAY);
            periodTimeSerieOptions.fromNbLastTimeScale(TimeScale.PER_DAY, 2);
            periodTimeSerieOptions.setGroupFormula(GroupFormula.AVG_VALUE);
            periodTimeSerieOptions.setKpiID(_key.getKpiID());
            return evaluateKpiValueOnPeriod(periodTimeSerieOptions, _key.getEntityKey());
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;

    }


    @Override
    public Double getRemainingEffort(final KpiGoal _kpiGoal) {


        try {

            final Double goalValue = _kpiGoal.getValue();
            final Double realValue = evaluateKpiGoalValue(_kpiGoal);
            final Double remainEffort = 100.0d * ((realValue - goalValue) / (goalValue + 1.0d));
            return remainEffort;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;

    }


    @Override
    public Period getRemainingTime(final KpiGoal _kpiGoal) {


        try {

            Validate.notNull(_kpiGoal);
            if (_kpiGoal.getUntilDate() == null) {
                return new Period();
            }

            return new Period(new DateTime(), new DateTime(_kpiGoal.getUntilDate()));
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new Period();

    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#returnsTheLastValue(org.komea.product.backend.service.history.HistoryKey)
     */
    @Override
    public Double returnsTheLastValue(final HistoryKey _lastKey) {


        try {

            final MeasureCriteria measureCriteria = new MeasureCriteria();
            measureCriteria.setOrderByClause("date DESC");
            measureCriteria.createCriteria()
            .andIdKpiEqualTo(generateKpiFormulaID(_lastKey.getKpiID()))
            .andEntityIDEqualTo(_lastKey.getEntityKey().getId());
            final Measure measure =
                    CollectionUtil.firstElement(measureDao.selectByCriteria(measureCriteria));
            return measure == null ? null : measure.getValue();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;

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
        MeasureUtils.setMeasureDateTime(measure, _actualTime);
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
