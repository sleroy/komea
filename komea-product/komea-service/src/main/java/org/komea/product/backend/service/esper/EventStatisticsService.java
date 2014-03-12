/**
 *
 */

package org.komea.product.backend.service.esper;



import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.service.ISystemProjectBean;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.enums.EvictionType;
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



/**
 * This service provides informations about the informations received by esper
 * (number of alerts received per days, and alert type breakdown);
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Service
public class EventStatisticsService implements IEventStatisticsService
{
    
    
    public static final String  ALERT_CRITICITY_DAY       = "ALERT_CRITICITY_DAY_";
    public static final String  ALERT_RECEIVED_IN_ONE_DAY = "ALERT_RECEIVED_IN_ONE_DAY";
    
    public static final String  STATS_BREAKDOWN_24H       = "STATS_BREAKDOWN_24H";
    
    private static final Logger LOGGER                    =
                                                                  LoggerFactory
                                                                          .getLogger("event-statistic-system");
    
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
                kpiService.getSingleValue(KpiKey.ofKpiName(getKpiNameFromSeverity(_criticity)));
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
    
    
        final ICEPQuery statsBreakdownStatement = cepEngine.getStatement(STATS_BREAKDOWN_24H);
        return statsBreakdownStatement.getResult().asType();
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
        cepEngine
                .createOrUpdateEPLQuery(new QueryDefinition(
                        "SELECT DISTINCT provider.name as provider, eventType.eventKey as type, count(*) as number FROM Event.win:time(24 hour)  GROUP BY provider.name, eventType.name ORDER BY provider.name ASC, eventType.name ASC",
                        STATS_BREAKDOWN_24H));
        
        
        if (alertPerDay != null) {
            kpiService.storeValueInHistory(kpiKey);
        }
        
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
        kpi.setEsperRequest("SELECT COUNT(*) as alert_number FROM Event(eventType.severity=Severity."
                + _criticity.name() + ")" + ".win:time(24 hour)");
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
        kpi.setEsperRequest("SELECT COUNT(*) as alert_number FROM Event.win:time(24 hour)");
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
