package org.komea.product.web.rest.api;

import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.Valid;
import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.measure.IMeasureService;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.MeasuresDto;
import org.komea.product.database.dto.SearchHistoricalMeasuresDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.EntityTypeExtended;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
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

@Controller
@RequestMapping(value = "/measures")
public class MeasuresController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasuresController.class);

    @Autowired
    private IKPIService measureHistoryService;

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IMeasureService measureService;

    @Autowired
    private IKPIService kpiService;

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

        List<MeasureHistoricalResultDto> historicalMeasures = Lists.newArrayList();
        for (KpiKey kpiKey : _searchHistoricalMeasure.getKpiKeys()) {
            MeasureHistoricalResultDto historic = new MeasureHistoricalResultDto(kpiKey);
            historic.setMeasure(measureHistoryService.getHistory(kpiKey, _searchHistoricalMeasure.getNumber()));
            historicalMeasures.add(historic);
        }

        return historicalMeasures;
    }

    /**
     * This method return the historical measure for a set of entities and for a
     * group of kpi types between two dates
     *
     * @param _searchLastMeasure contiain a set of entities a group of kpi
     * types, the stating and the end date or by number id start and end are
     * null
     * @return the historical measures for this entities
     */
    @RequestMapping(method = RequestMethod.POST, value = "/historical")
    @ResponseBody
    public List<MeasureHistoricalResultDto> historicalMeasures(
            @Valid @RequestBody final SearchHistoricalMeasuresDto _searchHistoricalMeasure) {

        LOGGER.debug("call rest method /measures/historical/");
        if (_searchHistoricalMeasure.getStart() != null && _searchHistoricalMeasure.getEnd() != null) {

            return findHistoricalsMeasuresByDate(_searchHistoricalMeasure);
        } else {
            return findNLastHistoricalsMeasures(_searchHistoricalMeasure);
        }
    }

    /**
     * This method return the last measure for a set of entities and for a group
     * of kpi types
     *
     * @param _kpiKeys contiain a set of entities and a group of kpi types
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
            measureResult.setMeasure(measureHistoryService.getKpiMeasureValue(kpiKey));
            measuresResponse.add(measureResult);
        }
        return measuresResponse;
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
        double value = measureHistoryService.getKpiDoubleValue(_kpiKey);
        LOGGER.info("value = {}", value);
        return value;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public MeasuresDto findMeasures(@RequestBody final SearchMeasuresDto _searchMeasuresDto) {
        System.out.println("findMeasures : " + _searchMeasuresDto.toString());
        final EntityTypeExtended entityTypeExtended = _searchMeasuresDto.getEntityTypeExtended();
        final List<Kpi> kpis = kpiService.getKpis(entityTypeExtended, _searchMeasuresDto.getKpiKeys());
        System.out.println("kpis : " + kpis.toString());
        final List<BaseEntity> entities = entityService.getEntities(entityTypeExtended, _searchMeasuresDto.getEntityKeys());
        System.out.println("entities : " + entities.toString());
        final List<Measure> measures = measureService.getMeasures(kpis, entities, _searchMeasuresDto);
        System.out.println("measures : " + measures.toString());
        return new MeasuresDto(entityTypeExtended, entities, kpis, measures);
    }

}
