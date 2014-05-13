
package org.komea.product.backend.service.kpi;


import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.IKpiQueryService;
import org.komea.product.backend.api.IKpiValueService;
import org.komea.product.backend.criterias.FindKpiOrFail;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service provides the functionalities to get the value of a kpi, stores
 * the value into the history.
 */
@Service
@Transactional()
public final class KPIValueService implements IKpiValueService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-value-service");
    
    @Autowired
    private IEntityService      entityService;
    
    @Autowired
    private KpiDao              kpiDAO;
    
    @Autowired
    private IKpiQueryService    kpiQueryRegistry;
    
    @Autowired
    private MeasureDao          measureService;
    
    @Autowired
    private ProjectDao          projectDao;
    
    /**
     * Method getEntityService.
     * 
     * @return IEntityService
     */
    public IEntityService getEntityService() {
    
        return entityService;
    }
    
    /**
     * Method getKpiDAO.
     * 
     * @return KpiDao
     */
    public KpiDao getKpiDAO() {
    
        return kpiDAO;
    }
    
    public IKpiQueryService getKpiQueryRegistry() {
    
        return kpiQueryRegistry;
    }
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.api.IKPIService#getMinimalValueForAKpi(java
     * .lang.Integer)
     */
    @Override
    public Double getMinimalValueForAKpi(final Integer _kpiID) {
    
        final Kpi selectByPrimaryKey = kpiDAO.selectByPrimaryKey(_kpiID);
        Validate.notNull(_kpiID);
        return selectByPrimaryKey.getValueMin();
    }
    
    /**
     * Project : DAO
     * 
     * @return the project DAO.
     */
    public ProjectDao getProjectDao() {
    
        return projectDao;
    }
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.api.IKpiValueService#getRealTimeValues(java
     * .lang.String)
     */
    @Override
    public KpiResult getRealTimeValue(final String _kpiName) {
    
        LOGGER.debug("Obtain the real time measure for -> {}", _kpiName);
        final Kpi selectKpiByKey = new FindKpiOrFail(KpiKey.ofKpiName(_kpiName), kpiDAO).find();
        if (selectKpiByKey == null) {
            LOGGER.debug("Returns an ## empty result ##", KpiResult.EMPTY);
            return new InferMissingEntityValuesIntoKpiResult(KpiResult.EMPTY, selectKpiByKey, entityService).inferEntityKeys();
        }
        
        final KpiResult queryValueFromKpi = kpiQueryRegistry.getQueryValueFromKpi(selectKpiByKey);
        LOGGER.debug("Returns the real time measure for -> {}", _kpiName);
        return queryValueFromKpi;
    }
    
    /**
     * Returns a single value from a kpi result.
     */
    @Override
    public Number getSingleValue(final KpiKey _kpiKey) {
    
        return getRealTimeValue(_kpiKey.getKpiName()).getValue(_kpiKey.getEntityKey());
    }
    
    /**
     * Method setEntityService.
     * 
     * @param _entityService
     *            IEntityService
     */
    public void setEntityService(final IEntityService _entityService) {
    
        entityService = _entityService;
    }
    
    /**
     * /**
     * 
     * @param _requiredDAO
     */
    public void setKpiDAO(final KpiDao _requiredDAO) {
    
        kpiDAO = _requiredDAO;
    }
    
    public void setKpiQueryRegistry(final IKpiQueryService _kpiQueryRegistry) {
    
        kpiQueryRegistry = _kpiQueryRegistry;
    }
    
    /**
     * @param _measureService
     *            the measureService to set
     */
    public final void setMeasureService(final MeasureDao _measureService) {
    
        measureService = _measureService;
    }
    
    public void setProjectDao(final ProjectDao _projectDao) {
    
        projectDao = _projectDao;
    }
    
}
