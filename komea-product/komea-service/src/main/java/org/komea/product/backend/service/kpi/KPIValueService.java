
package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.api.formula.ITupleResultMap;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.backend.api.exceptions.EntityNotFoundException;
import org.komea.product.backend.criterias.FindKpiOrFail;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.KpiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This service provides the functionalities to get the value of a kpi, stores the value into the history.
 */
@Service
@Transactional()
public final class KPIValueService implements IKpiValueService
{
    
    
    private static final Logger      LOGGER = LoggerFactory.getLogger("kpi-value-service");
    
    @Autowired
    private IEntityService           entityService;
    
    @Autowired
    private IEventEngineService      esperEngine;
    
    @Autowired
    private KpiDao                   kpiDAO;
    
    @Autowired
    private IKpiQueryRegisterService kpiQueryRegistry;
    
    @Autowired
    private IMeasureHistoryService   measureService;
    
    @Autowired
    private ProjectDao               projectDao;
    
    
    
    /**
     * Method getEntityService.
     * 
     * @return IEntityService
     */
    public IEntityService getEntityService() {
    
    
        return entityService;
    }
    
    
    /**
     * @return the esperEngine
     */
    public final IEventEngineService getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    /**
     * Method getKpiDAO.
     * 
     * @return KpiDao
     */
    public KpiDao getKpiDAO() {
    
    
        return kpiDAO;
    }
    
    
    public IKpiQueryRegisterService getKpiQueryRegistry() {
    
    
        return kpiQueryRegistry;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiValueService#getLastMeasureOfKpi(org.komea.product.database.model.Kpi, org.komea.product.database.api.IEntity)
     */
    @Override
    public Measure getLastMeasureOfKpi(final Kpi findKPIOrFail, final IEntity entity) {
    
    
        LOGGER.debug("Fetching last measures for KPI {} and entity {}", findKPIOrFail.getKpiKey(),
                entity);
        final MeasureCriteria criteria = new MeasureCriteria();
        final HistoryKey hKey = HistoryKey.of(findKPIOrFail, entity);
        final Measure valueMeasure =
                CollectionUtil.singleOrNull(measureService.getFilteredHistory(hKey, 1, criteria));
        LOGGER.debug("Returning value {}", valueMeasure);
        return valueMeasure;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiValueService#getMeasureService()
     */
    @Override
    public final IHistoryService getMeasureService() {
    
    
        return measureService;
    }
    
    
    /**
     * Project : DAO
     * 
     * @return the project DAO.
     */
    public ProjectDao getProjectDao() {
    
    
        return projectDao;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiValueService#getRealTimeMeasure(org.komea.product.service.dto.KpiKey)
     */
    @Override
    public Measure getRealTimeMeasure(final KpiKey _key) {
    
    
        LOGGER.debug("Obtain the real time measure for -> {}", _key);
        final Kpi kpiOrFail = new FindKpiOrFail(_key, kpiDAO).find();
        final ICEPQuery queryOrFail = esperEngine.getQueryOrFail(kpiOrFail.computeKPIEsperKey());
        final Measure measureKey =
                Measure.initializeMeasureFromKPIKey(kpiOrFail.getId(), _key.getEntityKey());
        //
        final List<IEntity> entitiesAssociatedToKpiKey = getEntitiesAssociatedToKpiKey(_key);
        if (entitiesAssociatedToKpiKey.isEmpty()) { throw new EntityNotFoundException(
                _key.getEntityKey()); }
        if (entitiesAssociatedToKpiKey.size() > 1) { throw new IllegalArgumentException(
                "Cannot return a measure, many entities are referenced by the KpiKey " + _key); }
        //
        final IEntity entity = CollectionUtil.singleOrNull(entitiesAssociatedToKpiKey);
        measureKey.setEntity(entity.entityType(), entity.getId());
        final ITupleResultMap<Number> map = queryOrFail.getResult().asMap();
        final Number number = map.get(entity.getEntityKey());
        if (number != null) {
            measureKey.setValue(number.doubleValue());
        }
        LOGGER.debug("Obtain the real time measure : {} result = {}", _key, measureKey.getValue());
        return measureKey;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiValueService#getRealTimeMeasuresFromEntities(java.util.List, java.util.List)
     */
    @Override
    public List<Measure> getRealTimeMeasuresFromEntities(
            final List<Kpi> kpis,
            final List<BaseEntityDto> entities) {
    
    
        final List<Measure> measures = new ArrayList<Measure>(kpis.size() * entities.size());
        for (final BaseEntityDto entity : entities) {
            for (final Kpi kpi : kpis) {
                try {
                    final Measure measure = getRealTimeMeasure(KpiKey.ofKpiAndEntity(kpi, entity));
                    if (measure != null && measure.getValue() != null) {
                        measures.add(measure);
                    }
                } catch (final Exception ex) {
                    LOGGER.error(
                            "Error with getRealTimeMeasure(kpiKey) where kpiKey="
                                    + KpiKey.ofKpiAndEntity(kpi, entity), ex);
                }
            }
        }
        return measures;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiValueService#getSingleValue(org.komea.product.service.dto.KpiKey)
     */
    
    @Override
    public Number getSingleValue(final KpiKey _kpiKey) {
    
    
        final Kpi kpiOrFail = new FindKpiOrFail(_kpiKey, kpiDAO).find();
        
        final ICEPResult queryResult = kpiQueryRegistry.getQueryValueFromKpi(kpiOrFail);
        
        return queryResult.asNumber().doubleValue();
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
     * @param _esperEngine
     *            the esperEngine to set
     */
    public final void setEsperEngine(final IEventEngineService _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    /**
     * /**
     * 
     * @param _requiredDAO
     */
    public void setKpiDAO(final KpiDao _requiredDAO) {
    
    
        kpiDAO = _requiredDAO;
    }
    
    
    public void setKpiQueryRegistry(final IKpiQueryRegisterService _kpiQueryRegistry) {
    
    
        kpiQueryRegistry = _kpiQueryRegistry;
    }
    
    
    /**
     * @param _measureService
     *            the measureService to set
     */
    public final void setMeasureService(final IMeasureHistoryService _measureService) {
    
    
        measureService = _measureService;
    }
    
    
    public void setProjectDao(final ProjectDao _projectDao) {
    
    
        projectDao = _projectDao;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiValueService#storeMeasureOfAKpiInDatabase(org.komea.product.service.dto.KpiKey, java.lang.Number)
     */
    
    @Override
    public void storeMeasureOfAKpiInDatabase(final KpiKey _kpiKey, final Number _kpiValue) {
    
    
        final Kpi findKPI = new FindKpiOrFail(_kpiKey, kpiDAO).find();
        final Measure measure =
                Measure.initializeMeasureFromKPIKey(findKPI.getId(), _kpiKey.getEntityKey());
        
        measure.setValue(_kpiValue.doubleValue());
        measureService.storeMeasure(measure);
        final int purgeHistory = measureService.buildHistoryPurgeAction(findKPI).purgeHistory();
        LOGGER.debug("Purge history : {} items", purgeHistory);
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiValueService#storeValueInHistory(org.komea.product.service.dto.KpiKey)
     */
    @Override
    @Transactional
    public void storeValueInHistory(final KpiKey _kpiKey) throws KPINotFoundException {
    
    
        final Kpi kpi = new FindKpiOrFail(_kpiKey, kpiDAO).find();
        final ICEPResult queryResult = kpiQueryRegistry.getQueryValueFromKpi(kpi);
        
        if (kpi.isGlobal()) {
            final Number singleResult = queryResult.asNumber();
            storeMeasureOfAKpiInDatabase(_kpiKey, singleResult.doubleValue());
        } else {
            final Map<EntityKey, Number> simplifiedMap =
                    (Map) queryResult.asMap().asSimplifiedMap();
            for (final java.util.Map.Entry<EntityKey, Number> kpiLineValue : simplifiedMap
                    .entrySet()) {
                final KpiKey kpiKeyWithEntity =
                        KpiKey.ofKpiNameAndEntityKey(_kpiKey.getKpiName(), kpiLineValue.getKey());
                storeMeasureOfAKpiInDatabase(kpiKeyWithEntity, kpiLineValue.getValue());
                
            }
        }
        
    }
    
    
    /**
     * Returns the list of entities associated to a KPI key.
     * 
     * @param _kpiKey
     *            the measure
     * @return the list of entities.
     */
    private List<IEntity> getEntitiesAssociatedToKpiKey(final KpiKey _kpiKey) {
    
    
        final Kpi findKPIOrFail = new FindKpiOrFail(_kpiKey, kpiDAO).find();
        List<IEntity> entities = null;
        if (_kpiKey.isAssociatedToEntity()) {
            final IEntity entityAssociatedToKpi = entityService.getEntityAssociatedToKpi(_kpiKey);
            if (entityAssociatedToKpi == null) { throw new EntityNotFoundException(
                    _kpiKey.getEntityKey()); }
            entities = Collections.singletonList(entityAssociatedToKpi);
        } else {
            
            entities = entityService.loadEntities(findKPIOrFail.getEntityType());
            
        }
        LOGGER.debug("Entities associated to KPI key {}: {}", _kpiKey, entities.size());
        return entities;
    }
    
    
    protected KpiCriteria createKeyCriteria(final String key) {
    
    
        final KpiCriteria criteria = new KpiCriteria();
        criteria.createCriteria().andKpiKeyEqualTo(key);
        return criteria;
    }
    
}
