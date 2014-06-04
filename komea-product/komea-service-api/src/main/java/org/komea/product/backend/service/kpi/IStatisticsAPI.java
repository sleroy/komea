/**
 *
 */

package org.komea.product.backend.service.kpi;


import org.joda.time.DateTime;
import org.joda.time.Period;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiGoal;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.service.dto.EntityKey;

/**
 * @author sleroy
 */
public interface IStatisticsAPI {
    
    /**
     * Force backup of kpi measures according the given delay.
     * 
     * @param _delay
     */
    void backupKpiValuesIntoHistory(BackupDelay _delay);
    
    /**
     * This method builds a time serie from the history given a list of options.
     * Basically the purpose of a time serie is to build charts. A time serie is
     * either global to the type of entities of a kpi or for a given entity (1
     * or all). TODO:: handle a list of entities .
     * 
     * @return a time serie object
     * @param _timeSerieOptions
     *            the time serie options.
     * @return the coordinates of the time serie
     */
    TimeSerie buildGlobalPeriodTimeSeries(PeriodTimeSerieOptions _timeSerieOptions);
    
    /**
     * Builsd period time and series
     * 
     * @param _timeSerieOptions
     *            the time serie options.
     * @return the coordinates
     */
    TimeSerie buildPeriodTimeSeries(PeriodTimeSerieOptions _timeSerieOptions, EntityKey _entityKey);
    
    /**
     * Builds period time and series
     * 
     * @param _timeSerieOptions
     *            the time serie options.
     * @return the coordinates
     */
    TimeSerie buildTimeSeries(TimeSerieOptions _timeSerieOptions, EntityKey _entityKey);
    
    /**
     * Returns the value represented by the kpi goal.
     * 
     * @param _kpiGoal
     *            the value represented by the kpi goal.
     * @return the value.
     */
    Double evaluateKpiGoalValue(KpiGoal _kpiGoal);
    
    /**
     * Evaluate the last value of a kpi since the timescale provided.
     * 
     * @param _kpiPerId
     *            the kpii
     * @param _timeScale
     *            the time scale.
     * @return
     */
    KpiResult evaluateKpiLastValue(Kpi _kpiPerId, TimeScale _timeScale);
    
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
     * according the period [now() -timeScale()] and the given formula; The
     * result is computed for an unique entity.
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
     * according the period [now() -timeScale()] and the given formula; The
     * result is computed for an unique entity.
     */
    KpiResult evaluateKpiValuesOnPeriod(PeriodTimeSerieOptions _options);
    
    /**
     * Evaluate the current kpi value.
     * 
     * @param _kpiKeys
     *            the history key
     */
    Double evaluateTheCurrentKpiValue(final HistoryKey _kpiKeys);
    
    /**
     * Return the current kpi values.
     * 
     * @warning Should only be used to measure progression of a kpi.
     * @param _kpIID
     * @return a map associating the entities with their values.
     */
    KpiResult evaluateTheCurrentKpiValues(Integer _kpIID);
    
    Double getLastButOneStoredValueInHistory(HistoryKey _key);
    
    /**
     * Returns the remaining effort to spent to achieve the goal. This result is a percentage difference.
     * 
     * @param _kpiGoal
     *            the goal
     * @return the remaining effort.
     */
    Double getRemainingEffort(KpiGoal _kpiGoal);
    
    /**
     * Returns the remaining time
     * 
     * @param _kpiGoal
     *            the kpi goal
     * @return the remaining time before the goal exhaustion.
     */
    Period getRemainingTime(KpiGoal _kpiGoal);
    
    /**
     * Stores a value into the history
     * 
     * @param _kpiKey
     *            the kpi key
     * @param _value
     *            the value to store
     */
    void storeValueInHistory(HistoryKey _kpiKey, Double _value);
    
    /**
     * Stores a value in history with the given date.
     * 
     * @param _historyKey
     *            the kpi key
     * @param _value
     *            the value
     * @param _actualTime
     *            the actual time
     */
    void storeValueInHistory(HistoryKey _historyKey, Double _value, DateTime _actualTime);
}
