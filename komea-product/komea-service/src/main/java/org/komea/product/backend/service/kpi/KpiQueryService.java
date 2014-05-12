/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.io.Serializable;

import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.api.IKpiQueryService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.cep.api.dynamicdata.IDynamicDataQuery;
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
    
    
    private static final Logger              LOGGER = LoggerFactory.getLogger("kpi-query-service");
    
    @Autowired
    private ICronRegistryService             cronRegistry;
    
    
    @Autowired
    private IDynamicDataQueryRegisterService dynamicDataQueryRegisterService;
    
    @Autowired
    private IEntityService                   entityService;
    
    @Autowired
    private IEventEngineService              esperEngine;
    
    
    @Autowired
    private IKpiQueryRegisterService         kpiQueryRegisterService;
    
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.tester.IKpiQueryRegisterService#refreshEsper(org
     * .komea.product.database.model.Kpi)
     */
    @Override
    public void createOrUpdateQueryFromKpi(final Kpi _kpi) {
    
    
        LOGGER.debug("Refreshing Esper with KPI {}", _kpi.getKpiKey());
        evaluateFormulaAndRegisterQuery(_kpi);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.tester.IKpiQueryRegisterService#createEsperQueryFromKPI
     * (org.komea.product.database.model.Kpi)
     */
    
    public void evaluateFormulaAndRegisterQuery(final Kpi _kpi) {
    
    
        final InstantiateQueryFromFormula instantiateQueryFromFormula =
                new InstantiateQueryFromFormula(_kpi);
        kpiQueryRegisterService.registerQuery(_kpi, instantiateQueryFromFormula.instantiate());
    }
    
    
    /**
     * @return the cronRegistry
     */
    public ICronRegistryService getCronRegistry() {
    
    
        return cronRegistry;
    }
    
    
    public IDynamicDataQueryRegisterService getDynamicDataQueryRegisterService() {
    
    
        return dynamicDataQueryRegisterService;
    }
    
    
    /**
     * @return the entityService
     */
    public IEntityService getEntityService() {
    
    
        return entityService;
    }
    
    
    /**
     * @return the esperEngine
     */
    public IEventEngineService getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public IKpiQueryRegisterService getKpiQueryRegisterService() {
    
    
        return kpiQueryRegisterService;
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
            LOGGER.error("KPI {}  could not return any result ", _kpi.computeKPIEsperKey(), e);
        }
        return new InferMissingEntityValuesIntoKpiResult(result, _kpi, entityService)
                .inferEntityKeys();
    }
    
    
    /**
     * @param _cronRegistry
     *            the cronRegistry to set
     */
    public void setCronRegistry(final ICronRegistryService _cronRegistry) {
    
    
        cronRegistry = _cronRegistry;
    }
    
    
    public void setDynamicDataQueryRegisterService(
            final IDynamicDataQueryRegisterService _dynamicDataQueryRegisterService) {
    
    
        dynamicDataQueryRegisterService = _dynamicDataQueryRegisterService;
    }
    
    
    /**
     * @param _entityService
     *            the entityService to set
     */
    public void setEntityService(final IEntityService _entityService) {
    
    
        entityService = _entityService;
    }
    
    
    /**
     * @param _esperEngine
     *            the esperEngine to set
     */
    public void setEsperEngine(final IEventEngineService _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    public void setKpiQueryRegisterService(final IKpiQueryRegisterService _kpiQueryRegisterService) {
    
    
        kpiQueryRegisterService = _kpiQueryRegisterService;
    }
    
    
    private IDynamicDataQuery findDynamicQuery(final String computeKPIEsperKey) {
    
    
        return dynamicDataQueryRegisterService.getQuery(computeKPIEsperKey);
    }
    
    
    private ICEPQuery<Serializable, KpiResult> findEventQuery(final String computeKPIEsperKey) {
    
    
        return esperEngine.getQueryOrFail(computeKPIEsperKey);
    }
    
    
    private KpiResult getKpiResultFromKpi(final Kpi _kpi) {
    
    
        KpiResult result;
        final String computeKPIEsperKey = _kpi.computeKPIEsperKey();
        LOGGER.trace("Request value from KPI {}", _kpi.getKpiKey());
        final IDynamicDataQuery query = findDynamicQuery(computeKPIEsperKey);
        if (query != null) {
            result = obtainDynamicQueryResult(computeKPIEsperKey, query);
        } else {
            result = obtainCepQueryResult(computeKPIEsperKey);
        }
        LOGGER.trace("Result of the query is {}", result);
        return result;
    }
    
    
    private KpiResult obtainCepQueryResult(final String computeKPIEsperKey) {
    
    
        // IF IT FAILS WE CHECK FOR EVENT QUERY
        LOGGER.trace("This query {} is an event query", computeKPIEsperKey);
        return findEventQuery(computeKPIEsperKey).getResult();
    }
    
    
    private KpiResult obtainDynamicQueryResult(
            final String computeKPIEsperKey,
            final IDynamicDataQuery query) {
    
    
        LOGGER.trace("This query {} is a dynamic query", computeKPIEsperKey);
        return query.getResult();
    }
    
    
}
