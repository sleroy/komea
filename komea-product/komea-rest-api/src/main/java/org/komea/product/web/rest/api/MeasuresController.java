
package org.komea.product.web.rest.api;


import javax.validation.Valid;

import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKpiAPI;
import org.komea.product.database.dto.MeasuresDto;
import org.komea.product.database.dto.SearchMeasuresDto;
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
    
        return null;
        
        //
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
    
        //
        return null;
        
    }
    
}
