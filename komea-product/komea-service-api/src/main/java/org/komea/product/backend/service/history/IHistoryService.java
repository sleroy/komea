
package org.komea.product.backend.service.history;



import java.util.List;

import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;



public interface IHistoryService
{
    
    
    /**
     * This method get the last n measure for a history key
     * 
     * @param _kpiKey
     *            the kpi key
     * @param _nbRow
     *            the number of result asked
     * @return the measures list
     */
    public abstract List<Measure> getFilteredHistory(
            HistoryKey _kpiKey,
            int _nbRow,
            MeasureCriteria _criteria);
    
    
    /**
     * Returns the list of measures from an entity and a KPI.
     * 
     * @param _entity
     *            the entity.
     * @param _kpi
     *            the kpi.
     * @return the list of measures.
     */
    public abstract List<Measure> getFilteredHistory(HistoryKey _kpiKey, MeasureCriteria _criteria);
    
    
    /**
     * Returns the list of measures from an entity and a KPI.
     * 
     * @param _entity
     *            the entity.
     * @param _kpi
     *            the kpi.
     * @return the list of measures.
     */
    public abstract List<Measure> getHistory(HistoryKey _kpiKey);
    
    
    /**
     * Stores a new measure in the history.
     * 
     * @param _measure
     *            the measure
     */
    public abstract void storeMeasure(Measure _measure);
    
}
