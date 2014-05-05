
package org.komea.product.backend.api;



import java.util.List;

import org.joda.time.DateTime;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.model.Kpi;



/**
 */
public interface IMeasureHistoryService extends IHistoryService
{
    
    
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
    
    
    /**
     * Builds an history purge action from a kpi.
     * 
     * @param _kpi
     *            the kpi
     * @return the history purge action.
     */
    IHistoryPurgeAction buildHistoryPurgeAction(Kpi _kpi);


    
    
}
