package org.komea.product.web.rest.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IKpiValueService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
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

    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public MeasuresDto findMeasures(@RequestBody
            final SearchMeasuresDto _searchMeasuresDto) {
        final ExtendedEntityType extendedEntityType = _searchMeasuresDto.getExtendedEntityType();
        final EntityType entityType = extendedEntityType.getEntityType();
        final List<Kpi> simpleKpis = kpiService.getKpis(
                extendedEntityType.getKpiType(), _searchMeasuresDto.getKpiKeys());
        final List<Kpi> kpis;
        if (extendedEntityType.isForGroups()) {
            kpis = kpiService.getKpisForGroups(simpleKpis);
        } else {
            kpis = simpleKpis;
        }
        final List<BaseEntityDto> simpleEntities = entityService.getBaseEntityDTOS(
                extendedEntityType.getEntityType(), _searchMeasuresDto.getEntityKeys());
        final List<Measure> measures = new ArrayList<Measure>();
        if (extendedEntityType.isForGroups()) {
            for (final BaseEntityDto simpleEntity : simpleEntities) {
                final Integer entityId = simpleEntity.getId();
                final List<? extends IEntity> entities = entityService.getSubEntities(entityId, extendedEntityType);
                if (entities != null && !entities.isEmpty()) {
                    List<BaseEntityDto> subEntities = BaseEntityDto.convertEntities(entities);
                    final List<Measure> realTimeMeasures = kpiValueService.getRealTimeMeasuresFromEntities(
                            simpleKpis, subEntities);
                    final Map<Integer, List<Measure>> measuresByKpi = new HashMap<Integer, List<Measure>>(realTimeMeasures.size());
                    for (final Measure realTimeMeasure : realTimeMeasures) {
                        final Integer idKpi = realTimeMeasure.getIdKpi();
                        if (!measuresByKpi.containsKey(idKpi)) {
                            measuresByKpi.put(idKpi, new ArrayList<Measure>());
                        }
                        measuresByKpi.get(idKpi).add(realTimeMeasure);
                    }
                    for (final Kpi kpi : kpis) {
                        final List<Measure> kpiMeasures = measuresByKpi.get(kpi.getId());
                        if (kpiMeasures == null || kpiMeasures.isEmpty()) {
                            continue;
                        }
                        final Measure measure = new Measure();
                        measure.setIdKpi(kpi.getId());
                        measure.setDate(new Date());
                        measure.setEntityId(entityType, entityId);
                        if (kpi.isAverage()) {
                            kpiValueService.setAverage(kpiMeasures, measure);
                        } else {
                            kpiValueService.setSum(kpiMeasures, measure);
                        }
                        measures.add(measure);
                    }
                }
            }
        } else {
            measures.addAll(kpiValueService.getRealTimeMeasuresFromEntities(simpleKpis, simpleEntities));
        }
        //todo
//        measures.addAll(measureService.getMeasures(simpleKpis, simpleEntities, _searchMeasuresDto));
        return new MeasuresDto(extendedEntityType, simpleEntities, kpis, measures);
    }

    /**
     * This method get the last measure for a kpi type on an entity
     *
     * @param _kpiKey the kpi type
     * @param _entityKey the entity
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
