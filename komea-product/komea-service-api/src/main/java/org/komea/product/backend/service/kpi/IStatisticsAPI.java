/**
 * 
 */

package org.komea.product.backend.service.kpi;

import java.util.List;

import org.joda.time.DateTime;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Measure;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
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
	 * Compute the average of kpi measures
	 * 
	 * @param _kpiMeasures
	 *            the kpi measures
	 * @return a value representing the average.
	 */
	@Deprecated
	double computeAverageFromMeasures(List<Measure> _kpiMeasures);

	/**
	 * Computes the sum from a list of measures
	 * 
	 * @param _kpiMeasures
	 *            the kpi measures
	 * @return a value representing the sum
	 */
	@Deprecated
	double computeSumFromMeasures(List<Measure> _kpiMeasures);

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
	 * Return the current value of a KPI for a given entity.
	 * 
	 * @warning Should only be used to measure progression of a kpi.
	 * @param _kpiKeys
	 *            the kpi and entity
	 * @return the current kpi value.
	 */
	Double evaluateTheCurrentKpiValue(HistoryKey _kpiKeys);

	/**
	 * Return the current kpi values.
	 * 
	 * @warning Should only be used to measure progression of a kpi.
	 * @param _kpIID
	 * @return a map associating the entities with their values.
	 */
	KpiResult evaluateTheCurrentKpiValues(Integer _kpIID);

	/**
	 * Returns the kpi values averaged since the begin period given in
	 * parameter. The results are returned as an object called KpiRESULT.
	 * Basically it is a map referencing an entity key to its value.
	 * 
	 * @param _kpiID
	 *            the kpi name
	 * @param _previousTime
	 *            the previous time.
	 * @return the kpi result
	 */
	KpiResult getKpiValuesAverageOnPeriod(Integer _kpiID, DateTime _previousTime);

	/**
	 * Returns the last stored value into the history of a KPI.
	 * 
	 * @param the
	 *            history key.
	 */
	@Deprecated
	Double getLastStoredValueInHistory(HistoryKey _key);

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
