/**
 *
 */

package org.komea.product.backend.service.kpi;



import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.komea.product.backend.api.IDynamicQueryCacheService;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.exceptions.KpiProvidesInvalidFormulaException;
import org.komea.product.backend.service.ISpringService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.cron.KpiHistoryJob;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.esper.ConvertELIntoQuery;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.cep.api.dynamicdata.IDynamicDataQuery;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;
import org.quartz.JobDataMap;
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
public class KpiQueryRegisterService implements IKpiQueryRegisterService
{
    
    
    /**
     *
     */
    private static final String              KPI_HISTORY_INTERVAL = "0 0/60 * * * ?";
    
    private static final Logger              LOGGER               =
                                                                          LoggerFactory
                                                                                  .getLogger("kpi-query-register");
    
    @Autowired
    private ICronRegistryService             cronRegistry;
    
    @Autowired
    private IDynamicDataQueryRegisterService dynamicDataQueryRegisterService;
    
    @Autowired
    private IDynamicQueryCacheService        dynamicQueryCacheService;
    
    @Autowired
    private IEntityService                   entityService;
    
    @Autowired
    private IEventEngineService              esperEngine;
    
    @Autowired
    private ProjectDao                       projectDao;
    
    @Autowired
    private KpiDao                           requiredDAO;
    
    @Autowired
    private ISpringService                   springService;
    
    
    
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
        if (_kpi.isAssociatedToEntity()) {
            
            entityService.findEntityAssociatedToKpi(KpiKey.ofKpi(_kpi));
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.tester.IKpiQueryRegisterService#createEsperQueryFromKPI
     * (org.komea.product.database.model.Kpi)
     */
    @Override
    public void evaluateFormulaAndRegisterQuery(final Kpi _kpi) {
    
    
        Validate.isTrue(!_kpi.isAssociatedToEntity());
        final String formula = _kpi.getEsperRequest();
        if (!ConvertELIntoQuery.isValidFormula(formula)) {
            return;
        }
        final Object queryImplementation = ConvertELIntoQuery.parseEL(formula);
        if (!ConvertELIntoQuery.isValidFormulaObject(queryImplementation)) {
            throw new KpiProvidesInvalidFormulaException(_kpi);
        }
        final String queryName = _kpi.computeKPIEsperKey();
        registerQuery(_kpi, queryImplementation, queryName);
    }


    private void registerQuery(
            final Kpi _kpi,
            final Object queryImplementation,
            final String queryName) {
    
    
        if (queryImplementation instanceof ICEPQueryImplementation) {
            registerCEPQuery(_kpi, queryImplementation, queryName);
            
        } else if (queryImplementation instanceof IDynamicDataQuery) {
            registerDynamicQuery(_kpi, queryImplementation, queryName);
        }
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
    
    
    /**
     * @return the projectDao
     */
    public ProjectDao getProjectDao() {
    
    
        return projectDao;
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
        // WHEN A KPI IS REQUESTED
        // WE CHECK FOR DYNAMIC QUERY
        try {
            final String computeKPIEsperKey = _kpi.computeKPIEsperKey();
            LOGGER.trace("Request value from KPI {}", _kpi.getKpiKey());
            final IDynamicDataQuery query =
                    dynamicDataQueryRegisterService.getQuery(computeKPIEsperKey);
            if (query != null) {
                result = obtainDynamicQueryResult(computeKPIEsperKey, query);
            } else {
                result = obtainCepQueryResult(computeKPIEsperKey);
            }
            LOGGER.trace("Result of the query is {}", result);
            
        } catch (final Exception e) {
            LOGGER.error("KPI {}  could not return any result ", _kpi.computeKPIEsperKey(), e);
        }
        return new InferMissingEntityValuesIntoKpiResult(result, _kpi, entityService)
                .inferEntityKeys();
    }
    
    
    /**
     * @return the requiredDAO
     */
    public KpiDao getRequiredDAO() {
    
    
        return requiredDAO;
    }
    
    
    public ISpringService getSpringService() {
    
    
        return springService;
    }
    
    
    @PostConstruct
    public void initCron() {
    
    
        cronRegistry.registerCronTask("CRON_REGISTRY", KPI_HISTORY_INTERVAL, KpiHistoryJob.class,
                new JobDataMap());
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
    
    
    /**
     * @param _projectDao
     *            the projectDao to set
     */
    public void setProjectDao(final ProjectDao _projectDao) {
    
    
        projectDao = _projectDao;
    }
    
    
    /**
     * @param _requiredDAO
     *            the requiredDAO to set
     */
    public void setRequiredDAO(final KpiDao _requiredDAO) {
    
    
        requiredDAO = _requiredDAO;
    }
    
    
    public void setSpringService(final ISpringService _springService) {
    
    
        springService = _springService;
    }
    
    
    private KpiResult obtainCepQueryResult(final String computeKPIEsperKey) {
    
    
        KpiResult result;
        // IF IT FAILS WE CHECK FOR EVENT QUERY
        LOGGER.trace("This query {} is an event query", computeKPIEsperKey);
        result = esperEngine.getQueryOrFail(computeKPIEsperKey).getResult();
        return result;
    }
    
    
    private KpiResult obtainDynamicQueryResult(
            final String computeKPIEsperKey,
            final IDynamicDataQuery query) {
    
    
        KpiResult result;
        LOGGER.trace("This query {} is a dynamic query", computeKPIEsperKey);
        result = query.getResult();
        return result;
    }
    
    
    private void registerCEPQuery(
            final Kpi _kpi,
            final Object queryImplementation,
            final String queryName) {
    
    
        LOGGER.debug("KPI {} provides an event query {}.", _kpi, queryImplementation);
        esperEngine.createOrUpdateQuery(new QueryDefinition(queryName,
                (ICEPQueryImplementation) queryImplementation));
    }
    
    
    private void registerDynamicQuery(
            final Kpi _kpi,
            final Object queryImplementation,
            final String queryName) {
    
    
        springService.autowirePojo(queryImplementation);
        LOGGER.debug("KPI {} provides an dynamic data query {}.", _kpi, queryImplementation);
        final IDynamicDataQuery cachedQuery =
                dynamicQueryCacheService.addCacheOnDynamicQuery(queryName,
                        (IDynamicDataQuery) queryImplementation);
        dynamicDataQueryRegisterService.registerQuery(queryName, cachedQuery);
    }
    
}
