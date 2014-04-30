
package org.komea.product.web.rest.api;



import java.util.List;

import javax.validation.Valid;

import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IKpiMathService;
import org.komea.product.backend.service.kpi.IKpiValueService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.dto.MeasuresDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;



@Controller
@RequestMapping(
    value = "/measures")
public class MeasuresController
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasuresController.class);
    
    @Autowired
    private IEntityService      entityService;
    
    @Autowired
    private IKpiMathService     kpiMathService;
    
    @Autowired
    private IKPIService         kpiService;
    
    @Autowired
    private IKpiValueService    kpiValueService;
    
    @Autowired
    private IHistoryService     measureService;
    
    
    
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/find")
    @ResponseBody
    public MeasuresDto findMeasures(@RequestBody
    final SearchMeasuresDto _searchMeasuresDto) {
    
    
        LOGGER.debug("call rest method /measures/find/ with body: " + _searchMeasuresDto);
        final ExtendedEntityType extendedEntityType = _searchMeasuresDto.getExtendedEntityType();
        final EntityType entityType = extendedEntityType.getEntityType();
        final List<BaseEntityDto> entities =
                entityService.getBaseEntityDTOS(entityType, _searchMeasuresDto.getEntityKeys());
        final List<Kpi> kpis = Lists.newArrayList();
        final List<MeasureDto> measures = Lists.newArrayList();
        if (extendedEntityType.isForGroups()) {
            
            new FillKpisGroupMeasures(kpis, measures, _searchMeasuresDto, entities);
        } else {
            kpis.addAll(kpiService.getKpis(extendedEntityType.getKpiType(),
                    _searchMeasuresDto.getKpiKeys()));
            measures.addAll(kpiValueService.getRealTimeMeasuresFromEntities(kpis, entities));
            measures.addAll(measureService.getMeasures(kpis, entities, _searchMeasuresDto));
        }
        return new MeasuresDto(extendedEntityType, entities, kpis, measures);
    }
    
    
    public IKpiMathService getKpiMathService() {
    
    
        return kpiMathService;
    }
    
    
    /**
     * This method get the last measure for a kpi type on an entity
     * 
     * @param _kpiKey
     *            the kpi type
     * @return the last measure value
     * @throws KPINotFoundException
     */
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/last",
        produces = "application/json")
    @ResponseBody
    public Double lastMeasuresForEntity(@Valid
    @RequestBody
    final KpiKey _kpiKey) throws KPINotFoundException {
    
    
        final double value = kpiService.getSingleValue(_kpiKey).doubleValue();
        return value;
    }
    
    
    public void setKpiMathService(final IKpiMathService _kpiMathService) {
    
    
        kpiMathService = _kpiMathService;
    }
    
    
}
