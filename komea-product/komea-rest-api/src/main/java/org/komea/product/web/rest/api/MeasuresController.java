
package org.komea.product.web.rest.api;



import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.ManyHistoricalMeasureRequest;
import org.komea.product.service.dto.MeasureEvolutionResult;
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
import com.google.common.collect.Maps;



@Controller
@RequestMapping(value = "/measures")
public class MeasuresController
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasuresController.class);
    
    @Autowired
    private IMeasureService     measureService;
    
    
    
    /**
     * Utilisé pour les bubble charts et les bar charts.
     *
     * @param _request
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/averageHistoric",
            consumes = "application/json; charset=utf-8",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MeasureResult> averageHistoricalMeasure(@RequestBody
    final ManyHistoricalMeasureRequest _request) {
    
    
        LOGGER.trace("REQUEST : averageHistoricalMeasure {}", _request);
        List<MeasureResult> timeSeriesToMeasureResults = Collections.EMPTY_LIST;
        try {
            final List<TimeSerieDTO> timeSerieDTOs =
                    measureService.findMultipleHistoricalMeasure(_request.getKpiKeyList(),
                            _request.getPeriod());
            timeSeriesToMeasureResults = TimeSerieDTO.timeSeriesToMeasureResults(timeSerieDTOs);

        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.trace("RESPONSE : averageHistoricalMeasure {}", timeSeriesToMeasureResults);
        return timeSeriesToMeasureResults;
    }
    
    
    /**
     * Utilisé pour les tableaux.
     *
     * @param _request
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/averageHistoricEvolution",
            consumes = "application/json; charset=utf-8",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MeasureEvolutionResult> averageHistoricalWithEvolution(@RequestBody
    final ManyHistoricalMeasureRequest _request) {
    
    
        LOGGER.trace("REQUEST : averageHistoricalWithEvolution {}", _request);
        final List<MeasureEvolutionResult> measureEvolutionResults = Lists.newArrayList();
        try {
            final List<MeasureResult> averageHistoricalMeasure = averageHistoricalMeasure(_request);
            final List<TimeSerieDTO> oldTimeSerieDTOs =
                    measureService.findMultipleHistoricalMeasure(_request.getKpiKeyList(), _request
                            .getPeriod().previous());
            final Map<KpiKey, Double> oldValues = Maps.newHashMap();
            for (final TimeSerieDTO oldTimeSerieDTO : oldTimeSerieDTOs) {
                oldValues.put(KpiKey.ofKpiAndEntity(oldTimeSerieDTO.getKpi(),
                        oldTimeSerieDTO.getEntity()), oldTimeSerieDTO.getGroupFormulaValue());
            }
            
            for (final MeasureResult measureResult : averageHistoricalMeasure) {
                final Double oldValue =
                        oldValues.get(KpiKey.ofKpiAndEntity(measureResult.getKpi(),
                                measureResult.getEntity()));
                measureEvolutionResults.add(new MeasureEvolutionResult(measureResult, oldValue));
            }
        } catch (final Throwable e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.trace("RESPONSE : averageHistoricalWithEvolution {}", measureEvolutionResults);
        return measureEvolutionResults;
    }
    
    
    /**
     * Utilisée pour la génération des timecharts
     *
     * @param _request
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/historic",
            consumes = "application/json; charset=utf-8",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<TimeSerieDTO> findHistoricalMeasure(@RequestBody
    final ManyHistoricalMeasureRequest _request) {
    
    
        LOGGER.trace("REQUEST : findHistoricalMeasure {}", _request);
        final List<TimeSerieDTO> timeSerieDTOs = Collections.EMPTY_LIST;
        try {
            
            
            measureService.findMultipleHistoricalMeasure(_request.getKpiKeyList(),
                    _request.getPeriod());
            LOGGER.debug("findHistoricalMeasure with params : {}\nResults : {}", _request,
                    timeSerieDTOs);
            
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.trace("RESPONSE : findHistoricalMeasure {}", timeSerieDTOs);
        return timeSerieDTOs;
    }
}
