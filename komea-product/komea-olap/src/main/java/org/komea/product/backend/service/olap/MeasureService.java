
package org.komea.product.backend.service.olap;


import java.util.List;

import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.MeasureResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class MeasureService implements IMeasureService {
    
    @Autowired
    private IStatisticsAPI statService;
    
    @Autowired
    private IKPIService    kpiService;
    
    @Autowired
    private IEntityService entityService;
    
    @Override
    public List<MeasureResult> currentMeasures(final KpiStringKeyList _kpiKeys) {
    
        // TODO Auto-generated currentMeasures
        return Lists.newArrayList();
    }
    
    @Override
    public double currentMeasure(final KpiStringKey _kpiKey) {
    
        Kpi kpi = kpiService.findKPI(_kpiKey.getKpiName());
        IEntity entity = entityService.findEntityByEntityStringKey(_kpiKey.getEntityKey());
        HistoryKey historyKey = HistoryKey.of(kpi, entity);
        return statService.evaluateTheCurrentKpiValue(historyKey);
    }
}
