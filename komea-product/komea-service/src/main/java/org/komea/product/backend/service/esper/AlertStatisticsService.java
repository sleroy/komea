/**
 *
 */

package org.komea.product.backend.service.esper;



import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.service.ISystemProjectBean;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.demodata.AlertJobDemo;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.AlertTypeStatistic;
import org.komea.product.service.dto.KpiKey;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espertech.esper.client.EPStatement;



/**
 * This service provides informations about the informations received by esper
 * (number of alerts received per days, and alert type breakdown);
 * 
 * @author sleroy
 */
@Service
public class AlertStatisticsService implements IEventStatisticsService
{
    
    
    public static final String     ALERT_RECEIVED_IN_ONE_DAY = "ALERT_RECEIVED_IN_ONE_DAY";
    public static final String     ALERT_CRITICITY_DAY       = "ALERT_CRITICITY_DAY_";
    
    public static final String     STATS_BREAKDOWN_24H       = "STATS_BREAKDOWN_24H";
    
    private static final Logger    LOGGER                    =
                                                                     LoggerFactory
                                                                             .getLogger(AlertStatisticsService.class);
    
    @Autowired
    private IKPIService            kpiService;
    
    @Autowired
    private ISystemProjectBean     systemProject;
    
    
    @Autowired
    private IEsperEngine           esperEngine;
    
    @Autowired
    private ProviderDao            providerDAO;
    
    @Autowired
    private ICronRegistryService   registry;
    
    @Autowired
    private IEventPushService      eventPushService;
    
    
    @Autowired
    private IHistoryService measureHistoryService;
    
    
    
