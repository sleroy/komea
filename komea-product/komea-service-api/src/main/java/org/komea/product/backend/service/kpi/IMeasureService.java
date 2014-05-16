
package org.komea.product.backend.service.kpi;


import java.util.List;

import org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions;
import org.komea.product.database.dao.timeserie.TimeSerieDTO;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.MeasureResult;

public interface IMeasureService {
    
    /**
     * This method return the current value list of kpi apply on a list of entities
     * 
     * @param _kpiKeys
     *            contain the kpi name list, the entity keys list and the entity type
     * @return the current value list for each couple (entity, kpi)
     */
    List<MeasureResult> currentMeasures(KpiStringKeyList _kpiKeys);
    
    /**
     * This method return the current value of the kpi on an entity
     * If the kpi does not exist a KPINotFoundRuntimeException is launched
     * If the entity does not exist a EntityNotFoundException is launched
     * 
     * @param _kpiKey
     *            the kpiString keu (kpi key, entity key and entity type)
     * @return the current value
     */
    double currentMeasure(KpiStringKey _kpiKey);
    
    /**
     * this method find historical measure for a list of kpi apply on a list of entities during a period
     * 
     * @param _kpiKeyList
     *            contain the kpi name list, the entity keys list and the entity type
     * @param _period
     *            the period to fond measure (start --> end)
     * @return a list of series of measure with these date for each couple (entity, kpi)
     */
    List<TimeSerieDTO> findMupltipleHistoricalMeasure(KpiStringKeyList _kpiKeyList, PeriodTimeSerieOptions _period);
    
    /**
     * this method find historical measure for a kpi on an entity during a period
     * 
     * @param _kpiKey
     *            the kpi key, which contain the kpi name, the entity name and the entity type
     * @param _period
     *            the period to fond measure (start --> end)
     * @return a series of measure with these date
     */
    TimeSerieDTO findHistoricalMeasure(KpiStringKey _kpiKey, PeriodTimeSerieOptions _period);
    //
    
}
