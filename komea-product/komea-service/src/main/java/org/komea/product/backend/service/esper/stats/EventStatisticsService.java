/**
 *
 */

package org.komea.product.backend.service.esper.stats;



import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.formula.tuple.EventCountFormula;
import org.komea.eventory.formula.tuple.GroupByFormula;
import org.komea.eventory.query.CEPQueryImplementation;
import org.komea.eventory.query.FilterDefinition;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.service.ISystemProjectBean;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.esper.IEventStatisticsService;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.cep.tuples.ProviderEventTypeTupleCreator;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.EventTypeStatistic;
import org.komea.product.service.dto.KpiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This service provides informations about the informations received by esper
 * (number of alerts received per days, and alert type breakdown);
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Service
@Transactional
public class EventStatisticsService implements IEventStatisticsService
{
    
    
    private static final String ALERT_CRITICITY_DAY       = "ALERT_CRITICITY_DAY_";
    
    private static final String ALERT_RECEIVED_IN_ONE_DAY = "ALERT_RECEIVED_IN_ONE_DAY";
    
    private static final Logger LOGGER                    =
                                                                  LoggerFactory
                                                                          .getLogger("event-statistic-system");
    
    private static final String STATS_BREAKDOWN_24H       = "STATS_BREAKDOWN_24H";
    
    @Autowired
    private IEventEngineService cepEngine;
    
    @Autowired
    private IEventPushService   eventPushService;
    
    @Autowired
    private IKPIService         kpiService;
    
    @Autowired
    private IHistoryService     measureHistoryService;
    
    @Autowired
    private ProviderDao         providerDAO;
    
    @Autowired
    private ISystemProjectBean  systemProject;
    
    
    
