/**
 *
 */

package org.komea.product.backend.service.esper.stats;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.engine.IQuery;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.query.CEPQueryImplementation;
import org.komea.eventory.query.FilterDefinition;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.criterias.MeasureDateComparator;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.esper.IEventStatisticsService;
import org.komea.product.backend.service.esper.QueryInformations;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.cep.formula.EventCountFormula;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.EventTypeStatistic;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;



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
    
    
    private static final String     ALERT_CRITICITY_DAY       = "ALERT_CRITICITY_DAY_";
    
    private static final String     ALERT_RECEIVED_IN_ONE_DAY = "ALERT_RECEIVED_IN_ONE_DAY";
    
    private static final Logger     LOGGER                    =
                                                                      LoggerFactory
                                                                              .getLogger("event-statistic-system");
    
    /**
     * 
     */
    private static final int        NUMBER_HOURS              = 24;
    
    /**
     * 
     */
    private static final int        PRECISION                 = 5;
    
    private static final String     STATS_BREAKDOWN_24H       = "STATS_BREAKDOWN_24H";
    
    @Autowired
    private IEventEngineService     cepEngine;
    
    @Autowired
    private ICronRegistryService    cronRegistryService;
    
    private Cache<Integer, Measure> historyOfAlerts;
    
    @Autowired
    private ProviderDao             providerDAO;
    
    
    
    /**
     * Method alertCriticityPerDay.
     * 
     * @param _criticity
     *            Severity
     * @return Kpi
     */
    public void alertCriticityPerDay(final Severity _criticity) {
    
    
        cepEngine.createQuery(QueryInformations.directInformations(
                getKpiNameFromSeverity(_criticity), new AlertPerSeverityPerDay(_criticity)));
        
    }
    
    
    /**
     * Method alertPerDay.
     * 
     * @return Kpi
     */
    public void alertPerDay() {
    
    
        cepEngine.createQuery(QueryInformations.directInformations(ALERT_RECEIVED_IN_ONE_DAY,
                new AlertPerDay()));
    }
    
    
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
        cepQueryDefinition.addFilterDefinition(FilterDefinition.create().setCacheConfiguration(
                CacheConfigurationBuilder.expirationTimeCache(24, TimeUnit.HOURS)));
        cepQueryDefinition.setFormula(new ProviderFormula(new EventCountFormula()));
        
        return cepQueryDefinition;
    }
    
    
    /**
     * Method getAllMeasures.
     * 
     * @return List<Measure>
     * @see org.komea.product.backend.service.esper.IEventStatisticsService#getAllMeasures()
     */
    @Override
    public List<Measure> getAllMeasures() {
    
    
        final ArrayList<Measure> measureList = Lists.newArrayList(historyOfAlerts.asMap().values());
        Collections.sort(measureList, new MeasureDateComparator());
        return Collections.unmodifiableList(measureList);
        
    }
    
    
    /**
     * @return the cepEngine
     */
    public final IEventEngineService getEsperEngine() {
    
    
        return cepEngine;
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
    
    
        final IQuery<Number> query =
                cepEngine.getQuery(FormulaID.ofRawID(getKpiNameFromSeverity(_criticity)));
        final Double kpiSingleValue = query.getResult().doubleValue();
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
    
    
        return ((Number) cepEngine.getQuery(FormulaID.ofRawID(ALERT_RECEIVED_IN_ONE_DAY))
                .getResult()).longValue();
        
    }
    
    
    /**
     * Method getReceivedAlertTypesIn24LastHours.
     * 
     * @return List<EventTypeStatistic>
     * @see org.komea.product.backend.service.esper.IEventStatisticsService#getReceivedAlertTypesIn24LastHours()
     */
    @Override
    public List<EventTypeStatistic> getReceivedAlertTypesIn24LastHours() {
    
    
        final ICEPQuery<IEvent, EventTypeStatistics> statsBreakdownStatement =
                (ICEPQuery<IEvent, EventTypeStatistics>) cepEngine.getQuery(FormulaID
                        .of(STATS_BREAKDOWN_24H));
        
        return statsBreakdownStatement.getResult().getEventTypeStatistics();
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Creating System KPI for statistics...");
        historyOfAlerts =
                CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS)
                        .maximumSize(NUMBER_HOURS * PRECISION).build();
        alertPerDay();
        alertCriticityPerDay(Severity.BLOCKER);
        alertCriticityPerDay(Severity.CRITICAL);
        alertCriticityPerDay(Severity.MAJOR);
        alertCriticityPerDay(Severity.MINOR);
        alertCriticityPerDay(Severity.INFO);
        LOGGER.info("Creating cron for statistics.");
        
        // output snapshot every 1 minute
        cepEngine.createOrUpdateQuery(QueryInformations.directInformations(STATS_BREAKDOWN_24H,
                buildProviderEventFrequencyQuery()));
        
        cronRegistryService.registerCronTask("STATS", "0 0/1   * * * ?", EventStatsCron.class,
                new JobDataMap());
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.esper.IEventStatisticsService#putHistory
     * (int, org.komea.product.database.model.Measure)
     */
    @Override
    public void putHistory(final int _hourOrDay, final Measure _measure) {
    
    
        historyOfAlerts.put(_hourOrDay, _measure);
        
    }
    
    
    /**
     * @param _esperEngine
     *            the cepEngine to set
     */
    public final void setEsperEngine(final IEventEngineService _esperEngine) {
    
    
        cepEngine = _esperEngine;
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
