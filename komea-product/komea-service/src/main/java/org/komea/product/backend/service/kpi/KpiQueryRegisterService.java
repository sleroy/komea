/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.apache.commons.lang.StringUtils;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.api.IQueryDefinition;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.esper.ConvertELIntoQuery;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class KpiQueryRegisterService implements IKpiQueryRegisterService
{
    
    
    private static final Logger  LOGGER = LoggerFactory.getLogger("kpi-query-register");
    
    
    @Autowired
    private ICronRegistryService cronRegistry;
    
    
    @Autowired
    private IEntityService       entityService;
    
    
    @Autowired
    private IEventEngineService  esperEngine;
    
    
    @Autowired
    private ProjectDao           projectDao;
    
    
    @Autowired
    private KpiDao               requiredDAO;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiQueryRegisterService#createEsperQueryFromKPI(org.komea.product.database.model.Kpi)
     */
    @Override
    public IQueryDefinition createEsperQueryFromKPI(final Kpi _kpi) {
    
    
        final ICEPQueryImplementation queryImplementation =
                ConvertELIntoQuery.parseEL(_kpi.getEsperRequest());
        LOGGER.debug("Updating Esper with the query {}", queryImplementation);
        
        return new QueryDefinition(_kpi.computeKPIEsperKey(), queryImplementation);
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IKpiQueryRegisterService#createOrUpdateHistoryCronJob(org.komea.product.database.model.Kpi,
     * org.komea.product.database.api.IEntity)
     */
    @Override
    public void createOrUpdateHistoryCronJob(final Kpi _kpi, final IEntity _entity) {
    
    
        if (StringUtils.isEmpty(_kpi.getCronExpression())) { return; }
        final String kpiCronName = _kpi.getCronHistoryJobName();
        if (cronRegistry.existCron(kpiCronName)) {
            cronRegistry.updateCronFrequency(kpiCronName, _kpi.getCronExpression());
        } else {
            prepareKpiHistoryJob(_kpi, _entity, kpiCronName);
        }
        
    }
    
    
    /**
     * @return the cronRegistry
     */
    public ICronRegistryService getCronRegistry() {
    
    
        return cronRegistry;
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
     * @see org.komea.product.backend.service.kpi.IKpiQueryRegisterService#getEsperQueryFromKpi(org.komea.product.database.model.Kpi)
     */
    @Override
    public ICEPResult getQueryValueFromKpi(final Kpi _kpi) {
    
    
        return esperEngine.getQueryOrFail(_kpi.computeKPIEsperKey()).getResult();
    }
    
    
    /**
     * @return the requiredDAO
     */
    public KpiDao getRequiredDAO() {
    
    
        return requiredDAO;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiQueryRegisterService#prepareKpiHistoryJob(org.komea.product.database.model.Kpi,
     * org.komea.product.database.api.IEntity, java.lang.String)
     */
    @Override
    public void prepareKpiHistoryJob(final Kpi _kpi, final IEntity _entity, final String kpiCronName) {
    
    
        final JobDataMap properties = new JobDataMap();
        properties.put("entity", _entity);
        properties.put("kpi", _kpi);
        cronRegistry.registerCronTask(kpiCronName, _kpi.getCronExpression(), KpiHistoryJob.class,
                properties);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiQueryRegisterService#refreshEsper(org.komea.product.database.model.Kpi)
     */
    @Override
    public void registerOrUpdateQueryFromKpi(final Kpi _kpi) {
    
    
        LOGGER.debug("Refreshing Esper with KPI {}", _kpi.getKpiKey());
        esperEngine.createOrUpdateQuery(createEsperQueryFromKPI(_kpi));
        IEntity entity = null;
        if (_kpi.isAssociatedToEntity()) {
            
            entity = entityService.getEntityAssociatedToKpi(KpiKey.ofKpi(_kpi));
        }
        createOrUpdateHistoryCronJob(_kpi, entity);
    }
    
    
    /**
     * @param _cronRegistry
     *            the cronRegistry to set
     */
    public void setCronRegistry(final ICronRegistryService _cronRegistry) {
    
    
        cronRegistry = _cronRegistry;
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
    
    
}
