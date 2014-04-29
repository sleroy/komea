package org.komea.product.web.rest.api;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IKpiValueService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.dto.MeasuresDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.KpiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/measures")
public class MeasuresController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasuresController.class);

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IKPIService kpiService;

    @Autowired
    private IKpiValueService kpiValueService;

    @Autowired
    private IHistoryService measureService;

    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public MeasuresDto findMeasures(@RequestBody
            final SearchMeasuresDto _searchMeasuresDto) {
        LOGGER.info("call rest method /measures/find/ with body: " + _searchMeasuresDto);
        final ExtendedEntityType extendedEntityType = _searchMeasuresDto.getExtendedEntityType();
        final EntityType entityType = extendedEntityType.getEntityType();
        final List<BaseEntityDto> entities = entityService.getBaseEntityDTOS(
                entityType, _searchMeasuresDto.getEntityKeys());
        final List<Kpi> kpis = Lists.newArrayList();
        final List<MeasureDto> measures = Lists.newArrayList();
        if (extendedEntityType.isForGroups()) {
            fillKpiGroupsMeasures(kpis, measures, _searchMeasuresDto, entities);
        } else {
            kpis.addAll(kpiService.getKpis(extendedEntityType.getKpiType(), _searchMeasuresDto.getKpiKeys()));
            measures.addAll(kpiValueService.getRealTimeMeasuresFromEntities(kpis, entities));
            measures.addAll(measureService.getMeasures(kpis, entities, _searchMeasuresDto));
        }
        LOGGER.info("entities: " + entities);
        LOGGER.info("kpis: " + kpis);
        LOGGER.info("measures: " + measures);
        return new MeasuresDto(extendedEntityType, entities, kpis, measures);
    }

    private void fillKpiGroupsMeasures(final List<Kpi> kpis, final List<MeasureDto> measures,
            final SearchMeasuresDto _searchMeasuresDto, final List<BaseEntityDto> entities) {
        final ExtendedEntityType extendedEntityType = _searchMeasuresDto.getExtendedEntityType();
        final EntityType entityType = extendedEntityType.getEntityType();
        final List<String> groupKpiKeys = _searchMeasuresDto.getKpiKeys();
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
                final List<MeasureDto> realTimeMeasures = kpiValueService.getRealTimeMeasuresFromEntities(
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
                    if (Kpi.isAverage(kpi.getKpiKey())) {
                        kpiValueService.setAverage(kpiMeasures, measure);
                    } else {
                        kpiValueService.setSum(kpiMeasures, measure);
                    }
                    measures.add(measure);
                }
            }
        }
        final List<MeasureDto> allSubMeasures = measureService.getMeasures(
                baseKpis, allSubEntitiesDto, _searchMeasuresDto);
        final List<MeasureDto> history = Lists.newArrayList();
        // TODO calculate history from allSubMeasures
        measures.addAll(history);
    }

    /**
     * This method get the last measure for a kpi type on an entity
     *
     * @param _kpiKey the kpi type
     * @return the last measure value
     * @throws KPINotFoundException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/last", produces = "application/json")
    @ResponseBody
    public Double lastMeasuresForEntity(@Valid
            @RequestBody
            final KpiKey _kpiKey) throws KPINotFoundException {

        final double value = kpiService.getSingleValue(_kpiKey).doubleValue();
        return value;
    }

}
