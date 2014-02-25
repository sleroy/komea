package org.komea.product.backend.service.kpi;

import com.espertech.esper.client.EPStatement;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.esper.EPStatementResult;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dto.KpiTendancyDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.KPIValueTable;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.KpiLineValue;
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
public final class KPIService extends AbstractService<Kpi, Integer, KpiCriteria> implements IKPIService {

    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-service");

    @SuppressWarnings("rawtypes")
    private static <TEntity extends IEntity> KPIValueTable<TEntity> fetchKpiValueTable(final Kpi kpiOrFail, final EPStatement epStatement) {

        final List<KpiLineValue> kpiLinesValues = EPStatementResult.build(epStatement).mapAPojoPerResultLine(KpiLineValue.class);
        return new KPIValueTable(kpiOrFail, kpiLinesValues);
    }

    @Autowired
    private ICronRegistryService cronRegistry;

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IEsperEngine esperEngine;

    @Autowired
    private IMeasureHistoryService measureService;

    @Autowired
    private KpiDao requiredDAO;

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
        final String kpiCronName = _kpi.getCronHistoryJobName(_entity);
        if (cronRegistry.existCron(kpiCronName)) {
            cronRegistry.updateCronFrequency(kpiCronName, _kpi.getCronExpression());
        } else {
            final JobDataMap properties = new JobDataMap();
            properties.put("entity", _entity);
            properties.put("kpi", _kpi);
            properties.put("service", this);
            cronRegistry.registerCronTask(kpiCronName, _kpi.getCronExpression(), KpiHistoryJob.class, properties);
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
        if (_kpiKey.verifiyIfIsAssociatedToEntity()) {
            kpiCriteria.createCriteria().andEntityIDEqualTo(_kpiKey.getEntityID());
            kpiCriteria.createCriteria().andEntityTypeEqualTo(_kpiKey.getEntityType());
        }
        return CollectionUtil.singleOrNull(requiredDAO.selectByExampleWithBLOBs(kpiCriteria));

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
     * Method getKpiRealTimeValues.
     *
     * @param _kpiKey KpiKey
     * @return KPIValueTable<TEntity>
     * @throws KPINotFoundException
     * @see
     * org.komea.product.backend.service.kpi.IKPIService#getKpiRealTimeValues(KpiKey)
     */
    @Override
    public <TEntity extends IEntity> KPIValueTable<TEntity> getKpiRealTimeValues(final KpiKey _kpiKey) throws KPINotFoundException {

        final Kpi kpiOrFail = findKPIOrFail(_kpiKey);
        final EPStatement epStatement = esperEngine.getStatementOrFail(kpiOrFail.computeKPIEsperKey());

        return fetchKpiValueTable(kpiOrFail, epStatement);

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
     * Method getKpiSingleValue.
     *
     * @param _kpiKey KpiKey
     * @return Double
     * @see
     * org.komea.product.backend.service.kpi.IKPIService#getKpiSingleValue(KpiKey)
     */
    @Override
    public Double getKpiSingleValue(final KpiKey _kpiKey) {

        final Kpi kpiOrFail = findKPIOrFail(_kpiKey);
        final EPStatement epStatement = esperEngine.getStatementOrFail(kpiOrFail.computeKPIEsperKey());

        final Number number = EPStatementResult.build(epStatement).singleResult();
        return number == null ? 0 : number.doubleValue();
    }

    /**
     * Method getKpiSingleValue.
     *
     * @param _kpiKey KpiKey
     * @param _columnName String
     * @return Double
     * @see
     * org.komea.product.backend.service.kpi.IKPIService#getKpiSingleValue(KpiKey,
     * String)
     */
    @Override
    public Double getKpiSingleValue(final KpiKey _kpiKey, final String _columnName) {

        final Kpi kpiOrFail = findKPIOrFail(_kpiKey);
        final EPStatement epStatement = esperEngine.getStatementOrFail(kpiOrFail.computeKPIEsperKey());

        final Number number = EPStatementResult.build(epStatement).singleResult(_columnName);
        return number == null ? 0 : number.doubleValue();
    }

    /**
     * Method getKpiTendancy.
     *
     * @param _measureKey KpiKey
     * @return KpiTendancyDto
     * @see
     * org.komea.product.backend.service.kpi.IKPIService#getKpiTendancy(KpiKey)
     */
    @Override
    public KpiTendancyDto getKpiTendancy(final KpiKey _measureKey) {

        // TODO Auto-generated method stub
        return new KpiTendancyDto(0, 0, _measureKey);
    }

    /**
     * @return the measureService
     */
    public final IHistoryService getMeasureService() {

        return measureService;
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
     * @param _kpi Kpi
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
    public final void setEsperEngine(final IEsperEngine _esperEngine) {

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

    /**
     * @param _requiredDAO
     */
    public void setRequiredDAO(final KpiDao _requiredDAO) {

        requiredDAO = _requiredDAO;
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
        final EPStatement epStatement = esperEngine.getStatementOrFail(kpi.computeKPIEsperKey());

        if (kpi.isGlobal()) {
            final Number singleResult = EPStatementResult.build(epStatement).singleResult();
            storeMeasureOfAKpiInDatabase(_kpiKey, singleResult.doubleValue());
        } else {
            final KPIValueTable<IEntity> kpiRealTimeValues = fetchKpiValueTable(kpi, epStatement);
            for (final KpiLineValue<IEntity> kpiLineValue : kpiRealTimeValues.getValues()) {
                final KpiKey kpiKeyWithEntity = KpiKey.ofKpiNameAndEntityOrNull(_kpiKey.getKpiName(), kpiLineValue.getEntity());
                storeMeasureOfAKpiInDatabase(kpiKeyWithEntity, kpiLineValue.getValue());

            }
        }

    }

    /**
     * Method initializeMeasureFromKPI.
     *
     * @param _kpiKey KpiKey
     * @return Measure
     */
    private Measure initializeMeasureFromKPIKey(final KpiKey _kpiKey) {

        final Measure measure = new Measure();
        measure.setDate(new Date());
        measure.setEntity(_kpiKey.getEntityType(), _kpiKey.getEntityID());
        measure.setIdKpi(findKPIOrFail(_kpiKey).getId());

        return measure;
    }

    /**
     * Stores the measure of a kpi in the database
     *
     * @param _kpiKey the kpi key (with reference to the entity)
     * @param _kpiValue the value.
     */
    private void storeMeasureOfAKpiInDatabase(final KpiKey _kpiKey, final Double _kpiValue) {

        final Measure measure = initializeMeasureFromKPIKey(_kpiKey);
        final Kpi findKPI = findKPIOrFail(_kpiKey);
        measure.setValue(_kpiValue);
        measureService.storeMeasure(measure);
        final int purgeHistory = measureService.buildHistoryPurgeAction(findKPI).purgeHistory();
        LOGGER.info("Purge history : {} items", purgeHistory);
    }
}