    /**
     *
     */
    public AlertStatisticsService() {
    
    
        super();
    }
    
    
    /**
     * @return the eventPushService
     */
    public IEventPushService getAlertPushService() {
    
    
        return eventPushService;
    }
    
    
    @Override
    public List<Measure> getAllMeasures() {
    
    
        final List<Measure> history =
                measureHistoryService.getHistory(HistoryKey.of(kpiService.findKPI(
                        KpiKey.ofKpiName(ALERT_RECEIVED_IN_ONE_DAY)).getId()));
        LOGGER.info("History of alerts {}", history.size());
        return history;
        
    }
    
    
    /**
     * @return the esperEngine
     */
    public final IEsperEngine getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public IKPIService getKpiService() {
    
    
        return kpiService;
    }
    
    
    @Override
    public long getNumberOfAlerts(final Severity _criticity) {
    
    
        return kpiService.getKpiSingleValue(
                KpiKey.ofKpiNameAndEntity(ALERT_CRITICITY_DAY + _criticity.name().toUpperCase(),
                        systemProject.getSystemProject())).intValue();
        
    }
    
    
    public ProviderDao getProviderDAO() {
    
    
        return providerDAO;
    }
    
    
    @Override
    public long getReceivedAlertsIn24LastHours() {
    
    
        return kpiService.getKpiSingleValue(
                KpiKey.ofKpiNameAndEntity(ALERT_RECEIVED_IN_ONE_DAY, systemProject.getSystemProject()))
                .intValue();
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.esper.IEventStatisticsService#getReceivedAlertTypesIn24Hours()
     */
    @Override
    public List<AlertTypeStatistic> getReceivedAlertTypesIn24LastHours() {
    
    
        final EPStatement statsBreakdownStatement = esperEngine.getStatement(STATS_BREAKDOWN_24H);
        return EPStatementResult.build(statsBreakdownStatement).mapAPojoPerResultLine(
                AlertTypeStatistic.class);
    }
    
    
    public ICronRegistryService getRegistry() {
    
    
        return registry;
    }
    
    
    /**
     * @return the systemProject
     */
    public final ISystemProjectBean getSystemProject() {
    
    
        return systemProject;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Creating System KPI for statistics...");
        final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityType(ALERT_RECEIVED_IN_ONE_DAY, EntityType.PERSON);
        final Kpi kpi = kpiService.findKPI(kpiKey);
        Kpi alertPerDay = null;
        if (kpi == null) {
            alertPerDay = alertPerDay();
            kpiService.saveOrUpdate(alertPerDay);
            kpiService.saveOrUpdate(alertCriticityPerDay(Severity.BLOCKER));
            kpiService.saveOrUpdate(alertCriticityPerDay(Severity.CRITICAL));
            kpiService.saveOrUpdate(alertCriticityPerDay(Severity.MAJOR));
            kpiService.saveOrUpdate(alertCriticityPerDay(Severity.MINOR));
            kpiService.saveOrUpdate(alertCriticityPerDay(Severity.INFO));
            
            
            LOGGER.info("Statistics KPI already existing.");
        }
        
        
        // output snapshot every 1 minute
        esperEngine
                .createOrUpdateEPLQuery(new QueryDefinition(
                        "SELECT DISTINCT provider.name as provider, eventType.eventKey as type, count(*) as number FROM Event.win:time(24 hour)  GROUP BY provider.name, eventType.name ORDER BY provider.name ASC, eventType.name ASC",
                        STATS_BREAKDOWN_24H));
        
        scheduleAlerts();
        if (alertPerDay != null) {
            kpiService.storeValueInHistory(kpiKey);
        }
        
    }
    
    
    public void scheduleAlerts() {
    
    
        final JobDataMap properties = new JobDataMap();
        properties.put("esper", eventPushService);
        registry.registerCronTask("ALERT_DEMO_STAT", "0/10 * * * * ?", AlertJobDemo.class,
                properties);
        
    }
    
    
    /**
     * @param _eventPushService
     *            the eventPushService to set
     */
    public void setAlertPushService(final IEventPushService _eventPushService) {
    
    
        eventPushService = _eventPushService;
    }
    
    
    /**
     * @param _esperEngine
     *            the esperEngine to set
     */
    public final void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    /**
     * @param _kpiService
     *            the kpiService to set
     */
    public final void setKpiService(final IKPIService _kpiService) {
    
    
        kpiService = _kpiService;
    }
    
    
    public void setProviderDAO(final ProviderDao _providerDAO) {
    
    
        providerDAO = _providerDAO;
    }
    
    
    public void setRegistry(final ICronRegistryService _registry) {
    
    
        registry = _registry;
    }
    
    
    /**
     * @param _systemProject
     *            the systemProject to set
     */
    public final void setSystemProject(final ISystemProjectBean _systemProject) {
    
    
        systemProject = _systemProject;
    }
    
    
    private Kpi alertCriticityPerDay(final Severity _criticity) {
    
    
        Kpi kpi;
        kpi = new Kpi();
        kpi.setDescription("Provides the number of alerts of criticity "
                + _criticity + " received under 24 hours");
        kpi.setEntityType(EntityType.PROJECT);
        kpi.setEsperRequest("SELECT COUNT(*) as alert_number FROM Event.win:time(24 hour) WHERE eventType.severity=Severity."
                + _criticity.name());
        kpi.setKpiKey(ALERT_CRITICITY_DAY + _criticity.name());
        kpi.setValueMin(0d);
        kpi.setValueMax(Double.MAX_VALUE);
        kpi.setName("Number of alerts of criticity " + _criticity + " received under 24 hours.");
        kpi.setEntityID(systemProject.getSystemProject().getId());
        kpi.setCronExpression("0 * * * * ?");
        kpi.setEvictionRate(1);
        kpi.setEvictionType(EvictionType.DAYS);
        kpi.setValueDirection(ValueDirection.WORST);
        kpi.setValueType(ValueType.INT);
        return kpi;
    }
    
    
    private Kpi alertPerDay() {
    
    
        Kpi kpi;
        kpi = new Kpi();
        kpi.setDescription("Provides the number of alerts received under 24 hours");
        kpi.setEntityType(EntityType.PROJECT);
        kpi.setEsperRequest("SELECT COUNT(*) as alert_number FROM Event.win:time(24 hour)");
        kpi.setKpiKey(ALERT_RECEIVED_IN_ONE_DAY);
        kpi.setValueMin(0d);
        kpi.setValueMax(Double.MAX_VALUE);
        kpi.setName("Number of alerts received under 24 hours.");
        kpi.setEntityID(systemProject.getSystemProject().getId());
        kpi.setCronExpression("0 * * * * ?");
        kpi.setEvictionRate(1);
        kpi.setEvictionType(EvictionType.DAYS);
        kpi.setValueDirection(ValueDirection.WORST);
        kpi.setValueType(ValueType.INT);
        return kpi;
    }
}
