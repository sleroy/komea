package org.komea.product.web.rest.api;


import java.util.List;
import javax.validation.Valid;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.MeasuresDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.KPIValueTable;
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
    private IHistoryService     measureService;
    
    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public MeasuresDto findMeasures(@RequestBody final SearchMeasuresDto _searchMeasuresDto) {
    
        final EntityType entityType = _searchMeasuresDto.getEntityType();
        System.out.println("findMeasures " + a + " : " + _searchMeasuresDto);
        final List<Kpi> kpis = kpiService.getKpis(entityType, _searchMeasuresDto.getKpiKeys());
        final List<BaseEntity> entities = entityService.getEntities(entityType, _searchMeasuresDto.getEntityKeys());
        final List<Measure> measures = measureService.getMeasures(kpis, entities, _searchMeasuresDto);
        return new MeasuresDto(entityType, entities, kpis, measures);
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
    public Double lastMeasuresForEntity(@Valid @RequestBody final KpiKey _kpiKey) throws KPINotFoundException {
    
        LOGGER.info("request /measures/last");
        LOGGER.info("kpi key =  {}", _kpiKey.toString());
        final double value = kpiService.getKpiSingleValue(_kpiKey);
        LOGGER.info("value = {}", value);
        return value;
    }
    
    /**
     * Returns the kPI double value.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return the kpi double value.
     * @throws KPINotFoundException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/realtime", produces = "application/json")
    @ResponseBody
    <T extends IEntity> KPIValueTable<T> getKpiRealTimeValues(@Valid @RequestBody final KpiKey _kpiKey) throws KPINotFoundException {
    
        KPIValueTable<T> kpiValueTable = kpiService.getKpiRealTimeValues(_kpiKey);
        return kpiValueTable;
        
    }
}
