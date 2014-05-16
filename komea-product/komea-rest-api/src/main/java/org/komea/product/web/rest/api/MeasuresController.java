
package org.komea.product.web.rest.api;


import java.util.List;

import javax.validation.Valid;

import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.ManyHistoricalMeasureRequest;
import org.komea.product.service.dto.ManyHistoricalMeasureRequestTemp;
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
    private IStatisticsAPI      statService;
    
    @Autowired
    private IMeasureService     measureService;
    
    @RequestMapping(method = RequestMethod.POST, value = "/historic", consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<TimeSerieDTO> findHistoricalMeasure(@RequestBody final ManyHistoricalMeasureRequest _request) {
    
        return measureService.findMupltipleHistoricalMeasure(_request.getKpiKeyList(), _request.getPeriod());
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/historic-temp", consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<TimeSerieDTO> findHistoricalMeasureTemps(@RequestBody final ManyHistoricalMeasureRequestTemp _request) {
    
        return Lists.newArrayList();
    }
    
    /**
     * This method get the current measure for a kpi type on an entity
     *
     * @param _kpiKey
     *            the kpi type
     * @return the last measure value
     * @throws KPINotFoundException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/current", produces = "application/json")
    @ResponseBody
    public List<MeasureResult> currentMeasures(@Valid @RequestBody final KpiStringKeyList _kpiKeys) throws KPINotFoundException {
    
        return measureService.currentMeasures(_kpiKeys);
        
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
    public List<MeasureResult> lastMeasures(@Valid @RequestBody final KpiStringKeyList _kpiKeys) throws KPINotFoundException {
    
        return Lists.newArrayList();
        
    }
}
