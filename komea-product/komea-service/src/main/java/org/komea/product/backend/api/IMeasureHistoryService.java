
package org.komea.product.backend.api;


import java.util.List;

import org.joda.time.DateTime;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.HistoryStringKey;
import org.komea.product.service.dto.HistoryStringKeyList;
import org.komea.product.service.dto.LimitCriteria;
import org.komea.product.service.dto.MeasureResult;

/**
 */
public interface IMeasureHistoryService extends IHistoryService {
    
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
    
    /**
     * This method find the historical measures of a a KPI on an entity
     * 
     * @param _historyKey
     *            contain the KPI key, the entity key and the entity type
     * @param _limit
     *            the limit of the research by entity. As the number of result of the date range
     * @return the measures
     */
    MeasureResult getHistoricalMeasure(final HistoryStringKey _historyKey, final LimitCriteria _limit);
    
    /**
     * This method find the historical measures of a a list of KPIs on a list of entities
     * 
     * @param _historyKey
     *            contain the KPI list keys, the entity list keys and the entity type
     * @param _limit
     *            the limit of the research by entity. As the number of result of the date range
     * @return a list of measures
     */
    List<MeasureResult> getHistoricalMeasures(final HistoryStringKeyList _historyKeyList, final LimitCriteria _limit);
    
}
