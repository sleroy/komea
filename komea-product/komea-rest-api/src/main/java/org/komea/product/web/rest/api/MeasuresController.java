package org.komea.product.web.rest.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.model.timeserie.dto.TimeCoordinateDTO;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.KpiStringKeyList;
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

    /**
     * This method get the current measure for a kpi type on an entity
     *
     * @param _kpiKeys
     * @return the last measure value
     * @throws KPINotFoundException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/current", produces = "application/json")
    @ResponseBody
    public List<MeasureResult> currentMeasures(@Valid
            @RequestBody
            final KpiStringKeyList _kpiKeys) throws KPINotFoundException {

        LOGGER.debug("currentMeasures: {}", _kpiKeys);
        final List<MeasureResult> results = measureService.currentMeasures(_kpiKeys);
        for (final MeasureResult measureResult : results) {
            if (measureResult.getValue() == null) {
                measureResult.setValue(measureService.lastMeasure(
                        measureResult.getKpi(), measureResult.getEntity()));
            }
        }
        return results;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/historic",
            consumes = "application/json; charset=utf-8",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<TimeSerieDTO> findHistoricalMeasure(@RequestBody
            final ManyHistoricalMeasureRequest _request) {

        final List<TimeSerieDTO> timeSerieDTOs = measureService.findMultipleHistoricalMeasure(
                _request.getKpiKeyList(), _request.getPeriod());
        LOGGER.debug("findHistoricalMeasure with params : {}\nResults : {}", _request, timeSerieDTOs);
        return timeSerieDTOs;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/averageHistoric",
            consumes = "application/json; charset=utf-8",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MeasureResult> averageHistoricalMeasure(@RequestBody
            final ManyHistoricalMeasureRequest _request) {

        final List<TimeSerieDTO> timeSerieDTOs = measureService.findMultipleHistoricalMeasure(
                _request.getKpiKeyList(), _request.getPeriod());
        final List<MeasureResult> measureResults = Lists.newArrayList();
        final boolean addCurrentValues = _request.getPeriod().getEndDate().after(new Date());
        for (final TimeSerieDTO timeSerieDTO : timeSerieDTOs) {
            if (addCurrentValues) {
                final Double value = measureService.currentMeasure(
                        timeSerieDTO.getKpi(), timeSerieDTO.getEntity());
                timeSerieDTO.addCoordinate(new TimeCoordinateDTO(new Date(), value));
            }
            measureResults.add(timeSerieDTO.toMeasureResult());
        }
        return measureResults;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/averageHistoricEvolution",
            consumes = "application/json; charset=utf-8",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<MeasureEvolutionResult> averageHistoricalWithEvolution(@RequestBody
            final ManyHistoricalMeasureRequest _request) {
        final List<MeasureResult> averageHistoricalMeasure = averageHistoricalMeasure(_request);
        final List<TimeSerieDTO> oldTimeSerieDTOs = measureService.findMultipleHistoricalMeasure(
                _request.getKpiKeyList(), _request.getPeriod().previous());
        final Map<KpiKey, Double> oldValues = Maps.newHashMap();
        for (final TimeSerieDTO oldTimeSerieDTO : oldTimeSerieDTOs) {
            oldValues.put(KpiKey.ofKpiAndEntity(oldTimeSerieDTO.getKpi(),
                    oldTimeSerieDTO.getEntity()), oldTimeSerieDTO.getGroupFormulaValue());
        }
        final List<MeasureEvolutionResult> measureEvolutionResults = Lists.newArrayList();
        for (final MeasureResult measureResult : averageHistoricalMeasure) {
            final Double oldValue = oldValues.get(
                    KpiKey.ofKpiAndEntity(measureResult.getKpi(), measureResult.getEntity()));
            measureEvolutionResults.add(new MeasureEvolutionResult(measureResult, oldValue));
        }
        return measureEvolutionResults;
    }

    /**
     * This method get the last measure for a kpi type on an entity
     *
     * @param _kpiKeys
     * @return the last measure value
     * @throws KPINotFoundException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/last", produces = "application/json")
    @ResponseBody
    public List<MeasureResult> lastMeasures(@Valid
            @RequestBody
            final KpiStringKeyList _kpiKeys) throws KPINotFoundException {

        LOGGER.debug("lastMeasures: {}", _kpiKeys);
        return measureService.lastMeasures(_kpiKeys);

    }

    /**
     * Get current measure and last value in history for each pair of entity/kpi
     *
     * @param _kpiKeys kpiStringKeyList
     * @return measures with last values in history
     */
    @RequestMapping(method = RequestMethod.POST, value = "/evolutions", produces = "application/json")
    @ResponseBody
    public List<MeasureEvolutionResult> measuresWithEvolution(@Valid
            @RequestBody final KpiStringKeyList _kpiKeys) {
        return measureService.measuresWithEvolution(_kpiKeys);
    }
}
