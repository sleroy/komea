
package org.komea.product.backend.service.kpi;



import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.exceptions.EntityNotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.esper.EPStatementResult;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.KpiTendancyDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.KPIValueTable;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.KpiLineValue;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.espertech.esper.client.EPStatement;



/**
 */
@Service
@Transactional
public final class KPIService extends AbstractService<Kpi, Integer, KpiCriteria> implements
        IKPIService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-service");
    
    
    
    /**
     * Fetch Kpi Value table
     * 
     * @param kpiOrFail
     *            the kpi or fail
     * @param epStatement
     *            the statement
     * @return the entity
     */
    @SuppressWarnings("rawtypes")
    public static <TEntity extends IEntity> KPIValueTable<TEntity> fetchKpiValueTable(
            final Kpi kpiOrFail,
            final EPStatement epStatement) {
    
    
        final List<KpiLineValue> kpiLinesValues =
                EPStatementResult.build(epStatement).mapAPojoPerResultLine(KpiLineValue.class);
        return new KPIValueTable(kpiOrFail, kpiLinesValues);
    }
    
    
    
    @Autowired
    private ICronRegistryService   cronRegistry;
    
    @Autowired
    private IEntityService         entityService;
    
    @Autowired
    private IEsperEngine           esperEngine;
    
    @Autowired
    private IMeasureHistoryService measureService;
    
    @Autowired
    private ProjectDao             projectDao;
    
    @Autowired
    private KpiDao                 requiredDAO;
    
    
    
    /**
     * Creates of update the history job of a KPI
     * 
     * @param _kpi
     *            the kpi
     * @param _entity
     *            its entity.
     */
    public void createOrUpdateHistoryCronJob(final Kpi _kpi, final IEntity _entity) {
    
    
        if (StringUtils.isEmpty(_kpi.getCronExpression())) { return; }
        final String kpiCronName = _kpi.getCronHistoryJobName(_entity);
        if (cronRegistry.existCron(kpiCronName)) {
            cronRegistry.updateCronFrequency(kpiCronName, _kpi.getCronExpression());
        } else {
            final JobDataMap properties = new JobDataMap();
            properties.put("entity", _entity);
            properties.put("kpi", _kpi);
            properties.put("service", this);
            cronRegistry.registerCronTask(kpiCronName, _kpi.getCronExpression(),
                    KpiHistoryJob.class, properties);
        }
        
    }
    
    
    /**
     * Method findKPI.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return Kpi
     * @see org.komea.product.backend.service.kpi.IKPIService#findKPI(KpiKey)
     */
    @Override
    public Kpi findKPI(final KpiKey _kpiKey) {
    
    
        final KpiCriteria kpiCriteria = new KpiCriteria();
        kpiCriteria.createCriteria().andKpiKeyEqualTo(_kpiKey.getKpiName());
        if (_kpiKey.isAssociatedToEntity()) {
            kpiCriteria.createCriteria().andEntityIDEqualTo(_kpiKey.getEntityKey().getId());
            kpiCriteria.createCriteria().andEntityTypeEqualTo(
                    _kpiKey.getEntityKey().getEntityType());
        }
        return CollectionUtil.singleOrNull(requiredDAO.selectByExampleWithBLOBs(kpiCriteria));
        
    }
    
    
    /**
     * Method findKPIOrFail.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return Kpi
     * @see org.komea.product.backend.service.kpi.IKPIService#findKPIOrFail(KpiKey)
     */
    @Override
    public Kpi findKPIOrFail(final KpiKey _kpiKey) {
    
    
        final Kpi findKPI = findKPI(_kpiKey);
        if (findKPI == null) { throw new KPINotFoundRuntimeException(_kpiKey.getKpiName()); }
        return findKPI;
    }
    
    
    /**
     * Method getCronRegistry.
     * 
     * @return ICronRegistryService
     */
    public ICronRegistryService getCronRegistry() {
    
    
        return cronRegistry;
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
     * @return the esperEngine
     */
    public final IEsperEngine getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    /**
     * Method getKpiDAO.
     * 
     * @return KpiDao
     */
    public KpiDao getKpiDAO() {
    
    
        return requiredDAO;
    }
    
    
    /**
     * Method getKpis.
     * 
     * @param entityType
     *            EntityType
     * @param kpiKeys
     *            List<String>
     * @return List<Kpi>
     */
    @Override
    public List<Kpi> getKpis(final EntityType entityType, final List<String> kpiKeys) {
    
    
        final KpiCriteria kpiCriteria = new KpiCriteria();
        if (kpiKeys.isEmpty()) {
            kpiCriteria.createCriteria().andEntityTypeEqualTo(entityType);
        } else {
            for (final String kpiKey : kpiKeys) {
                final KpiCriteria.Criteria criteria = kpiCriteria.or();
                criteria.andKpiKeyEqualTo(kpiKey.trim()).andEntityTypeEqualTo(entityType);
            }
        }
        return requiredDAO.selectByCriteria(kpiCriteria);
    }
    
    
    /**
     * @return the measureService
     */
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
    
    
    /**
     * Get real time measure for a kpi.
     */
    @Override
    public Measure getRealTimeMeasure(final KpiKey _key) {
    
    
        LOGGER.debug("Obtain the real time measure : {}", _key);
        final Kpi kpiOrFail = findKPIOrFail(_key);
        final KPIValueTable<IEntity> valueTable =
                fetchKpiValueTable(kpiOrFail, getEsperQueryFromKpi(kpiOrFail));
        final Measure measureKey =
                Measure.initializeMeasureFromKPIKey(kpiOrFail.getId(), _key.getEntityKey());
        final List<IEntity> entitiesAssociatedToKpiKey = getEntitiesAssociatedToKpiKey(_key);
        if (entitiesAssociatedToKpiKey.size() != -1) { throw new IllegalArgumentException(
                "Cannot return a measure, many entities are referenced by the KpiKey " + _key); }
        final IEntity entity = CollectionUtil.singleOrNull(entitiesAssociatedToKpiKey);
        measureKey.setEntity(entity.entityType(), entity.getId());
        measureKey.setValue(valueTable.getValueOfEntity(entity));
        LOGGER.debug("Obtain the real time measure : {} result = ", _key, measureKey.getValue());
        return measureKey;
    }
    
    
    /**
     * Method getKpiRealTimeValues.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return KPIValueTable<TEntity>
     * @throws KPINotFoundException
     * @see org.komea.product.backend.service.kpi.IKPIService#getRealTimeValues(KpiKey)
     */
    @Override
    public <TEntity extends IEntity> KPIValueTable<TEntity> getRealTimeValues(final KpiKey _kpiKey)
            throws KPINotFoundException {
    
    
        final Kpi kpiOrFail = findKPIOrFail(_kpiKey);
        if (kpiOrFail.isGlobal()) { throw new IllegalArgumentException(
                "Global KPI cannot returns values for associated entities"); }
        final EPStatement epStatement = getEsperQueryFromKpi(kpiOrFail);
        
        return fetchKpiValueTable(kpiOrFail, epStatement);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.genericservice.AbstractService#getRequiredDAO()
     */
    @Override
    public IGenericDAO<Kpi, Integer, KpiCriteria> getRequiredDAO() {
    
    
        return requiredDAO;
    }
    
    
    /**
     * Method getKpiSingleValue.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return Double
     * @see org.komea.product.backend.service.kpi.IKPIService#getSingleValue(KpiKey)
     */
    @Override
    public Double getSingleValue(final KpiKey _kpiKey) {
    
    
        final Kpi kpiOrFail = findKPIOrFail(_kpiKey);
        final EPStatement epStatement = getEsperQueryFromKpi(kpiOrFail);
        
        final Number number = EPStatementResult.build(epStatement).singleResult();
        return number == null ? 0 : number.doubleValue();
    }
    
    
    /**
     * Returns the single value of a kpi with a column name.
     * 
     * @param _kpiKey
     *            KpiKey
     * @param _columnName
     *            String
     * @return Double
     * @see org.komea.product.backend.service.kpi.IKPIService#getSingleValue(KpiKey, String)
     */
    @Override
    public Double getSingleValue(final KpiKey _kpiKey, final String _columnName) {
    
    
        final Kpi kpiOrFail = findKPIOrFail(_kpiKey);
        final EPStatement epStatement = getEsperQueryFromKpi(kpiOrFail);
        
        final Number number = EPStatementResult.build(epStatement).singleResult(_columnName);
        return number == null ? Double.valueOf(0) : number.doubleValue();
    }
    
    
    /**
     * Method getKpiTendancy.
     * 
     * @param _measureKey
     *            KpiKey
     * @return KpiTendancyDto
     * @see org.komea.product.backend.service.kpi.IKPIService#getTendancy(KpiKey)
     */
    @SuppressWarnings("boxing")
    @Override
    public KpiTendancyDto getTendancy(final KpiKey _measureKey) {
    
    
        final KpiTendancyDto tendancies = new KpiTendancyDto(_measureKey);
        final Kpi kpiOrFail = findKPIOrFail(_measureKey);
        if (kpiOrFail.isGlobal()) { throw new IllegalArgumentException("Not work on global kpis"); }
        
        
        final Map<EntityKey, Double> realTimeValues = getRealTimeValues(_measureKey).convertIdMap();
        LOGGER.debug("Kpi {}, Received {} values from esper to compute tendancy", _measureKey,
                realTimeValues.size());
        LOGGER.trace("Real values for {}", realTimeValues);
        final Map<EntityKey, Double> lastMeasuresMap =
                getKpiLastMeasures(_measureKey).convertIdMap();
        LOGGER.debug("Kpi {}, Received {} values from history to compute tendancy", _measureKey,
                lastMeasuresMap.size());
        LOGGER.trace("Last values for {}", lastMeasuresMap);
        final List<IEntity> entities = getEntitiesAssociatedToKpiKey(_measureKey);
        for (final IEntity entity : entities) {
            final EntityKey entityKey = entity.getEntityKey();
            final Double realValue = realTimeValues.get(entityKey);
            final Double lastValue = lastMeasuresMap.get(entityKey);
            if (realValue == null) {
                LOGGER.warn(
                        "The KPI {} entity {} is not providing value, please check the formula",
                        _measureKey.getKpiName(), entity);
            }
            tendancies.addTendancy(entity, realValue, lastValue);
        }
        return tendancies;
    }
    
    
    /**
     * Method listAllKpis.
     * 
     * @return List<Kpi>
     * @see org.komea.product.backend.service.kpi.IKPIService#listAllKpis()
     */
    @Override
    public List<Kpi> listAllKpis() {
    
    
        final List<Kpi> kpiList = requiredDAO.selectByCriteria(new KpiCriteria());
        return kpiList;
    }
    
    
    /**
     * Method saveOrUpdate.
     * 
     * @param _kpi
     *            Kpi
     * @see org.komea.product.backend.service.kpi.IKPIService#saveOrUpdate(Kpi)
     */
    @Transactional
    @Override
    public void saveOrUpdate(final Kpi _kpi) {
    
    
        if (_kpi.getId() == null) {
            LOGGER.info("Saving new KPI : {}", _kpi.getKpiKey());
            requiredDAO.insert(_kpi);
        } else {
            LOGGER.info("KPI {} updated", _kpi.getKpiKey());
            requiredDAO.updateByPrimaryKey(_kpi);
        }
        
        final QueryDefinition queryDefinition = new QueryDefinition(_kpi);
        LOGGER.info("Updating Esper with the query {}", queryDefinition);
        esperEngine.createOrUpdateEPLQuery(queryDefinition);
        IEntity entity = null;
        if (_kpi.isAssociatedToEntity()) {
            
            entity = entityService.getEntityAssociatedToKpi(KpiKey.ofKpi(_kpi));
        }
        createOrUpdateHistoryCronJob(_kpi, entity);
    }
    
    
    /**
     * Method setCronRegistry.
     * 
     * @param _cronRegistry
     *            ICronRegistryService
     */
    public void setCronRegistry(final ICronRegistryService _cronRegistry) {
    
    
        cronRegistry = _cronRegistry;
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
    public final void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    /**
     * Method setKpiDAO.
     * 
     * @param _kpiDAO
     *            KpiDao
     */
    public void setKpiDAO(final KpiDao _kpiDAO) {
    
    
        requiredDAO = _kpiDAO;
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
    
    
    /**
     * @param _requiredDAO
     */
    public void setRequiredDAO(final KpiDao _requiredDAO) {
    
    
        requiredDAO = _requiredDAO;
    }
    
    
    /**
     * Stores the measure of a kpi in the database
     * 
     * @param _kpiKey
     *            the kpi key (with reference to the entity)
     * @param _kpiValue
     *            the value.
     */
    @Override
    public void storeMeasureOfAKpiInDatabase(final KpiKey _kpiKey, final Double _kpiValue) {
    
    
        final Kpi findKPI = findKPIOrFail(_kpiKey);
        final Measure measure =
                Measure.initializeMeasureFromKPIKey(findKPI.getId(), _kpiKey.getEntityKey());
        
        measure.setValue(_kpiValue);
        measureService.storeMeasure(measure);
        final int purgeHistory = measureService.buildHistoryPurgeAction(findKPI).purgeHistory();
        LOGGER.debug("Purge history : {} items", purgeHistory);
    }
    
    
    /**
     * Method storeValueInHistory.
     * 
     * @param _kpiKey
     *            KpiKey
     * @throws KPINotFoundException
     * @see org.komea.product.backend.service.kpi.IKPIService#storeValueInHistory(KpiKey)
     */
    @Transactional
    @Override
    public void storeValueInHistory(final KpiKey _kpiKey) throws KPINotFoundException {
    
    
        final Kpi kpi = findKPIOrFail(_kpiKey);
        final EPStatement epStatement = getEsperQueryFromKpi(kpi);
        
        if (kpi.isGlobal()) {
            final Number singleResult = EPStatementResult.build(epStatement).singleResult();
            storeMeasureOfAKpiInDatabase(_kpiKey, singleResult.doubleValue());
        } else {
            final KPIValueTable<IEntity> kpiRealTimeValues = fetchKpiValueTable(kpi, epStatement);
            for (final KpiLineValue<IEntity> kpiLineValue : kpiRealTimeValues.getValues()) {
                final KpiKey kpiKeyWithEntity =
                        KpiKey.ofKpiNameAndEntityOrNull(_kpiKey.getKpiName(),
                                kpiLineValue.getEntity());
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
    
    
        final Kpi findKPIOrFail = findKPIOrFail(_kpiKey);
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
    
    
    /**
     * Returns the esper statement of a KPI from the esper engine.
     * 
     * @param _kpi
     *            the kpi
     * @return the statement or null.
     */
    private EPStatement getEsperQueryFromKpi(final Kpi _kpi) {
    
    
        final EPStatement epStatement = esperEngine.getStatementOrFail(_kpi.computeKPIEsperKey());
        return epStatement;
    }
    
    
    /**
     * Returns the kpi last measures.
     * 
     * @param _measureKey
     *            the measure key.
     */
    private KPIValueTable<IEntity> getKpiLastMeasures(final KpiKey _measureKey) {
    
    
        LOGGER.debug("#####################################################################################");
        LOGGER.debug("Fetching last measures for KPI {}", _measureKey);
        final Kpi findKPIOrFail = findKPIOrFail(_measureKey);
        final KPIValueTable<IEntity> kpiValueTable = new KPIValueTable<IEntity>();
        for (final IEntity entity : entityService.loadEntities(findKPIOrFail.getEntityType())) {
            LOGGER.debug("Fetching last measures for KPI {} and entity {}", _measureKey, entity);
            final MeasureCriteria criteria = new MeasureCriteria();
            final HistoryKey hKey = HistoryKey.of(findKPIOrFail, entity);
            final Measure valueMeasure =
                    CollectionUtil.singleOrNull(measureService
                            .getFilteredHistory(hKey, 1, criteria));
            LOGGER.debug("Returning value {}", valueMeasure);
            kpiValueTable.addLine(entity, valueMeasure == null ? null : valueMeasure.getValue());
        }
        LOGGER.debug("#####################################################################################");
        return kpiValueTable;
        
        
    }
}
