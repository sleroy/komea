
package org.komea.product.backend.service.kpi;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.esper.reactor.KPINotFoundRuntimeException;
import org.komea.product.backend.service.IEntityService;
import org.komea.product.backend.service.business.IEPMetric;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.esper.EPMetric;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.service.dto.KpiKey;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public final class KPIService implements IKPIService {
    
    @Autowired
    private IMeasureHistoryService measureService;
    
    @Autowired
    private IEsperEngine           esperEngine;
    
    @Autowired
    private KpiDao                 kpiDAO;
    
    @Autowired
    private ICronRegistryService   cronRegistry;
    
    private static final Logger    LOGGER = LoggerFactory.getLogger(KPIService.class);
    
    @Autowired
    private IEntityService         entityService;
    
    public KPIService() {
    
        super();
    }
    
    /**
     * Creates of update the history job of a KPI
     * 
     * @param _kpi
     *            the kpi
     * @param _entity
     *            its entity.
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
    
    @Override
    public Kpi findKPI(final KpiKey _kpiKey) {
    
        final KpiCriteria kpiCriteria = new KpiCriteria();
        kpiCriteria.createCriteria().andKpiKeyEqualTo(_kpiKey.getKpiName());
        if (_kpiKey.isAssociateWithEntity()) {
            kpiCriteria.createCriteria().andEntityIDEqualTo(_kpiKey.getEntityID());
            kpiCriteria.createCriteria().andEntityTypeEqualTo(_kpiKey.getEntityType());
        }
        return CollectionUtil.singleOrNull(kpiDAO.selectByExampleWithBLOBs(kpiCriteria));
        
    }
    
    // public <TEntity extends IEntity> IKPIFacade<TEntity> findKPIFacade(final KpiKey _kpiKey)
    // throws KPINotFoundException {
    //
    //
    // LOGGER.debug("Returning KPI facade from {}", _kpiKey);
    // final KpiCriteria criteria = new KpiCriteria();
    // criteria.createCriteria().andKpiKeyEqualTo(_kpiKey.getKpiName());
    // final Kpi requestedKpi =
    // CollectionUtil.singleOrNull(kpiDAO.selectByExampleWithBLOBs(criteria));
    // final IEntity entity = entityService.getEntityAssociatedToKpi(_kpiKey);
    // if (requestedKpi == null) { throw new KPINotFoundException(entity, _kpiKey.getKpiName()); }
    // final IEPMetric metricWrapperOverEsperQuery =
    // findMeasure(requestedKpi.computeKPIEsperKey(entity));
    // return new KPIFacade<TEntity>(metricWrapperOverEsperQuery, (TEntity) entity, requestedKpi,
    // measureService);
    //
    // }
    //
    
    @Override
    public Kpi findKPIOrFail(final KpiKey _kpiKey) {
    
        final IEntity entityAssociatedToKpi = entityService.getEntityAssociatedToKpi(_kpiKey);
        final Kpi findKPI = findKPI(_kpiKey);
        if (findKPI == null) {
            throw new KPINotFoundRuntimeException(entityAssociatedToKpi, _kpiKey.getKpiName());
        }
        return findKPI;
    }
    
    public IEPMetric findMeasure(final String _measureName) {
    
        return new EPMetric(esperEngine.getStatementOrFail(_measureName));
        
    }
    
    public ICronRegistryService getCronRegistry() {
    
        return cronRegistry;
    }
    
    public IEntityService getEntityService() {
    
        return entityService;
    }
    
    /**
     * @return the esperEngine
     */
    public final IEsperEngine getEsperEngine() {
    
        return esperEngine;
    }
    
    @Override
    public List<Measure> getHistory(final KpiKey _kpiKey) {
    
        final IEntity entityAssociatedToKpi = entityService.getEntityAssociatedToKpi(_kpiKey);
        
        return measureService.getMeasures(HistoryKey.of(findKPIOrFail(_kpiKey), entityAssociatedToKpi));
    }
    
    @Override
    public List<Measure> getHistory(final KpiKey _kpiKey, final MeasureCriteria _criteria) {
    
        final IEntity entity = entityService.getEntityAssociatedToKpi(_kpiKey);
        return measureService.getMeasures(HistoryKey.of(findKPIOrFail(_kpiKey), entity), _criteria);
    }
    
    public KpiDao getKpiDAO() {
    
        return kpiDAO;
    }
    
    @Override
    public double getKpiDoubleValue(final KpiKey _kpiKey) throws KPINotFoundException {
    
        return getKPIValue(_kpiKey).getDoubleValue();
        
    }
    
    @Override
    public IEPMetric getKPIValue(final KpiKey _kpiKey) {
    
        final IEntity entity = entityService.getEntityAssociatedToKpi(_kpiKey);
        final Kpi findKPIOrFail = findKPIOrFail(_kpiKey);
        return new EPMetric(esperEngine.getStatementOrFail(findKPIOrFail.computeKPIEsperKey(entity)));
        
    }
    
    @Override
    public List<Kpi> getListOfKpisForEntity(final IEntity _entity) {
    
        final List<Kpi> kpis = new ArrayList<Kpi>();
        final KpiCriteria allKpisFromEntityType = new KpiCriteria();
        allKpisFromEntityType.createCriteria().andEntityTypeEqualTo(_entity.entityType()).andEntityIDIsNull();
        
        kpis.addAll(kpiDAO.selectByExampleWithBLOBs(allKpisFromEntityType));
        final KpiCriteria allKpisOnlyEntity = new KpiCriteria();
        allKpisOnlyEntity.createCriteria().andEntityTypeEqualTo(_entity.entityType()).andEntityIDEqualTo(_entity.getId());
        kpis.addAll(kpiDAO.selectByExampleWithBLOBs(allKpisOnlyEntity));
        return kpis;
    }
    
    /**
     * @return the measureService
     */
    public final IMeasureHistoryService getMeasureService() {
    
        return measureService;
    }
    
    @PostConstruct
    public void init() {
    
        //
        
    }
    
    @Transactional
    @Override
    public void saveOrUpdate(final Kpi _kpi) {
    
        if (_kpi.getId() == null) {
            LOGGER.info("Saving new KPI : {}", _kpi.getKpiKey());
            kpiDAO.insert(_kpi);
        } else {
            LOGGER.info("KPI {} updated", _kpi.getKpiKey());
            kpiDAO.updateByPrimaryKey(_kpi);
        }
    }
    
    public void setCronRegistry(final ICronRegistryService _cronRegistry) {
    
        cronRegistry = _cronRegistry;
    }
    
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
    
    public void setKpiDAO(final KpiDao _kpiDAO) {
    
        kpiDAO = _kpiDAO;
    }
    
    /**
     * @param _measureService
     *            the measureService to set
     */
    public final void setMeasureService(final IMeasureHistoryService _measureService) {
    
        measureService = _measureService;
    }
    
    @Transactional
    @Override
    public void storeValueInHistory(final KpiKey _kpiKey) {
    
        final Measure measure = new Measure();
        switch (_kpiKey.getEntityType()) {
            case PERSON:
                measure.setIdPerson(_kpiKey.getEntityID());
                break;
            case PERSONG_GROUP:
                measure.setIdPersonGroup(_kpiKey.getEntityID());
                break;
            case PROJECT:
                measure.setIdProject(_kpiKey.getEntityID());
                break;
            default:
                // TODO:: Add code for default statement
                throw new UnsupportedOperationException("Not implemented default statement");
                
        }
        
        final Kpi findKPI = findKPI(_kpiKey);
        final IEntity entityAssociatedToKpi = entityService.getEntityAssociatedToKpi(_kpiKey);
        measure.setDate(new Date());
        measure.setIdKpi(findKPI.getId());
        measure.setValue(findMeasure(findKPI.computeKPIEsperKey(entityAssociatedToKpi)).getDoubleValue());
        measureService.storeMeasure(measure);
        final int purgeHistory = measureService.buildHistoryPurgeAction(findKPI).purgeHistory();
        LOGGER.debug("Purge history : {} items", purgeHistory);
        
    }
    
    @Override
    public void synchronizeEntityWithKomea(final IEntity _entity) {
    
        LOGGER.info("Updating / Refreshing Kpi statements of entity {}", _entity);
        final List<Kpi> listOfKpisOfEntity = getListOfKpisForEntity(_entity);
        
        LOGGER.info("EntityWithKPI {} has {} kpi", _entity, listOfKpisOfEntity.size());
        for (final Kpi kpi : listOfKpisOfEntity) {
            final String computeKPIEsperKey = kpi.computeKPIEsperKey(_entity);
            
            esperEngine.createOrUpdateEPLQuery(new QueryDefinition(kpi, computeKPIEsperKey));
            createOrUpdateHistoryCronJob(kpi, _entity);
        }
        
    }
    
    @Transactional
    @Override
    public void updateKPIOfEntity(final IEntity _entity, final List<Kpi> listOfKpis) {
    
        // Ignore silently global kpi...
        for (final Kpi kpi : listOfKpis) {
            if (kpi.getEntityID() == null) {
                continue;
            }
            saveOrUpdate(kpi);
        }
        
    }
    
    @Override
    public Measure getLastMeasures(final KpiKey _kpiKey) throws KPINotFoundException {
    
        // TODO STUB
        Measure measure = new Measure();
        measure.setDate(new Date());
        measure.setId(1);
        measure.setIdKpi(1);
        measure.setIdPerson(1);
        measure.setValue(12D);
        
        return measure;
    }
    
    @Override
    public List<Kpi> listAllKpis() {
    
        // TODO a faire le test
        List<Kpi> kpiList = kpiDAO.selectByCriteria(new KpiCriteria());
        return kpiList;
    }
    
}
