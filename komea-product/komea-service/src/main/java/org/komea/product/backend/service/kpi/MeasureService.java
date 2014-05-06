
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

import com.google.common.collect.Lists;

@Service
public class MeasureService implements IMeasureService {
    
    @Autowired
    private IHistoryService historyService;
    
    @Autowired
    private IEntityService  entityService;
    
    @Autowired
    private IKPIService     kpiService;
    
    @Override
    public MeasureResult getMeasure(final HistoryStringKey _historyKey, final LimitCriteria _limit) {
    
        MeasureResult measureResult = new MeasureResult();
        
        EntityStringKey entityKey = EntityStringKey.of(_historyKey.getEntityType().getEntityType(), _historyKey.getEntityKey());
        IEntity entity = entityService.findEntityByEntityStringKey(entityKey);
        if (entity == null) {
            return measureResult;
        }
        KpiKey kpiKey = KpiKey.ofKpiNameAndEntity(_historyKey.getKpiKey(), entity);
        Kpi kpi = kpiService.findKPI(kpiKey);
        if (kpi == null) {
            return measureResult;
        }
        
        HistoryKey historyKey = HistoryKey.of(kpi, entity);
        MeasureCriteria measureCriteria = new MeasureCriteria();
        Criteria criteria = measureCriteria.createCriteria();
        criteria.andDateBetween(_limit.getStartDate(), _limit.getEndDate());
        
        List<Measure> measures = historyService.getFilteredHistory(historyKey, _limit.getLimitNumber(), measureCriteria, criteria);
        
        for (Measure measure : measures) {
            measureResult.addHistoricalValue(new HistoricalValue(measure.getValue(), measure.getDate()));
        }
        return measureResult;
    }
    @Override
    public List<MeasureResult> getMeasures(final HistoryStringKeyList _historyKeys, final LimitCriteria _limit) {
    
        List<MeasureResult> measuresList = Lists.newArrayList();
        HistoryStringKey historyKey;
        for (String kpiKey : _historyKeys.getKpiKeys()) {
            for (String entityKey : _historyKeys.getEntityKeys()) {
                historyKey = new HistoryStringKey(kpiKey, entityKey, _historyKeys.getEntityType());
                measuresList.add(getMeasure(historyKey, _limit));
            }
        }
        return measuresList;
    }
    //
}
