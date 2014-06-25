
package org.komea.product.web.rest.api;



import java.util.Date;

import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.TimeSerie;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.service.dto.EntityStringKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value = "/history/")
public class HistoryController
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryController.class);
    
    @Autowired
    private IEntityService      entityService;


    @Autowired
    private IKPIService         kpiService;


    @Autowired
    private IStatisticsAPI      statisticsAPI;



    @RequestMapping(
            method = RequestMethod.GET,
            value = "/dayValue/{kpiName}",
            produces = "application/json")
    @ResponseBody
    public KpiResult currentValues(@PathVariable
    final String kpiName) {
    
    
        LOGGER.info("Current values requested for {} ", kpiName);
        final Kpi kpi = kpiService.selectByKeyOrFail(kpiName);
        final TimeSerieOptions options = new TimeSerieOptions(kpi);
        options.setTimeScale(TimeScale.PER_DAY);
        return statisticsAPI.evaluateKpiValues(options);
    }
    
    
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/interval/{entityID}/{kpiName}/{fromDate}/{toDate}",
            produces = "application/json")
    @ResponseBody
    public TimeSerie intervalTimeSerie(@PathVariable
    final String entityID, @PathVariable
    final String kpiName, @PathVariable
    final Date fromDate, @PathVariable
    final Date toDate) {
    
    
        LOGGER.info("Whole history requested for entity={} kpi={} from={} to={}", entityID,
                kpiName, fromDate, toDate);
        final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions();
        final Kpi kpi = kpiService.selectByKeyOrFail(kpiName);
        final IEntity byEntityStringKey =
                entityService.findEntityByEntityStringKey(EntityStringKey.of(kpi.getEntityType(),
                        entityID));
        if (byEntityStringKey == null) {
            LOGGER.error("Entity not found {} wit entityType {}", entityID, kpi.getEntityType());
            return null;
        }
        timeSerieOptions.setKpi(kpi);
        timeSerieOptions.setGroupFormula(GroupFormula.AVG_VALUE);
        timeSerieOptions.setFromPeriod(new DateTime(fromDate));
        timeSerieOptions.setToPeriod(new DateTime(toDate));
        timeSerieOptions.pickBestGranularity();
        return statisticsAPI.buildTimeSeries(timeSerieOptions, byEntityStringKey.getEntityKey());
    }


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/pastValues/{kpiName}/{fromDate}/{toDate}",
            produces = "application/json")
    @ResponseBody
    public KpiResult pastValues(@PathVariable
    final String kpiName, final Date fromDate, final Date toDate) {
    
    
        LOGGER.info("Current values requested for {} ", kpiName);
        final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions();
        final Kpi kpi = kpiService.selectByKeyOrFail(kpiName);
        timeSerieOptions.setKpi(kpi);
        timeSerieOptions.setFromPeriod(new DateTime(fromDate));
        timeSerieOptions.setToPeriod(new DateTime(toDate));
        timeSerieOptions.setGroupFormula(GroupFormula.AVG_VALUE);
        timeSerieOptions.pickBestGranularity();
        return statisticsAPI.evaluateKpiValuesOnPeriod(timeSerieOptions);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/period/{entityID}/{kpiName}/{date}",
            produces = "application/json")
    @ResponseBody
    public TimeSerie periodTimeSerie(@PathVariable
    final String entityID, @PathVariable
    final String kpiName, @PathVariable
    final Date date) {
    
    
        LOGGER.info("Whole history requested for entity={} kpiName={} date={}", entityID, kpiName,
                date);
        final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions();
        final Kpi kpi = kpiService.selectByKeyOrFail(kpiName);
        final IEntity byEntityStringKey =
                entityService.findEntityByEntityStringKey(EntityStringKey.of(kpi.getEntityType(),
                        entityID));
        if (byEntityStringKey == null) {
            LOGGER.error("Entity not found {} with entityType {}", entityID, kpi.getEntityType());
            return null;
        }
        timeSerieOptions.setKpi(kpi);
        timeSerieOptions.setGroupFormula(GroupFormula.AVG_VALUE);
        timeSerieOptions.setFromPeriod(new DateTime(date));
        timeSerieOptions.untilNow();
        timeSerieOptions.pickBestGranularity();
        return statisticsAPI.buildTimeSeries(timeSerieOptions, byEntityStringKey.getEntityKey());
    }
    
    
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/whole/{entityID}/{kpiName}",
            produces = "application/json")
    @ResponseBody
    public TimeSerie wholeTimeSerie(@PathVariable
    final String entityID, @PathVariable
    final String kpiName) {
    
    
        LOGGER.info("Whole history requested for {} {} {}", entityID, kpiName);
        final TimeSerieOptions timeSerieOptions = new TimeSerieOptions();
        final Kpi kpi = kpiService.selectByKeyOrFail(kpiName);
        final IEntity byEntityStringKey =
                entityService.findEntityByEntityStringKey(EntityStringKey.of(kpi.getEntityType(),
                        entityID));
        if (byEntityStringKey == null) {
            LOGGER.error("Entity not found {} wit entityType {}", entityID, kpi.getEntityType());
            return null;
        }
        timeSerieOptions.setKpi(kpi);
        timeSerieOptions.setGroupFormula(GroupFormula.AVG_VALUE);
        timeSerieOptions.setTimeScale(TimeScale.PER_WEEK);
        return statisticsAPI.buildTimeSeries(timeSerieOptions, byEntityStringKey.getEntityKey());
    }
}
