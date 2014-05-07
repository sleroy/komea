
package org.komea.product.backend.service.kpi;



import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.komea.product.backend.api.IDynamicQueryCacheService;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.service.ISpringService;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.cep.api.dynamicdata.IDynamicDataQuery;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class KpiQueryRegisterService implements IKpiQueryRegisterService
{
    
    
    private static final Logger              LOGGER = LoggerFactory.getLogger("kpi-query-register");
    
    @Autowired
    private IDynamicDataQueryRegisterService dynamicDataQueryRegisterService;
    
    @Autowired
    private IDynamicQueryCacheService        dynamicQueryCacheService;
    
    
    @Autowired
    private IEventEngineService              esperEngine;
    
    
    @Autowired
    private ISpringService                   springService;
    
    
    
    public IDynamicDataQueryRegisterService getDynamicDataQueryRegisterService() {
    
    
        return dynamicDataQueryRegisterService;
    }
    
    
    public IDynamicQueryCacheService getDynamicQueryCacheService() {
    
    
        return dynamicQueryCacheService;
    }
    
    
    public IEventEngineService getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public ISpringService getSpringService() {
    
    
        return springService;
    }
    
    
    public void registerCEPQuery(final Kpi _kpi, final Object queryImplementation) {
    
    
        LOGGER.debug("KPI {} provides an event query {}.", _kpi, queryImplementation);
        esperEngine.createOrUpdateQuery(new QueryDefinition(_kpi.computeKPIEsperKey(),
                (ICEPQueryImplementation) queryImplementation));
    }
    
    
    public void registerDynamicQuery(final Kpi _kpi, final Object queryImplementation) {
    
    
        springService.autowirePojo(queryImplementation);
        LOGGER.debug("KPI {} provides aString n dynamic data query {}.", _kpi, queryImplementation);
        final String kpiEsperKey = _kpi.computeKPIEsperKey();
        final IDynamicDataQuery cachedQuery =
                dynamicQueryCacheService.addCacheOnDynamicQuery(kpiEsperKey,
                        (IDynamicDataQuery) queryImplementation);
        dynamicDataQueryRegisterService.registerQuery(kpiEsperKey, cachedQuery);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiQueryRegisterService#registerQuery(org.komea.product.database.model.Kpi,
     * java.lang.Object)
     */
    @Override
    public void registerQuery(final Kpi _kpi, final Object queryImplementation) {
    
    
        if (queryImplementation instanceof ICEPQueryImplementation) {
            registerCEPQuery(_kpi, queryImplementation);
            
        } else if (queryImplementation instanceof IDynamicDataQuery) {
            registerDynamicQuery(_kpi, queryImplementation);
        } else {
            throw new IllegalArgumentException(
                    "Does not know the kind of query provided by the formula "
                            + queryImplementation.getClass().getName());
        }
    }
    
    
    public void setDynamicDataQueryRegisterService(
            final IDynamicDataQueryRegisterService _dynamicDataQueryRegisterService) {
    
    
        dynamicDataQueryRegisterService = _dynamicDataQueryRegisterService;
    }
    
    
    public void setDynamicQueryCacheService(
            final IDynamicQueryCacheService _dynamicQueryCacheService) {
    
    
        dynamicQueryCacheService = _dynamicQueryCacheService;
    }
    
    
    public void setEsperEngine(final IEventEngineService _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    public void setSpringService(final ISpringService _springService) {
    
    
        springService = _springService;
    }
    
}
