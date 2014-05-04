
package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.IHistoryService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.api.IKpiValueService;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.backend.api.exceptions.EntityNotFoundException;
import org.komea.product.backend.criterias.FindKpiOrFail;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.dto.MeasureDto;
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
 * This service provides the functionalities to get the value of a kpi, stores
 * the value into the history.
 */
@Service
@Transactional()
public final class KPIValueService implements IKpiValueService
{
    
    
    private static final Logger      LOGGER = LoggerFactory.getLogger("kpi-value-service");
    
    @Autowired
    private IEntityService           entityService;
    
    @Autowired
    private KpiDao                   kpiDAO;
    
    @Autowired
    private IKpiQueryRegisterService kpiQueryRegistry;
    
    @Autowired
    private IMeasureHistoryService   measureService;
    
    @Autowired
    private ProjectDao               projectDao;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiValueService#backupKpiValuesIntoHistory()
     */
    @Override
    public void backupKpiValuesIntoHistory() {
    
    
        LOGGER.info("Backup all kpis into the history...");
        for (final Kpi kpi : kpiDAO.selectByCriteria(new KpiCriteria())) {
            LOGGER.info("Kpi {} is backuping...", kpi.getKey());
            storeValueInHistory(KpiKey.ofKpi(kpi));
            LOGGER.info("Kpi {} backup finished", kpi.getKey());
            
        }
        LOGGER.info("Backup finished for all kpis");
        
    }
    
    
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
    
    
    public IKpiQueryRegisterService getKpiQueryRegistry() {
    
    
        return kpiQueryRegistry;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.tester.IKpiValueService#getLastMeasureOfKpi(org.komea.product.database.model.Kpi,
     * org.komea.product.database.api.IEntity)
     */
    @Override
    public Measure getLastMeasureInHistoryOfAKpi(final HistoryKey _historyKey) {
    
    
        LOGGER.debug("Fetching last measures for KPI {} and entity {}", _historyKey,
                _historyKey.getEntityKey());
        
        final MeasureCriteria criteria = new MeasureCriteria();
        final Measure valueMeasure =
                CollectionUtil.singleOrNull(measureService.getFilteredHistory(_historyKey, 1,
                        criteria));
        LOGGER.debug("Returning value {}", valueMeasure);
        return valueMeasure;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.tester.IKpiValueService#getMeasureService()
     */
    public final IHistoryService getMeasureService() {
    
    
        return measureService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IKPIService#getMinimalValueForAKpi(java.lang.Integer)
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
     * @see org.komea.product.cep.tester.IKpiValueService#getRealTimeMeasure(org.komea.product.service.dto.KpiKey)
     */
    @Override
    public Measure getRealTimeMeasure(final KpiKey _key) {
    
    
        LOGGER.debug("Obtain the real time measure for -> {}", _key);
        final List<? extends IEntity> entitiesAssociatedToKpiKey =
                getEntitiesAssociatedToKpiKey(_key);
        if (entitiesAssociatedToKpiKey.isEmpty()) { throw new EntityNotFoundException(
                _key.getEntityKey()); }
        if (entitiesAssociatedToKpiKey.size() > 1) { throw new IllegalArgumentException(
                "Cannot return a measure, many entities are referenced by the KpiKey " + _key); }
        
        final Kpi kpiOrFail = new FindKpiOrFail(_key, kpiDAO).find();
        final KpiResult kpiValue = kpiQueryRegistry.getQueryValueFromKpi(kpiOrFail);
        //
        final IEntity entity = CollectionUtil.singleOrNull(entitiesAssociatedToKpiKey);
        final Measure measureKey = initializeMeasure(_key, kpiOrFail, kpiValue, entity);
        LOGGER.debug("Obtain the real time measure : {} result = {}", _key, measureKey.getValue());
        return measureKey;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.tester.IKpiValueService#getRealTimeMeasuresFromEntities(java.util.List, java.util.List)
     */
    @Override
    public List<MeasureDto> getRealTimeMeasuresFromEntities(
            final List<Kpi> kpis,
            final List<BaseEntityDto> entities) {
    
    
        final List<MeasureDto> measures = new ArrayList<MeasureDto>(kpis.size() * entities.size());
        for (final BaseEntityDto entity : entities) {
            for (final Kpi kpi : kpis) {
                try {
                    final Measure measure = getRealTimeMeasure(KpiKey.ofKpiAndEntity(kpi, entity));
                    if (measure.isValid()) {
                        measures.add(new MeasureDto(measure, kpi.getKpiKey()));
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
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IKpiValueService#getRealTimeValues(java.lang.String)
     */
    @Override
    public KpiResult getRealTimeValues(final String _kpiName) {
    
    
        final Kpi selectKpiByKey = new FindKpiOrFail(KpiKey.ofKpiName(_kpiName), kpiDAO).find();
        if (selectKpiByKey == null) { return KpiResult.EMPTY; }
        
        return null;
    }
    
    
    @Override
    public Number getSingleValue(final KpiKey _kpiKey) {
    
    
        final Kpi kpiOrFail = new FindKpiOrFail(_kpiKey, kpiDAO).find();
        
        final KpiResult queryResult = kpiQueryRegistry.getQueryValueFromKpi(kpiOrFail);
        
        return queryResult.getValue(_kpiKey.getEntityKey());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.tester.IKpiValueService#getSingleValue(org.komea.product.service.dto.KpiKey)
     */
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
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.tester.IKpiValueService#storeValueInHistory(org.komea.product.service.dto.KpiKey)
     */
    @Override
    @Transactional
    public void storeValueInHistory(final KpiKey _kpiKey) throws KPINotFoundException {
    
    
        final Kpi kpi = new FindKpiOrFail(_kpiKey, kpiDAO).find();
        final KpiResult queryResult = kpiQueryRegistry.getQueryValueFromKpi(kpi);
        
        final Map<EntityKey, Number> resultMap = queryResult.getMap();
        for (final Entry<EntityKey, Number> kpiLineValue : resultMap.entrySet()) {
            final KpiKey kpiKeyWithEntity =
                    KpiKey.ofKpiNameAndEntityKey(_kpiKey.getKpiName(), kpiLineValue.getKey());
            storeValueInKpiHistory(kpiKeyWithEntity, kpiLineValue.getValue());
            
        }
        
    }
    
    
    @Override
    public void storeValueInKpiHistory(final KpiKey _kpiKey, final Number _kpiValue) {
    
    
        final Kpi findKPI = new FindKpiOrFail(_kpiKey, kpiDAO).find();
        final Measure measure =
                Measure.initializeMeasureFromKPIKey(findKPI.getId(), _kpiKey.getEntityKey());
        measure.setValue(_kpiValue.doubleValue());
        measureService.storeMeasure(measure);
        final int purgeHistory = measureService.buildHistoryPurgeAction(findKPI).purgeHistory();
        LOGGER.debug("Purge history : {} items", purgeHistory);
    }
    
    
    /**
     * Returns the list of entities associated to a KPI key.
     * 
     * @param _kpiKey
     *            the measure
     * @return the list of entities.
     */
    private List<? extends IEntity> getEntitiesAssociatedToKpiKey(final KpiKey _kpiKey) {
    
    
        final Kpi findKPIOrFail = new FindKpiOrFail(_kpiKey, kpiDAO).find();
        List<? extends IEntity> entities = null;
        if (_kpiKey.isAssociatedToEntity()) {
            final IEntity entityAssociatedToKpi = entityService.findEntityAssociatedToKpi(_kpiKey);
            if (null == entityAssociatedToKpi) { throw new EntityNotFoundException(
                    _kpiKey.getEntityKey()); }
            entities = Collections.singletonList(entityAssociatedToKpi);
        } else {
            
            entities = entityService.getEntitiesByEntityType(findKPIOrFail.getEntityType());
            
        }
        LOGGER.debug("Entities associated to KPI key {}: {}", _kpiKey, entities.size());
        return entities;
    }
    
    
    private Measure initializeMeasure(
            final KpiKey _key,
            final Kpi kpiOrFail,
            final KpiResult kpiValue,
            final IEntity entity) {
    
    
        final Measure measureKey =
                Measure.initializeMeasureFromKPIKey(kpiOrFail.getId(), _key.getEntityKey());
        measureKey.setEntity(entity.entityType(), entity.getId());
        final Number value = kpiValue.getValue(entity.getEntityKey());
        measureKey.setValue(value == null ? Double.NaN : value.doubleValue());
        return measureKey;
    }
    
    
}