    /**
     * Builds the Spring EL expression to call the KPI.F
     * 
     * @param _criticity
     *            test the severity
     * @return
     */
    public String buildELForAlertCriticityKpi(final Severity _criticity) {
    
    
        return "new "
                + AlertPerSeverityPerDay.class.getName() + "(T(" + Severity.class.getName() + ")."
                + _criticity + ")";
    }
    
    
    /**
     * Builds the query definition to compute statistics on provider and event
     * type frequency.
     * 
     * @return
     */
    public CEPQueryImplementation buildProviderEventFrequencyQuery() {
    
    
        final CEPQueryImplementation cepQueryDefinition = new CEPQueryImplementation();
        cepQueryDefinition.setParameters(Collections.<String, Object> emptyMap());
        cepQueryDefinition.addFilterDefinition(FilterDefinition.create().setCacheConfiguration(
                CacheConfigurationBuilder.expirationTimeCache(24, TimeUnit.HOURS)));
        cepQueryDefinition.setFormula(new GroupByFormula(new ProviderEventTypeTupleCreator(),
                new EventCountFormula()));
        
        return cepQueryDefinition;
    }
    
    
    /**
     * @return the eventPushService
     */
    public IEventPushService getAlertPushService() {
    
    
        return eventPushService;
    }
    
    
    /**
     * Method getAllMeasures.
     * 
     * @return List<Measure>
     * @see org.komea.product.backend.service.esper.IEventStatisticsService#getAllMeasures()
     */
    @Override
    public List<Measure> getAllMeasures() {
    
    
        final List<Measure> history =
                measureHistoryService.getHistory(HistoryKey.of(kpiService.findKPI(
                        KpiKey.ofKpiName(ALERT_RECEIVED_IN_ONE_DAY)).getId()));
        LOGGER.debug("History of alerts {}", history.size());
        return history;
        
    }
    
    
    /**
     * @return the cepEngine
     */
    public final IEventEngineService getEsperEngine() {
    
    
        return cepEngine;
    }
    
    
    /**
     * Method getKpiService.
     * 
     * @return IKPIService
     */
    public IKPIService getKpiService() {
    
    
        return kpiService;
    }
    
    
    /**
     * Method getNumberOfAlerts.
     * 
     * @param _criticity
     *            Severity
     * @return long
     * @see org.komea.product.backend.service.esper.IEventStatisticsService#getNumberOfAlerts(Severity)
     */
    @Override
    public long getNumberOfAlerts(final Severity _criticity) {
    
    
        final Double kpiSingleValue =
                kpiService.getSingleValue(KpiKey.ofKpiName(getKpiNameFromSeverity(_criticity)))
                        .doubleValue();
        return kpiSingleValue.intValue();
        
    }
    
    
    /**
     * Method getProviderDAO.
     * 
     * @return ProviderDao
     */
    public ProviderDao getProviderDAO() {
    
    
        return providerDAO;
    }
    
    
    /**
     * Method getReceivedAlertsIn24LastHours.
     * 
     * @return long
     * @see org.komea.product.backend.service.esper.IEventStatisticsService#getReceivedAlertsIn24LastHours()
     */
    @Override
    public long getReceivedAlertsIn24LastHours() {
    
    
        return kpiService.getSingleValue(KpiKey.ofKpiName(ALERT_RECEIVED_IN_ONE_DAY)).intValue();
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.esper.IEventStatisticsService#getReceivedAlertTypesIn24Hours()
     */
    /**
     * Method getReceivedAlertTypesIn24LastHours.
     * 
     * @return List<EventTypeStatistic>
     * @see org.komea.product.backend.service.esper.IEventStatisticsService#getReceivedAlertTypesIn24LastHours()
     */
    @Override
    public List<EventTypeStatistic> getReceivedAlertTypesIn24LastHours() {
    
    
        final ICEPQuery statsBreakdownStatement = cepEngine.getQuery(STATS_BREAKDOWN_24H);
        return statsBreakdownStatement.getResult().asMap().asPojoRows(new String[]
            {
                    "provider",
                    "type",
                    "value" }, EventTypeStatistic.class);
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
        final KpiKey kpiKey = KpiKey.ofKpiName(ALERT_RECEIVED_IN_ONE_DAY);
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
        LOGGER.info("Creating cron for statistics.");
        
        // output snapshot every 1 minute
        cepEngine.createOrUpdateQuery(new QueryDefinition(STATS_BREAKDOWN_24H,
                buildProviderEventFrequencyQuery()));
        
        
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
     *            the cepEngine to set
     */
    public final void setEsperEngine(final IEventEngineService _esperEngine) {
    
    
        cepEngine = _esperEngine;
    }
    
    
    /**
     * @param _kpiService
     *            the kpiService to set
     */
    public final void setKpiService(final IKPIService _kpiService) {
    
    
        kpiService = _kpiService;
    }
    
    
    /**
     * Method setProviderDAO.
     * 
     * @param _providerDAO
     *            ProviderDao
     */
    public void setProviderDAO(final ProviderDao _providerDAO) {
    
    
        providerDAO = _providerDAO;
    }
    
    
    /**
     * @param _systemProject
     *            the systemProject to set
     */
    public final void setSystemProject(final ISystemProjectBean _systemProject) {
    
    
        systemProject = _systemProject;
    }
    
    
    /**
     * Method alertCriticityPerDay.
     * 
     * @param _criticity
     *            Severity
     * @return Kpi
     */
    private Kpi alertCriticityPerDay(final Severity _criticity) {
    
    
        Kpi kpi;
        kpi = new Kpi();
        kpi.setDescription("Provides the number of alerts of criticity "
                + _criticity + " received under 24 hours");
        kpi.setEntityType(null);
        kpi.setEsperRequest(buildELForAlertCriticityKpi(_criticity));
        kpi.setKpiKey(getKpiNameFromSeverity(_criticity));
        kpi.setValueMin(0d);
        kpi.setValueMax(Double.MAX_VALUE);
        kpi.setName("Number of alerts of criticity " + _criticity + " received under 24 hours.");
        kpi.setEntityID(null);
        kpi.setCronExpression("0 * * * * ?");
        kpi.setEvictionRate(1);
        kpi.setEvictionType(EvictionType.DAYS);
        kpi.setValueDirection(ValueDirection.WORST);
        kpi.setValueType(ValueType.INT);
        kpi.setProviderType(ProviderType.OTHER);
        return kpi;
    }
    
    
    /**
     * Method alertPerDay.
     * 
     * @return Kpi
     */
    private Kpi alertPerDay() {
    
    
        Kpi kpi;
        kpi = new Kpi();
        kpi.setDescription("Provides the number of alerts received under 24 hours");
        kpi.setEntityType(null);
        kpi.setEsperRequest("new " + AlertPerDay.class.getName() + "()");
        kpi.setKpiKey(ALERT_RECEIVED_IN_ONE_DAY);
        kpi.setValueMin(0d);
        kpi.setValueMax(Double.MAX_VALUE);
        kpi.setName("Number of alerts received under 24 hours.");
        kpi.setEntityID(null);
        kpi.setCronExpression("0 * * * * ?");
        kpi.setEvictionRate(1);
        kpi.setEvictionType(EvictionType.DAYS);
        kpi.setValueDirection(ValueDirection.WORST);
        kpi.setValueType(ValueType.INT);
        kpi.setProviderType(ProviderType.OTHER);
        return kpi;
    }
    
    
    /**
     * Method getKpiNameFromSeverity.
     * 
     * @param _criticity
     *            Severity
     * @return String
     */
    private String getKpiNameFromSeverity(final Severity _criticity) {
    
    
        return ALERT_CRITICITY_DAY + _criticity.name();
    }
}
