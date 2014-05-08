
package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.komea.product.backend.api.IHistoryService;
import org.komea.product.backend.api.IKpiQueryService;
import org.komea.product.backend.api.IKpiValueService;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.backend.criterias.FindKpiOrFail;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProjectDao;
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
    
    
    private static final Logger    LOGGER = LoggerFactory.getLogger("kpi-value-service");
    
    @Autowired
    private IEntityService         entityService;
    
    @Autowired
    private KpiDao                 kpiDAO;
    
    @Autowired
    private IKpiQueryService       kpiQueryRegistry;
    
    @Autowired
    private IMeasureHistoryService measureService;
    
    @Autowired
    private ProjectDao             projectDao;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiValueService#
     * backupKpiValuesIntoHistory()
     */
    @Override
    public void backupKpiValuesIntoHistory() {
    
    
        LOGGER.info("Backup all kpis into the history...");
        for (final Kpi kpi : kpiDAO.selectByCriteria(new KpiCriteria())) {
            LOGGER.info("Kpi {} is backuping...", kpi.getKey());
            storeActualValueInHistory(HistoryKey.of(kpi));
            LOGGER.info("Kpi {} backup finished", kpi.getKey());
            
        }
        LOGGER.info("Backup finished for all kpis");
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.tester.IKpiValueService#getRealTimeMeasuresFromEntities
     * (java.util.List, java.util.List)
     */
    @Override
    public List<MeasureDto> getAllRealTimeMeasuresPerEntityAndPerKpi(
            final List<Kpi> kpis,
            final List<? extends IEntity> entities) {
    
    
        final List<MeasureDto> measures = new ArrayList<MeasureDto>(kpis.size() * entities.size());
        for (final IEntity entity : entities) {
            for (final Kpi kpi : kpis) {
                final Measure measure = getRealTimeMeasure(KpiKey.ofKpiAndEntity(kpi, entity));
                if (measure.isValid()) {
                    measures.add(new MeasureDto(measure, kpi.getKpiKey()));
                }
            }
        }
        return measures;
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
    
    
    public IKpiQueryService getKpiQueryRegistry() {
    
    
        return kpiQueryRegistry;
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.tester.IKpiValueService#getLastMeasureOfKpi(org
     * .komea.product.database.model.Kpi,
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
     * org.komea.product.cep.tester.IKpiValueService#getRealTimeMeasure(org.
     * komea.product.service.dto.KpiKey)
     */
    @Override
    public Measure getRealTimeMeasure(final KpiKey _kpiKey) {
    
    
        LOGGER.debug("Obtain the real time measure for -> {}", _kpiKey);
        final Kpi kpi = new FindKpiOrFail(_kpiKey.getKpiName(), kpiDAO).find();
        final Measure measureKey =
                Measure.initializeMeasure(kpi, _kpiKey.getEntityKey().getId(),
                        getSingleValue(_kpiKey).doubleValue());
        LOGGER.debug("Obtain the real time measure : {} result = {}", _kpiKey,
                measureKey.getValue());
        return measureKey;
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
            return new InferMissingEntityValuesIntoKpiResult(KpiResult.EMPTY, selectKpiByKey,
                    entityService).inferEntityKeys();
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
    public final void setMeasureService(final IMeasureHistoryService _measureService) {
    
    
        measureService = _measureService;
    }
    
    
    public void setProjectDao(final ProjectDao _projectDao) {
    
    
        projectDao = _projectDao;
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.tester.IKpiValueService#storeValueInHistory(org
     * .komea.product.service.dto.KpiKey)
     */
    @Override
    @Transactional
    public void storeActualValueInHistory(final HistoryKey _historyKey) throws KPINotFoundException {
    
    
        Validate.notNull(_historyKey);
        final Kpi findKPI = kpiDAO.selectByPrimaryKey(_historyKey.getKpiID());
        final KpiResult queryResult = kpiQueryRegistry.getQueryValueFromKpi(findKPI);
        // Store all data
        for (final Entry<EntityKey, Number> kpiLineValue : queryResult.getMap().entrySet()) {
            Validate.isTrue(kpiLineValue.getKey().isEntityReferenceKey());
            internalStoreValueInKpiHistory(kpiLineValue.getKey().getId(), kpiLineValue.getValue(),
                    findKPI);
        }
    }
    
    
    @Override
    public void storeValueInKpiHistory(final HistoryKey _kpiKey, final Number _kpiValue) {
    
    
        Validate.notNull(_kpiKey);
        Validate.notNull(_kpiValue);
        storeValueInKpiHistory(_kpiKey, _kpiValue, new DateTime());
    }
    
    
    @Override
    public void storeValueInKpiHistory(
            final HistoryKey _historyKey,
            final Number _kpiValue,
            final DateTime _dateTime) {
    
    
        Validate.isTrue(_historyKey.hasEntityReference());
        Validate.notNull(_kpiValue);
        Validate.notNull(_dateTime);
        final Kpi findKPI = kpiDAO.selectByPrimaryKey(_historyKey.getKpiID());
        internalStoreValueInKpiHistory(_historyKey.getEntityKey().getId(), _kpiValue, findKPI);
        
    }
    
    
    private void internalStoreValueInKpiHistory(
            final int _entityID,
            final Number _kpiValue,
            final Kpi findKPI) {
    
    
        Validate.notNull(_kpiValue);
        Validate.notNull(findKPI);
        final Measure measure =
                Measure.initializeMeasure(findKPI, _entityID, _kpiValue.doubleValue());
        
        measureService.storeMeasure(measure);
    }
}
