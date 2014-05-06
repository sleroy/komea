
package org.komea.product.backend.service.kpi;


import java.util.List;

import org.komea.product.backend.api.IHistoryService;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.database.model.MeasureCriteria.Criteria;
import org.komea.product.service.dto.EntityStringKey;
import org.komea.product.service.dto.KpiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasureService implements IMeasureService {
    
    @Autowired
    private IHistoryService historyService;
    
    @Autowired
    private IEntityService  entityService;
    
    @Autowired
    private IKPIService     kpiService;
    
    @Override
    public MeasureResult getMeasure(final HistoryStringKey _measureKey, final LimitCriteria _limit) {
    
        EntityStringKey entityKey = EntityStringKey.of(_measureKey.getEntityType().getEntityType(), _measureKey.getEntityKey());
        IEntity entity = entityService.getEntityOrFail(entityKey);
        KpiKey kpiKey = KpiKey.ofKpiNameAndEntity(_measureKey.getKpiKey(), entity);
        Kpi kpi = kpiService.findKPIOrFail(kpiKey);
        
        HistoryKey historyKey = HistoryKey.of(kpi, entity);
        MeasureCriteria measureCriteria = new MeasureCriteria();
        Criteria criteria = measureCriteria.createCriteria();
        criteria.andDateBetween(_limit.getStartDate(), _limit.getEndDate());
        
        List<Measure> measures = historyService.getFilteredHistory(historyKey, _limit.getLimitNumber(), measureCriteria, criteria);
        
        MeasureResult measureResult = new MeasureResult();
        for (Measure measure : measures) {
            measureResult.addHistoricalValue(new HistoricalValue(measure.getValue(), measure.getDate()));
        }
        return measureResult;
    }
    @Override
    public List<MeasureResult> getMeasures(final List<HistoryStringKey> _measureKey, final LimitCriteria _limit) {
    
        // TODO Auto-generated getMeasures
        return null;
    }
    //
}
