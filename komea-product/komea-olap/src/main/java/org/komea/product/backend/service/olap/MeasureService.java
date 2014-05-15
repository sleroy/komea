
package org.komea.product.backend.service.olap;


import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.exceptions.EntityNotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.TimeSerie;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions;
import org.komea.product.database.dao.timeserie.TimeSerieDTO;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.EntityStringKey;
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
    
        List<MeasureResult> measures = Lists.newArrayList();
        for (String entityKey : _kpiKeys.getEntityKeys()) {
            IEntity entity = entityService.findEntityByEntityStringKey(EntityStringKey.of(_kpiKeys.getEntityType(), entityKey));
            if (entity != null) {
                BaseEntityDto baseEntity = BaseEntityDto.newFromEntity(entity);
                for (String kpiKey : _kpiKeys.getKpiKey()) {
                    Kpi kpi = kpiService.findKPI(kpiKey);
                    if (kpi != null) {
                        HistoryKey historyKey = HistoryKey.of(kpi, entity);
                        measures.add(new MeasureResult(baseEntity, kpi, statService.evaluateTheCurrentKpiValue(historyKey)));
                    }
                }
            }
        }
        return measures;
    }
    @Override
    public double currentMeasure(final KpiStringKey _kpiKey) {
    
        Kpi kpi = kpiService.findKPI(_kpiKey.getKpiName());
        if (kpi == null) {
            throw new KPINotFoundRuntimeException(_kpiKey.getKpiName());
        }
        IEntity entity = entityService.findEntityByEntityStringKey(_kpiKey.getEntityKey());
        if (entity == null) {
            throw new EntityNotFoundException(_kpiKey.getEntityKey());
        }
        HistoryKey historyKey = HistoryKey.of(kpi, entity);
        return statService.evaluateTheCurrentKpiValue(historyKey);
    }
    @Override
    public List<TimeSerieDTO> findMupltipleHistoricalMeasure(final KpiStringKeyList _kpiKeyList, final PeriodTimeSerieOptions _period) {
    
        // TODO Auto-generated findHistoricalMeasure
        return Lists.newArrayList();
    }
    @Override
    public TimeSerieDTO findHistoricalMeasure(final KpiStringKey _kpiKey, final PeriodTimeSerieOptions _period) {
    
        Validate.notNull(_kpiKey);
        Validate.notNull(_kpiKey.getKpiName());
        Validate.notNull(_kpiKey.getEntityKey());
        Validate.notNull(_kpiKey.getEntityKey().getKey());
        Validate.notNull(_kpiKey.getEntityKey().getEntityType());
        Validate.notNull(_period);
        
        Kpi kpi = kpiService.findKPI(_kpiKey.getKpiName());
        if (kpi == null) {
            throw new KPINotFoundRuntimeException(_kpiKey.getKpiName());
        }
        _period.setKpiID(kpi.getId());
        IEntity entity = entityService.findEntityByEntityStringKey(_kpiKey.getEntityKey());
        if (entity == null) {
            throw new EntityNotFoundException(_kpiKey.getEntityKey());
        }
        EntityKey entityKey = EntityKey.of(entity);
        
        TimeSerie timeSeries = statService.buildPeriodTimeSeries(_period, entityKey);
        return TimeSerieDTO.build(timeSeries.getCoordinates(), kpi, BaseEntityDto.newFromEntity(entity));
        
    }
}
