
package org.komea.product.backend.service.kpi;


import java.util.List;

import org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions;
import org.komea.product.database.dao.timeserie.TimeSerieDTO;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.MeasureResult;

public interface IMeasureService {
    
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
    
    List<TimeSerieDTO> findMupltipleHistoricalMeasure(KpiStringKeyList _kpiKeyList, PeriodTimeSerieOptions _period);
    
    TimeSerieDTO findHistoricalMeasure(KpiStringKey _kpiKey, PeriodTimeSerieOptions _period);
    //
    
}
