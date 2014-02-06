
package org.komea.product.web.rest.api;


import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.service.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.SearchHistoricalMeasuresDto;
import org.komea.product.database.dto.SearchLastMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/measures")
public class MeasuresController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasuresController.class);
    
    @Autowired
    private IKPIService         measureHistoryService;
    
    @Autowired
    private IEntityService      entityService;
    
    /**
     * This method return the historical measure for a set of entities and for a group of kpi types between two dates
     * 
     * @param _searchLastMeasure
     *            contiain a set of entities a group of kpi types, the stating and the end date
     * @return the historical measures for this entities
     */
    @RequestMapping(method = RequestMethod.POST, value = "/historical")
    @ResponseBody
    public Map<Kpi, Map<String, List<Measure>>> historicalMeasures(@RequestBody final SearchHistoricalMeasuresDto _searchHistoricalMeasure) {
    
        LOGGER.debug("call rest method /measures/historical/");
        // TODO
        return null;
    }
    
    /**
     * This method return the last measure for a set of entities and for a group of kpi types
     * 
     * @param _searchLastMeasure
     *            contiain a set of entities and a group of kpi types
     * @return the last measures for this entities
     */
    @RequestMapping(method = RequestMethod.POST, value = "/last")
    @ResponseBody
    public Map<Kpi, Map<String, Measure>> lastMeasures(@RequestBody final SearchLastMeasuresDto _searchLastMeasure) {
    
        LOGGER.debug("call rest method /measures/last/");
        // TODO
        return new HashMap<Kpi, Map<String, Measure>>();
    }
    
    /**
     * This method get the last measure for a kpi type on an entity
     * 
     * @param _kpiKey
     *            the kpi type
     * @param _entityKey
     *            the entity
     * @return the last measure value
     * @throws KPINotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/last/{kpiKey}/{entityKey}/{entityType}")
    @ResponseBody
    public double lastMeasuresForEntity(@PathVariable(value = "kpiKey") final String _kpiKey,
            @PathVariable(value = "entityKey") final String _entityKey, @PathVariable(value = "entityType") final String _entityType)
            throws KPINotFoundException {
    
        EntityType entityType = EntityType.valueOf(_entityType);
        if (entityType == null) {
            throw new InvalidParameterException(MessageFormat.format("entityType {} is not a valid entityType", _entityType));
        }
        Integer entityID = Integer.valueOf(_entityKey);
        
        IEntity entity = entityService.getEntity(entityType, entityID);
        return measureHistoryService.findKPIFacade(entity, _kpiKey).getMetric().getDoubleValue();
    }
    
}
