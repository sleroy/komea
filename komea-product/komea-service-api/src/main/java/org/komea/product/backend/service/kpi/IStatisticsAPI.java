/**
 *
 */
package org.komea.product.backend.service.kpi;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiGoal;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerie;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.service.dto.EntityKey;

/**
 * @author sleroy
 */
public interface IStatisticsAPI {

    /**
     * This method builds a time serie from the history given a list of options.
     * Basically the purpose of a time serie is to build charts. A time serie is
     * either global to the type of entities of a kpi or for a given entity (1
     * or all). TODO:: handle a list of entities .
     *
     * @return a time serie object
     * @param _timeSerieOptions the time serie options.
     * @return the coordinates of the time serie
     */
    TimeSerie buildGlobalPeriodTimeSeries(PeriodTimeSerieOptions _timeSerieOptions);

    /**
     * Builsd period time and series
     *
     * @param _timeSerieOptions the time serie options.
     * @return the coordinates
     */
    TimeSerie buildPeriodTimeSeries(PeriodTimeSerieOptions _timeSerieOptions, EntityKey _entityKey);

    /**
     * Builds period time and series
     *
     * @param _timeSerieOptions the time serie options.
     * @return the coordinates
     */
    TimeSerie buildTimeSeries(TimeSerieOptions _timeSerieOptions, EntityKey _entityKey);

    int deleteMesuresOfEntity(IEntity _entity);

    /**
     * Returns the value represented by the kpi goal.
     *
     * @param _kpiGoal the value represented by the kpi goal.
     * @return the value.
     */
    Double evaluateKpiGoalValue(KpiGoal _kpiGoal);

    /**
     * Returns the last value on the given time scale.
     *
     * @param _kpi the kpi
     * @param _timeScale the time scale
     * @return the
     */
    KpiResult evaluateKpiLastValue(Kpi _kpi, TimeScale _timeScale);

    /**
     * Returns a value computed from kpi history given a formula. The formula
     * must provides the TimeScale and the formula used. It computes a value
     * according the period [now() -timeScale()] and the given formula; The
     * result is computed for an unique entity.
     */
    Double evaluateKpiValue(TimeSerieOptions _options, EntityKey _entityKey);

    /**
     * Returns a value computed from kpi history given a formula. The formula
     * must provides the TimeScale and the formula used. It computes a value
     * according the period [from(); to()] and the given formula; The result is
     * computed for an unique entity.
     */
    Double evaluateKpiValueOnPeriod(PeriodTimeSerieOptions _options, EntityKey _entityKey);

    /**
     * Returns a value computed from kpi history given a formula. The formula
     * must provides the TimeScale and the formula used. It computes a value
     * according the period [now() -timeScale()] and the given formula; The
     * result is computed for an unique entity.
     */
    KpiResult evaluateKpiValues(TimeSerieOptions _options);

    /**
     * Returns a value computed from kpi history given a formula. The formula
     * must provides the TimeScale and the formula used. It computes a value
     * according the period [from() ; to()] and the given formula; The result is
     * computed for an unique entity.
     */
    KpiResult evaluateKpiValuesOnPeriod(PeriodTimeSerieOptions _options);

    /**
     * Returns the remaining effort to spent to achieve the goal. This result is
     * a percentage difference.
     *
     * @param _kpiGoal the goal
     * @return the remaining effort.
     */
    Double getRemainingEffort(KpiGoal _kpiGoal);

    /**
     * Returns the remaining time
     *
     * @param _kpiGoal the kpi goal
     * @return the remaining time before the goal exhaustion.
     */
    Period getRemainingTime(KpiGoal _kpiGoal);

    /**
     * Returns the last value.
     *
     * @param _lastKey
     * @return the last value known in the history for this entity.
     */
    Double returnsTheLastValue(HistoryKey _lastKey);

    /**
     * Stores a value into the history
     *
     * @param _kpiKey the kpi key
     * @param _value the value to store
     */
    void storeValueInHistory(HistoryKey _kpiKey, Double _value);

    /**
     * Stores a value in history with the given date.
     *
     * @param _historyKey the kpi key
     * @param _value the value
     * @param _actualTime the actual time
     */
    void storeValueInHistory(HistoryKey _historyKey, Double _value, DateTime _actualTime);

    /**
     * Merge measures from entity source to entity destination
     *
     * @param entitySrc entity of measures source
     * @param entityDest entity of measures destination
     */
    void mergeEntitiesMeasures(IEntity entitySrc, IEntity entityDest);
}
