package org.komea.product.backend.service.kpi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IQueryDefinition;
import org.komea.product.backend.exceptions.EntityNotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.exceptions.KpiAlreadyExistingException;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.esper.ConvertELIntoQuery;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.cep.api.ITupleResultMap;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.HasSuccessFactorKpiDao;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.dao.KpiAlertTypeDao;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.HasSuccessFactorKpiCriteria;
import org.komea.product.database.model.HasSuccessFactorKpiKey;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiAlertTypeCriteria;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.database.model.SuccessFactor;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.KpiKey;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
@Transactional
public final class KPIService extends AbstractService<Kpi, Integer, KpiCriteria> implements
        IKPIService {

    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-service");

    @Autowired
    private ICronRegistryService cronRegistry;

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IEventEngineService esperEngine;

    @Autowired
    private IMeasureHistoryService measureService;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private KpiDao requiredDAO;

    @Autowired
    private KpiAlertTypeDao alertDao;

    @Autowired
    private HasSuccessFactorKpiDao successFactorKpiDao;

    /**
     * This methods registers in the CEP Engine a new query from a kpi.
     *
     * @param _kpi the kpi
     * @return the query definition.
     */
    public IQueryDefinition createEsperQueryFromKPI(final Kpi _kpi) {

        final ICEPQueryImplementation queryImplementation
                = ConvertELIntoQuery.parseEL(_kpi.getEsperRequest());
        LOGGER.debug("Updating Esper with the query {}", queryImplementation);

        return new QueryDefinition(_kpi.computeKPIEsperKey(), queryImplementation);
    }

    /**
     * Creates of update the history job of a KPI
     *
     * @param _kpi the kpi
     * @param _entity its entity.
     */
    public void createOrUpdateHistoryCronJob(final Kpi _kpi, final IEntity _entity) {

        if (StringUtils.isEmpty(_kpi.getCronExpression())) {
            return;
        }
        final String kpiCronName = _kpi.getCronHistoryJobName();
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
     * @param _kpiKey KpiKey
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
        return CollectionUtil.singleOrNull(requiredDAO.selectByCriteriaWithBLOBs(kpiCriteria));

    }

    /**
     * Method findKPIOrFail.
     *
     * @param _kpiKey KpiKey
     * @return Kpi
     * @see
     * org.komea.product.backend.service.kpi.IKPIService#findKPIOrFail(KpiKey)
     */
    @Override
    public Kpi findKPIOrFail(final KpiKey _kpiKey) {

        final Kpi findKPI = findKPI(_kpiKey);
        if (findKPI == null) {
            throw new KPINotFoundRuntimeException(_kpiKey.getKpiName());
        }
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
    public final IEventEngineService getEsperEngine() {

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
     * @param entityType EntityType
     * @param kpiKeys List<String>
     * @return List<Kpi>
     */
    @Override
    public List<Kpi> getKpis(final EntityType entityType, final List<String> kpiKeys) {

        final KpiCriteria kpiCriteria = new KpiCriteria();
        if (kpiKeys == null || kpiKeys.isEmpty()) {
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
     * Retuens the last measure of a kpi
     *
     * @param _measureKey the kpi key
     * @param findKPIOrFail the kpi
     * @param entity the entity.
     * @return the measure
     */
    public Measure getLastMeasureOfKpi(final Kpi findKPIOrFail, final IEntity entity) {

        LOGGER.debug("Fetching last measures for KPI {} and entity {}", findKPIOrFail.getKpiKey(),
                entity);
        final MeasureCriteria criteria = new MeasureCriteria();
        final HistoryKey hKey = HistoryKey.of(findKPIOrFail, entity);
        final Measure valueMeasure
                = CollectionUtil.singleOrNull(measureService.getFilteredHistory(hKey, 1, criteria));
        LOGGER.debug("Returning value {}", valueMeasure);
        return valueMeasure;
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

    @Override
    public Measure getRealTimeMeasure(final KpiKey _key) {

        LOGGER.debug("Obtain the real time measure for -> {}", _key);
        final Kpi kpiOrFail = findKPIOrFail(_key);
        final ICEPQuery queryOrFail = esperEngine.getQueryOrFail(kpiOrFail.computeKPIEsperKey());
        final Measure measureKey
                = Measure.initializeMeasureFromKPIKey(kpiOrFail.getId(), _key.getEntityKey());
        //
        final List<IEntity> entitiesAssociatedToKpiKey = getEntitiesAssociatedToKpiKey(_key);
        if (entitiesAssociatedToKpiKey.isEmpty()) {
            throw new EntityNotFoundException(
                    _key.getEntityKey());
        }
        if (entitiesAssociatedToKpiKey.size() > 1) {
            throw new IllegalArgumentException(
                    "Cannot return a measure, many entities are referenced by the KpiKey " + _key);
        }
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

    @Override
    public List<Measure> getRealTimeMeasuresFromEntities(
            final List<Kpi> kpis,
            final List<BaseEntityDto> entities) {

        final List<Measure> measures = new ArrayList<Measure>(kpis.size() * entities.size());
        for (final BaseEntityDto entity : entities) {
            for (final Kpi kpi : kpis) {
                try {
                    final Measure measure = getRealTimeMeasure(KpiKey.ofKpiAndEntity(kpi, entity));
                    if (measure != null) {
                        measures.add(measure);
                    }
                } catch (Exception ex) {
                    LOGGER.error("Error with getRealTimeMeasure(kpiKey) where kpiKey="
                            + KpiKey.ofKpiAndEntity(kpi, entity), ex);
                }
            }
        }
        return measures;
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
     * @param _kpiKey KpiKey
     * @return Number
     * @see
     * org.komea.product.backend.service.kpi.IKPIService#getSingleValue(KpiKey)
     */
    @Override
    public Number getSingleValue(final KpiKey _kpiKey) {

        final Kpi kpiOrFail = findKPIOrFail(_kpiKey);
        final ICEPQuery epStatement = getEsperQueryFromKpi(kpiOrFail);

        return epStatement.getResult().asNumber().doubleValue();
    }

    /**
     * Method listAllKpis.
     *
     * @return List<Kpi>
     * @see org.komea.product.backend.service.kpi.IKPIService#listAllKpis()
     */
    @Override
    public List<Kpi> listAllKpis() {

        return requiredDAO.selectByCriteriaWithBLOBs(new KpiCriteria());
    }

    /**
     * Refresh esper with a KPI. The esper statement will be either created or
     * updated and the cron job updated as well.
     *
     * @param _kpi the kpi.
     */
    @Override
    public void refreshEsper(final Kpi _kpi) {

        LOGGER.debug("Refreshing Esper with KPI {}", _kpi.getKpiKey());
        esperEngine.createOrUpdateQuery(createEsperQueryFromKPI(_kpi));
        IEntity entity = null;
        if (_kpi.isAssociatedToEntity()) {

            entity = entityService.getEntityAssociatedToKpi(KpiKey.ofKpi(_kpi));
        }
        createOrUpdateHistoryCronJob(_kpi, entity);
    }

    /**
     * Method saveOrUpdateKpi.
     *
     * @param _kpi Kpi
     * @see org.komea.product.backend.service.kpi.IKPIService#saveOrUpdate(Kpi)
     */
    @Transactional
    @Override
    public void saveOrUpdate(final Kpi _kpi) {

        if (_kpi.getId() == null) {
            LOGGER.debug("Saving new KPI : {}", _kpi.getKpiKey());
            if (findKPI(KpiKey.ofKpi(_kpi)) != null) {
                throw new KpiAlreadyExistingException(
                        _kpi.getKpiKey());
            }
            requiredDAO.insert(_kpi);
        } else {
            LOGGER.debug("KPI {} updated", _kpi.getKpiKey());
            requiredDAO.updateByPrimaryKey(_kpi);
        }

        refreshEsper(_kpi);
    }

    /**
     * Method setCronRegistry.
     *
     * @param _cronRegistry ICronRegistryService
     */
    public void setCronRegistry(final ICronRegistryService _cronRegistry) {

        cronRegistry = _cronRegistry;
    }

    /**
     * Method setEntityService.
     *
     * @param _entityService IEntityService
     */
    public void setEntityService(final IEntityService _entityService) {

        entityService = _entityService;
    }

    /**
     * @param _esperEngine the esperEngine to set
     */
    public final void setEsperEngine(final IEventEngineService _esperEngine) {

        esperEngine = _esperEngine;
    }

    /**
     * Method setKpiDAO.
     *
     * @param _kpiDAO KpiDao
     */
    public void setKpiDAO(final KpiDao _kpiDAO) {

        requiredDAO = _kpiDAO;
    }

    /**
     * @param _measureService the measureService to set
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
     * @param _kpiKey the kpi key (with reference to the entity)
     * @param _kpiValue the value.
     */
    @Override
    public void storeMeasureOfAKpiInDatabase(final KpiKey _kpiKey, final Number _kpiValue) {

        final Kpi findKPI = findKPIOrFail(_kpiKey);
        final Measure measure
                = Measure.initializeMeasureFromKPIKey(findKPI.getId(), _kpiKey.getEntityKey());

        measure.setValue(_kpiValue.doubleValue());
        measureService.storeMeasure(measure);
        final int purgeHistory = measureService.buildHistoryPurgeAction(findKPI).purgeHistory();
        LOGGER.debug("Purge history : {} items", purgeHistory);
    }

    /**
     * Method storeValueInHistory.
     *
     * @param _kpiKey KpiKey
     * @throws KPINotFoundException
     * @see
     * org.komea.product.backend.service.kpi.IKPIService#storeValueInHistory(KpiKey)
     */
    @Transactional
    @Override
    public void storeValueInHistory(final KpiKey _kpiKey) throws KPINotFoundException {

        final Kpi kpi = findKPIOrFail(_kpiKey);
        final ICEPQuery epStatement = getEsperQueryFromKpi(kpi);

        if (kpi.isGlobal()) {
            final Number singleResult = epStatement.getResult().asNumber();
            storeMeasureOfAKpiInDatabase(_kpiKey, singleResult.doubleValue());
        } else {
            final Map<EntityKey, Number> simplifiedMap
                    = (Map) epStatement.getResult().asMap().asSimplifiedMap();
            for (final java.util.Map.Entry<EntityKey, Number> kpiLineValue : simplifiedMap
                    .entrySet()) {
                final KpiKey kpiKeyWithEntity
                        = KpiKey.ofKpiNameAndEntityKey(_kpiKey.getKpiName(), kpiLineValue.getKey());
                storeMeasureOfAKpiInDatabase(kpiKeyWithEntity, kpiLineValue.getValue());

            }
        }

    }

    /**
     * Returns the list of entities associated to a KPI key.
     *
     * @param _kpiKey the measure
     * @return the list of entities.
     */
    private List<IEntity> getEntitiesAssociatedToKpiKey(final KpiKey _kpiKey) {

        final Kpi findKPIOrFail = findKPIOrFail(_kpiKey);
        List<IEntity> entities = null;
        if (_kpiKey.isAssociatedToEntity()) {
            final IEntity entityAssociatedToKpi = entityService.getEntityAssociatedToKpi(_kpiKey);
            if (entityAssociatedToKpi == null) {
                throw new EntityNotFoundException(
                        _kpiKey.getEntityKey());
            }
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
     * @param _kpi the kpi
     * @return the statement or null.
     */
    private ICEPQuery getEsperQueryFromKpi(final Kpi _kpi) {

        return esperEngine.getQueryOrFail(_kpi.computeKPIEsperKey());
    }

    @Override
    protected KpiCriteria createPersonCriteriaOnLogin(final String key) {

        final KpiCriteria criteria = new KpiCriteria();
        criteria.createCriteria().andKpiKeyEqualTo(key);
        return criteria;
    }

    @Override
    public void saveOrUpdateKpi(final Kpi kpi, final List<KpiAlertType> alertTypes,
            final List<SuccessFactor> successFactors) {
        saveOrUpdate(kpi);
        final Integer idKpi = kpi.getId();

        final KpiAlertTypeCriteria kpiAlertTypeCriteria = new KpiAlertTypeCriteria();
        kpiAlertTypeCriteria.createCriteria().andIdKpiEqualTo(idKpi);
        alertDao.deleteByCriteria(kpiAlertTypeCriteria);
        if (alertTypes != null) {
            for (final KpiAlertType alertType : alertTypes) {
                alertDao.insert(alertType);
            }
        }

        final HasSuccessFactorKpiCriteria hasSuccessFactorKpiCriteria = new HasSuccessFactorKpiCriteria();
        hasSuccessFactorKpiCriteria.createCriteria().andIdKpiEqualTo(idKpi);
        successFactorKpiDao.deleteByCriteria(hasSuccessFactorKpiCriteria);
        if (successFactors != null) {
            for (final SuccessFactor successFactor : successFactors) {
                successFactorKpiDao.insert(new HasSuccessFactorKpiKey(successFactor.getId(), idKpi));
            }
        }
    }

    @Override
    public void deleteKpi(final Kpi kpi) {
        final Integer idKpi = kpi.getId();

        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.createCriteria().andIdKpiEqualTo(idKpi);
        measureService.deleteByCriteria(measureCriteria);

        final KpiAlertTypeCriteria kpiAlertTypeCriteria = new KpiAlertTypeCriteria();
        kpiAlertTypeCriteria.createCriteria().andIdKpiEqualTo(idKpi);
        alertDao.deleteByCriteria(kpiAlertTypeCriteria);

        final HasSuccessFactorKpiCriteria hasSuccessFactorKpiCriteria = new HasSuccessFactorKpiCriteria();
        hasSuccessFactorKpiCriteria.createCriteria().andIdKpiEqualTo(idKpi);
        successFactorKpiDao.deleteByCriteria(hasSuccessFactorKpiCriteria);

        delete(kpi);
    }
}
