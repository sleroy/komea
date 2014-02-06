
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;



public interface IMeasureHistoryService
{
    
    
    /**
     * Returns the list of measures from an entity and a KPI.
     * 
     * @param _entity
     *            the entity.
     * @param _kpi
     *            the kpi.
     * @return the list of measures.
     */
    public List<Measure> getMeasures(HistoryKey _kpiKey);
    
    
    /**
     * Returns the list of measures from an entity and a KPI.
     * 
     * @param _entity
     *            the entity.
     * @param _kpi
     *            the kpi.
     * @return the list of measures.
     */
    public List<Measure> getMeasures(HistoryKey _kpiKey, MeasureCriteria _criteria);
    
    
    /**
     * Stores a new measure in the history.
     * 
     * @param _measure
     *            the measure
     */
    public void storeMeasure(Measure _measure);
    
    
    /**
     * Builds an history purge action from a kpi.
     * 
     * @param _kpi
     *            the kpi
     * @return the history purge action.
     */
    IHistoryPurgeAction buildHistoryPurgeAction(Kpi _kpi);
    
    
}
