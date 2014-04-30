/**
 *
 */
package org.komea.product.web.rest.api;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IKpiMathService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;

/**
 * @author sleroy
 */
public class FillKpisGroupMeasures {

    private final List<BaseEntityDto> entities;
    private final IEntityService entityService;
    private final IHistoryService measureService;
    private final List<Kpi> kpis;
    private final IKPIService kpiService;
    private final List<MeasureDto> measures;
    private final SearchMeasuresDto searchMeasuresDto;
    private final IKpiMathService kpiMathService;

    public FillKpisGroupMeasures(
            final List<Kpi> _kpis,
            final List<MeasureDto> _measures,
            final SearchMeasuresDto _searchMeasuresDto,
            final List<BaseEntityDto> _entities,
            final IKPIService _kpiService,
            final IEntityService _entityService,
            final IHistoryService _measureService,
            final IKpiMathService _kpiMathService) {

        super();
        kpis = _kpis;
        measures = _measures;
        searchMeasuresDto = _searchMeasuresDto;
        entities = _entities;
        kpiService = _kpiService;
        entityService = _entityService;
        measureService = _measureService;
        kpiMathService = _kpiMathService;
    }

    public void fillKpiGroupsMeasures() {

        final ExtendedEntityType extendedEntityType = searchMeasuresDto.getExtendedEntityType();
        final EntityType entityType = extendedEntityType.getEntityType();
        final List<String> groupKpiKeys = searchMeasuresDto.getKpiKeys();
        final List<Kpi> baseKpis = kpiService.getBaseKpisOfGroupKpiKeys(groupKpiKeys);
        kpis.addAll(kpiService.getKpisOfGroupKpiKeys(groupKpiKeys, baseKpis));
        final List<BaseEntityDto> allSubEntitiesDto = Lists.newArrayList();
        for (final BaseEntityDto simpleEntity : entities) {
            final Integer entityId = simpleEntity.getId();
            final List<? extends IEntity> subEntities
                    = entityService.getSubEntities(entityId, extendedEntityType);
            if (subEntities != null && !subEntities.isEmpty()) {
                final List<BaseEntityDto> subEntitiesDto = BaseEntityDto.convertEntities(subEntities);
                allSubEntitiesDto.addAll(subEntitiesDto);
                final List<MeasureDto> realTimeMeasures = kpiService.getRealTimeMeasuresFromEntities(
                        baseKpis, subEntitiesDto);
                final Map<Integer, List<Measure>> measuresByKpi
                        = new HashMap<Integer, List<Measure>>(realTimeMeasures.size());
                for (final Measure realTimeMeasure : realTimeMeasures) {
                    final Integer idKpi = realTimeMeasure.getIdKpi();
                    if (!measuresByKpi.containsKey(idKpi)) {
                        measuresByKpi.put(idKpi, Lists.<Measure>newArrayList());
                    }
                    measuresByKpi.get(idKpi).add(realTimeMeasure);
                }
                for (final Kpi kpi : kpis) {
                    final List<Measure> kpiMeasures = measuresByKpi.get(kpi.getId());
                    if (kpiMeasures == null || kpiMeasures.isEmpty()) {
                        continue;
                    }
                    final MeasureDto measure = new MeasureDto();
                    measure.setIdKpi(kpi.getId());
                    measure.setDate(new Date());
                    measure.setEntity(entityType, entityId);
                    measure.setKpiKey(kpi.getKpiKey());
                    final double value;
                    if (Kpi.isAverage(kpi.getKpiKey())) {
                        value = kpiMathService.computeAverageFromMeasures(kpiMeasures);
                    } else {
                        value = kpiMathService.computeSumFromMeasures(kpiMeasures);
                    }
                    measure.setValue(value);
                    measures.add(measure);
                }
            }
        }
        final List<MeasureDto> allSubMeasures = measureService.getMeasures(
                baseKpis, allSubEntitiesDto, searchMeasuresDto);
        final List<MeasureDto> history = Lists.newArrayList();
        // TODO calculate history from allSubMeasures
        measures.addAll(history);
    }
}
