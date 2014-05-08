
package org.komea.product.backend.api;



import org.joda.time.DateTime;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.model.MeasureCriteria;



/**
 */
public interface IMeasureHistoryService extends IHistoryService
{
    
    
    /**
     * Deletes the measure criteria.
     * 
     * @param _measureCriteria
     *            the measure criteria.
     */
    public void deleteByCriteria(MeasureCriteria _measureCriteria);
    
    
    /**
     * Stores a measure from a kpi and the value;
     * 
     * @param _ofKpi
     *            the kpi key
     * @param _value
     *            the value
     * @param _key
     *            the entity key
     * @param _analysisDate
     *            the date of the measure
     */
    public void storeMeasure(HistoryKey _ofKpi, Double _value, DateTime _analysisDate);
    
    
}
