/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions;
import org.komea.product.database.dao.timeserie.TimeSerieOptions;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.KpiKey;



/**
 * @author sleroy
 */
public interface IStatisticsAPI
{
    
    
    /**
     * This method builds a time serie from the history given a list of options. Basically the purpose of a time serie is to build charts.
     * A time serie is either global to the type of entities of a kpi or for a given entity (1 or all). TODO:: handle a list of entities .
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
     * Returns a value computed from kpi history given a formula. The formula must provides the TimeScale and the formula used. It computes
     * a value according the period [now() -timeScale()] and the given formula;
     * The result is computed for an unique entity.
     */
    KpiResult getKpiValuesOnPeriod(PeriodTimeSerieOptions _options);
    
    
    /**
     * Returns a value computed from kpi history given a formula. The formula must provides the TimeScale and the formula used. It computes
     * a value according the period [now() -timeScale()] and the given formula;
     * The result is computed for an unique entity.
     */
    Double evaluateKpiValueOnPeriod(PeriodTimeSerieOptions _options, EntityKey _entityKey);
    
    
    /**
     * Returns a value computed from kpi history given a formula. The formula must provides the TimeScale and the formula used. It computes
     * a value according the period [now() -timeScale()] and the given formula;
     * The result is computed for an unique entity.
     */
    KpiResult getKpiValues(TimeSerieOptions _options);
    
    
    /**
     * Returns a value computed from kpi history given a formula. The formula must provides the TimeScale and the formula used. It computes
     * a value according the period [now() -timeScale()] and the given formula;
     * The result is computed for an unique entity.
     */
    Double evaluateKpiValue(TimeSerieOptions _options, EntityKey _entityKey);
    
    
    /**
     * Return the current value of a KPI for a given entity.
     * 
     * @warning Should only be used to measure progression of a kpi.
     * @param _kpiKeys
     *            the kpi and entity
     * @return the current kpi value.
     */
    Double getTheCurrentKpiValue(KpiKey _kpiKeys);
    
    
    /**
     * Return the current kpi values.
     * 
     * @warning Should only be used to measure progression of a kpi.
     * @param _kpiName
     * @return a map associating the entities with their values.
     */
    KpiResult getTheCurrentKpiValues(String _kpiName);
}
