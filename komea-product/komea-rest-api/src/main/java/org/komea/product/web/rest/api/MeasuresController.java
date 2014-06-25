package org.komea.product.web.rest.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

@Controller
@RequestMapping(value = "/measures")
public class MeasuresController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasuresController.class);

    @Autowired
    private IMeasureService measureService;

    @RequestMapping(method = RequestMethod.POST, value = "/historic", consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<TimeSerieDTO> findHistoricalMeasure(@RequestBody final ManyHistoricalMeasureRequest _request) {

        final List<TimeSerieDTO> timeSerieDTOs = measureService.findMultipleHistoricalMeasure(
                _request.getKpiKeyList(), _request.getPeriod());
        LOGGER.debug("findHistoricalMeasure with params : {}\nResults : {}", _request, timeSerieDTOs);
        return timeSerieDTOs;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/averageHistoric", consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MeasureResult> averageHistoricalMeasure(@RequestBody final ManyHistoricalMeasureRequest _request) {
        final List<TimeSerieDTO> timeSerieDTOs = measureService.findMultipleHistoricalMeasure(
                _request.getKpiKeyList(), _request.getPeriod());
        return TimeSerieDTO.timeSeriesToMeasureResults(timeSerieDTOs);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/averageHistoricEvolution", consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MeasureEvolutionResult> averageHistoricalWithEvolution(@RequestBody final ManyHistoricalMeasureRequest _request) {
        final List<MeasureResult> averageHistoricalMeasure = averageHistoricalMeasure(_request);
        final List<TimeSerieDTO> oldTimeSerieDTOs = measureService.findMultipleHistoricalMeasure(
                _request.getKpiKeyList(), _request.getPeriod().previous());
        final Map<KpiKey, Double> oldValues = Maps.newHashMap();
        for (final TimeSerieDTO oldTimeSerieDTO : oldTimeSerieDTOs) {
            oldValues.put(KpiKey.ofKpiAndEntity(oldTimeSerieDTO.getKpi(), oldTimeSerieDTO.getEntity()),
                    oldTimeSerieDTO.getGroupFormulaValue());
        }
        final List<MeasureEvolutionResult> measureEvolutionResults = Lists.newArrayList();
        for (final MeasureResult measureResult : averageHistoricalMeasure) {
            final Double oldValue = oldValues.get(KpiKey.ofKpiAndEntity(measureResult.getKpi(), measureResult.getEntity()));
            measureEvolutionResults.add(new MeasureEvolutionResult(measureResult, oldValue));
        }
        return measureEvolutionResults;
    }
}
