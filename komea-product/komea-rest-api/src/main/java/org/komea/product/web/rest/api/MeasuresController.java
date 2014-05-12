
package org.komea.product.web.rest.api;


import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKpiAPI;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.dto.MeasuresDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.HistoricalMeasureRequest;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.MeasureResult;
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
@RequestMapping(value = "/measures")
public class MeasuresController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasuresController.class);
    
    @Autowired
    private IEntityService      entityService;
    
    @Autowired
    private IKpiAPI             kpiService;
    
    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public MeasuresDto findMeasures(@RequestBody final SearchMeasuresDto _searchMeasuresDto) {
    
        LOGGER.debug("call rest method /measures/find/ with body: " + _searchMeasuresDto);
        final ExtendedEntityType extendedEntityType = _searchMeasuresDto.getExtendedEntityType();
        final EntityType entityType = extendedEntityType.getEntityType();
        final List<BaseEntityDto> entities = entityService.getBaseEntityDTOS(entityType, _searchMeasuresDto.getEntityKeys());
        final List<Kpi> kpis = Lists.newArrayList();
        final List<MeasureDto> measures = Lists.newArrayList();
        if (extendedEntityType.isForGroups()) {
            final FillKpisGroupMeasures fillKpisGroupMeasures = new FillKpisGroupMeasures(kpis, measures, _searchMeasuresDto, entities,
                    kpiService, entityService);
            fillKpisGroupMeasures.fillKpiGroupsMeasures();
        } else {
            if (_searchMeasuresDto.hasNoSpecificKpisRequested()) {
                kpis.addAll(kpiService.getAllKpisOfEntityType(entityType));
            } else {
                kpis.addAll(kpiService.getSelectionOfKpis(_searchMeasuresDto.getKpiKeys()));
            }
            
            measures.addAll(kpiService.getRealTimeMeasuresFromEntities(kpis, entities));
            measures.addAll(kpiService.getMeasures(kpis, entities, _searchMeasuresDto));
            
        }
        return new MeasuresDto(extendedEntityType, entities, kpis, measures);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/historic", consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MeasureResult> findHistoricalMeasure(@RequestBody final HistoricalMeasureRequest _request) {
    
        LOGGER.debug("call rest method /measures/historic/ with body: ");
        return kpiService.getHistoricalMeasures(_request.getHistoryKeyList(), _request.getLimit());
    }
    
    /**
     * This method get the last measure for a kpi type on an entity
     *
     * @param _kpiKey
     *            the kpi type
     * @return the last measure value
     * @throws KPINotFoundException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/last", produces = "application/json")
    @ResponseBody
    public Double lastMeasuresForEntity(@Valid @RequestBody final KpiKey _kpiKey) throws KPINotFoundException {
    
        Validate.isTrue(_kpiKey.isAssociatedToEntity());
        final KpiResult kpiValues = kpiService.getKpiValues(_kpiKey.getKpiName());
        return kpiValues.getDoubleValue(_kpiKey.getEntityKey());
        
    }
    
}
