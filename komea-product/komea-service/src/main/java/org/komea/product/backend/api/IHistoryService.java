
package org.komea.product.backend.api;



import java.util.Collection;
import java.util.List;

import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;



/**
 */
public interface IHistoryService
{
    
    
    /**
     * This method get the last n measure for a history key
     * 
     * @param _kpiKey
     *            the kpi key
     * @param _nbRow
     *            the number of result asked
     * @param _criteria
     *            MeasureCriteria
     * @return the measures list
     */
    List<Measure> getFilteredHistory(HistoryKey _kpiKey, int _nbRow, MeasureCriteria _criteria);
    
    
    /**
     * Returns the list of measures from an entity and a KPI.
     * 
     * @param _kpiKey
     *            HistoryKey
     * @param _criteria
     *            MeasureCriteria
     * @return the list of measures.
     */
    List<Measure> getFilteredHistory(HistoryKey _kpiKey, MeasureCriteria _criteria);
    
    
    /**
     * Returns the list of measures from an entity and a KPI.
     * 
     * @param _kpiKey
     *            HistoryKey
     * @return the list of measures.
     */
    List<Measure> getHistory(HistoryKey _kpiKey);
    
    
    List<MeasureDto> getMeasures(
            Collection<Kpi> kpis,
            Collection<? extends IEntity> entities,
            SearchMeasuresDto searchMeasuresDto);
    
    
    /**
     * Stores a new measure in the history.
     * 
     * @param _measure
     *            the measure
     */
    void storeMeasure(Measure _measure);
    
}
