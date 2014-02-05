
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;



public interface IMeasureService
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
    public List<Measure> getMeasures(IEntity _entity, Kpi _kpi);
    
    
    /**
     * Stores a new measure in the history.
     * 
     * @param _measure
     *            the measure
     */
    public void storeMeasure(Measure _measure);
    
    
    /**
     * Returns a metric from a EPStatement.
     * 
     * @param _measureName
     *            the measure name.
     * @return the epstatement metric.
     */
    IEPMetric findMeasure(String _measureName);
}
