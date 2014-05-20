/**
 *
 */

package org.komea.product.backend.service.kpi;



import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.api.IKpiQueryService;
import org.komea.product.backend.groovy.IGroovyEngineService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 */
@Service
@Transactional
public class KpiQueryService implements IKpiQueryService
{
    
    
    private static final Logger      LOGGER = LoggerFactory.getLogger("kpi-query-service");
    
    @Autowired
    private ICronRegistryService     cronRegistry;
    
    @Autowired
    private IEntityService           entityService;
    
    @Autowired
    private IEventEngineService      esperEngine;
    
    @Autowired
    private IGroovyEngineService     groovyEngineService;
    
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
        IQuery queryImplementation = groovyEngineService.parseQuery(_kpi.getEsperRequest());
        if (queryImplementation == null) {
            LOGGER.error("Could not provide an implementation for the kpi {}, using stub",
                    _kpi.getKpiKey());
            queryImplementation = new StubQuery();
        }
        kpiQueryRegisterService.registerQuery(_kpi, queryImplementation);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.tester.IKpiQueryRegisterService#getEsperQueryFromKpi
     * (org.komea.product.database.model.Kpi)
     */
    @Override
    public KpiResult getQueryValueFromKpi(final Kpi _kpi) {
    
    
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
     * @see org.komea.product.backend.api.IKpiQueryService#isQueryOfKpiRegistered(org.komea.product.database.model.Kpi)
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
        LOGGER.trace("Result of the query is {}", result);
        return result;
    }
    
    
}
