
package org.komea.product.web.rest.api;


import java.util.List;

import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.service.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dto.SearchHistoricalMeasuresDto;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.MeasureHistoricalResultDto;
import org.komea.product.service.dto.MeasureResultDto;
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
    private IKPIService         measureHistoryService;
    
    @Autowired
    private IEntityService      entityService;
    
    private List<MeasureHistoricalResultDto> findHistoricalsMeasuresByDate(final SearchHistoricalMeasuresDto _searchHistoricalMeasure) {
    
        if (_searchHistoricalMeasure.getEnd().after(_searchHistoricalMeasure.getStart())) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        
        List<MeasureHistoricalResultDto> historicalMeasures = Lists.newArrayList();
        MeasureCriteria criteria = new MeasureCriteria();
        criteria.setOrderByClause("date DESC");
        criteria.createCriteria().andDateBetween(_searchHistoricalMeasure.getStart(), _searchHistoricalMeasure.getEnd());
        for (KpiKey kpiKey : _searchHistoricalMeasure.getKpiKeys()) {
            MeasureHistoricalResultDto historic = new MeasureHistoricalResultDto(kpiKey);
            historic.setMeasure(measureHistoryService.getHistory(kpiKey, criteria));
            historicalMeasures.add(historic);
        }
        
        return historicalMeasures;
    }
    
    private List<MeasureHistoricalResultDto> findNLastHistoricalsMeasures(final SearchHistoricalMeasuresDto _searchHistoricalMeasure) {
    
        if (_searchHistoricalMeasure.getNumber() < 0) {
            throw new IllegalArgumentException("Asked numbers of measures must be positive");
        }
        // TODO Auto-generated findNLastHistoricalsMeasures
        throw new UnsupportedOperationException("not yet implemented");
    }
    
    /**
     * This method return the historical measure for a set of entities and for a group of kpi types between two dates
     * 
     * @param _searchLastMeasure
     *            contiain a set of entities a group of kpi types, the stating and the end date or by number id start and end are null
     * @return the historical measures for this entities
     */
    @RequestMapping(method = RequestMethod.POST, value = "/historical")
    @ResponseBody
    public List<MeasureHistoricalResultDto> historicalMeasures(@RequestBody final SearchHistoricalMeasuresDto _searchHistoricalMeasure) {
    
        LOGGER.debug("call rest method /measures/historical/");
        if (_searchHistoricalMeasure.getStart() != null && _searchHistoricalMeasure.getEnd() != null) {
            
            return findHistoricalsMeasuresByDate(_searchHistoricalMeasure);
        } else {
            return findNLastHistoricalsMeasures(_searchHistoricalMeasure);
        }
    }
    /**
     * This method return the last measure for a set of entities and for a group of kpi types
     * 
     * @param _kpiKeys
     *            contiain a set of entities and a group of kpi types
     * @return the last measures for this entities
     * @throws KPINotFoundException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/lastList")
    @ResponseBody
    public List<MeasureResultDto> lastMeasures(@RequestBody final List<KpiKey> _kpiKeys) throws KPINotFoundException {
    
        LOGGER.debug("call rest method /measures/last/");
        List<MeasureResultDto> measuresResponse = Lists.newArrayList();
        
        for (KpiKey kpiKey : _kpiKeys) {
            MeasureResultDto measureResult = new MeasureResultDto();
            measureResult.setKpiKey(kpiKey);
            measureResult.setMeasure(measureHistoryService.getLastMeasures(kpiKey));
            measuresResponse.add(measureResult);
        }
        // TODO
        return measuresResponse;
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
    @RequestMapping(method = RequestMethod.POST, value = "/last", produces = "application/json")
    @ResponseBody
    public Double lastMeasuresForEntity(@RequestBody final KpiKey _kpiKey) throws KPINotFoundException {
    
        LOGGER.info("request /measures/last");
        LOGGER.info("kpi key =  {}", _kpiKey.toString());
        double value = measureHistoryService.getKpiDoubleValue(_kpiKey);
        LOGGER.info("value = {}", value);
        return value;
    }
    
}
