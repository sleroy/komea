/**
 *
 */

package org.komea.product.backend.service.kpi;



import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.criterias.FindKpiOrFail;
import org.komea.product.backend.criterias.FindKpiPerId;
import org.komea.product.backend.groovy.IGroovyEngineService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 */
@Service
@Transactional
public class QueryService implements IQueryService
{
    
    
    private static final Logger      LOGGER = LoggerFactory.getLogger("query-service");
    
    @Autowired
    private ICronRegistryService     cronRegistry;
    
    @Autowired
    private IEntityService           entityService;
    
    @Autowired
    private IEventEngineService      esperEngine;
    
    @Autowired
    private IGroovyEngineService     groovyEngineService;
    
    @Autowired
    private KpiDao                   kpiDao;
    
    @Autowired
    private IKpiQueryRegisterService kpiQueryRegisterService;
    
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.tester.IKpiQueryRegisterService#refreshEsper(org
     * .komea.product.database.model.Kpi)
     */
    @Override
    public void createOrUpdateQueryFromKpi(final Kpi _kpi) {
    
    
        LOGGER.debug("Refreshing Esper with KPI {}", _kpi.getKpiKey());
        IQuery queryImplementation = groovyEngineService.parseQuery(_kpi);
        if (queryImplementation == null) {
            LOGGER.error("Could not provide an implementation for the kpi {}, using stub",
                    _kpi.getKpiKey());
            queryImplementation = new StubQuery();
        }
        kpiQueryRegisterService.registerQuery(_kpi, queryImplementation);
        
    }
    
    
    @Cacheable(value = "kpi-realtime-value-cache")
    @Override
    public KpiResult evaluateRealTimeValues(final Integer _kpiID) {
    
    
        final Kpi selectKpiByKey = new FindKpiPerId(_kpiID, kpiDao).find();
        final KpiResult queryValueFromKpi = evaluateRealTimeValues(selectKpiByKey);
        LOGGER.debug("Returns the real time measure for -> {} is {}", selectKpiByKey,
                queryValueFromKpi);
        return queryValueFromKpi;
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.tester.IKpiQueryRegisterService#getEsperQueryFromKpi
     * (org.komea.product.database.model.Kpi)
     */
    @Override
    @Cacheable(value = "kpi-realtime-value-cache")
    public KpiResult evaluateRealTimeValues(final Kpi _kpi) {
    
    
        KpiResult result = new KpiResult();
        try {
            result = getKpiResultFromKpi(_kpi);
        } catch (final Exception e) {
            LOGGER.error("KPI {}  could not return any result ", _kpi.getEsperRequest(), e);
            result.hasFailed(e);
        }
        return new InferMissingEntityValuesIntoKpiResult(result, _kpi, entityService)
                .inferEntityKeys();
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.api.IQueryService#getRealTimeValues(java
     * .lang.String)
     */
    @Override
    public KpiResult evaluateRealTimeValues(final String _kpiName) {
    
    
        LOGGER.debug("Obtain the real time measure for -> {}", _kpiName);
        final Kpi selectKpiByKey = new FindKpiOrFail(KpiKey.ofKpiName(_kpiName), kpiDao).find();
        
        return evaluateRealTimeValues(selectKpiByKey.getId());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IQueryService#isDynamicQuery(org.komea.product.database.model.Kpi)
     */
    @Override
    public boolean isDynamicQuery(final Kpi _kpiChoice) {
    
    
        return esperEngine.getQuery(FormulaID.of(_kpiChoice)) instanceof IDynamicDataQuery;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IQueryService#isQueryOfKpiRegistered(org.komea.product.database.model.Kpi)
     */
    @Override
    public boolean isQueryOfKpiRegistered(final Kpi _kpi) {
    
    
        return esperEngine.existQuery(FormulaID.of(_kpi));
    }
    
    
    @Override
    public void removeQuery(final Kpi _kpi) {
    
    
        esperEngine.removeQuery(FormulaID.of(_kpi));
        
    }
    
    
    private KpiResult getKpiResultFromKpi(final Kpi _kpi) {
    
    
        KpiResult result;
        
        LOGGER.trace("Request value from KPI {}", _kpi.getKpiKey());
        result = (KpiResult) esperEngine.getQueryOrFail(FormulaID.of(_kpi)).getResult();
        LOGGER.debug("Result of the query is {}", result);
        return result;
    }
    
    
}
