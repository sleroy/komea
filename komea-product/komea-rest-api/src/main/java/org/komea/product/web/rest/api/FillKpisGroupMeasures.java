/**
 *
 */

package org.komea.product.web.rest.api;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKpiAPI;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 * @author scarreau
 */
@Deprecated
public class FillKpisGroupMeasures
{
    
    
    private final List<BaseEntityDto> entities;
    private final IEntityService      entityService;
    private final List<Kpi>           kpis;
    private final IKpiAPI             kpiService;
    private final List<MeasureDto>    measures;
    private final SearchMeasuresDto   searchMeasuresDto;
    
    
    
    public FillKpisGroupMeasures(
            final List<Kpi> _kpis,
            final List<MeasureDto> _measures,
            final SearchMeasuresDto _searchMeasuresDto,
            final List<BaseEntityDto> _entities,
            final IKpiAPI _kpiService,
            final IEntityService _entityService) {
    
    
        super();
        kpis = _kpis;
        measures = _measures;
        searchMeasuresDto = _searchMeasuresDto;
        entities = _entities;
        kpiService = _kpiService;
        entityService = _entityService;
    }
    
    
    public void buildMapKpiMeasure(
            final List<MeasureDto> realTimeMeasures,
            final Map<Integer, List<Measure>> measuresByKpi) {
    
    
        for (final Measure realTimeMeasure : realTimeMeasures) {
            final Integer idKpi = realTimeMeasure.getIdKpi();
            if (!measuresByKpi.containsKey(idKpi)) {
                measuresByKpi.put(idKpi, Lists.<Measure> newArrayList());
            }
            measuresByKpi.get(idKpi).add(realTimeMeasure);
        }
    }
    
    
    public void fillKpiGroupsMeasures() {
    
    
        final ExtendedEntityType extendedEntityType = searchMeasuresDto.getExtendedEntityType();
        final EntityType entityType = extendedEntityType.getEntityType();
        final List<String> groupKpiKeys = searchMeasuresDto.getKpiKeys();
        final List<Kpi> baseKpis = initKpiList(groupKpiKeys);
        final List<BaseEntityDto> allSubEntitiesDto = Lists.newArrayList();
        for (final BaseEntityDto simpleEntity : entities) {
            final Integer entityId = simpleEntity.getId();
            final List<? extends IEntity> subEntities =
                    entityService.getSubEntities(entityId, extendedEntityType);
            if (hasSubEntities(subEntities)) {
                computeMeasuresForSubEntities(entityType, baseKpis, allSubEntitiesDto, entityId,
                        subEntities);
            }
        }
        kpiService.getMeasures(baseKpis, allSubEntitiesDto, searchMeasuresDto);
        final List<MeasureDto> history = Lists.newArrayList();
        // TODO calculate history from allSubMeasures
        measures.addAll(history);
    }


    public void computeMeasuresForSubEntities(
            final EntityType entityType,
            final List<Kpi> baseKpis,
            final List<BaseEntityDto> allSubEntitiesDto,
            final Integer entityId,
            final List<? extends IEntity> subEntities) {
    
    
        final List<BaseEntityDto> subEntitiesDto =
                BaseEntityDto.convertEntities(subEntities);
        allSubEntitiesDto.addAll(subEntitiesDto);
        final List<MeasureDto> realTimeMeasures =
                kpiService.getRealTimeMeasuresFromEntities(baseKpis, subEntitiesDto);
        final Map<Integer, List<Measure>> measuresByKpi =
                new HashMap<Integer, List<Measure>>(realTimeMeasures.size());
        buildMapKpiMeasure(realTimeMeasures, measuresByKpi);
        forEachKpiComputeAggregateMeasure(entityType, entityId, measuresByKpi);
    }


    public void forEachKpiComputeAggregateMeasure(
            final EntityType entityType,
            final Integer entityId,
            final Map<Integer, List<Measure>> measuresByKpi) {
    
    
        for (final Kpi kpi : kpis) {
            final List<Measure> kpiMeasures = measuresByKpi.get(kpi.getId());
            if (kpiMeasures == null || kpiMeasures.isEmpty()) {
                continue;
            }
            
            measures.add(buildMeasureWithSummaryValue(kpiMeasures, kpi, entityType,
                    entityId));
        }
    }
    
    
    public boolean hasSubEntities(final List<? extends IEntity> subEntities) {
    
    
        return subEntities != null && !subEntities.isEmpty();
    }
    
    
    public List<Kpi> initKpiList(final List<String> groupKpiKeys) {
    
    
        final List<Kpi> baseKpis = kpiService.getBaseKpisOfGroupKpiKeys(groupKpiKeys);
        kpis.addAll(kpiService.getKpisOfGroupKpiKeys(groupKpiKeys, baseKpis));
        return baseKpis;
    }
    
    
    /**
     * @param _kpiMeasures
     * @param kpi
     * @param entityType
     * @param entityId
     * @return
     */
    private MeasureDto buildMeasureWithSummaryValue(
            final List<Measure> _kpiMeasures,
            final Kpi kpi,
            final EntityType entityType,
            final Integer entityId) {
    
    
        final MeasureDto measure = new MeasureDto();
        measure.setIdKpi(kpi.getId());
        measure.setDate(new Date());
        measure.setEntity(entityType, entityId);
        measure.setKpiKey(kpi.getKpiKey());
        final double value;
        if (Kpi.isAverage(kpi.getKpiKey())) {
            value = kpiService.computeAverageFromMeasures(_kpiMeasures);
        } else {
            value = kpiService.computeSumFromMeasures(_kpiMeasures);
        }
        measure.setValue(value);
        return measure;
    }
}
