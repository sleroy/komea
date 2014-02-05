
package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.esper.reactor.KPINotFoundRuntimeException;
import org.komea.product.backend.kpi.KPIFacade;
import org.komea.product.backend.service.business.IKPIFacade;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public final class KPIService implements IKPIService
{
    
    
    @Autowired
    private IMeasureService      measureService;
    
    
    @Autowired
    private IEsperEngine         esperEngine;
    
    @Autowired
    private KpiDao               kpiDAO;
    
    @Autowired
    private ICronRegistryService cronRegistry;
    
    
    private static final Logger  LOGGER = LoggerFactory.getLogger(KPIService.class);
    
    
    
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
    
    
    @Override
    public Kpi findKPI(final IEntity _entity, final String _kpiName) {
    
    
        final KpiCriteria kpiCriteria = new KpiCriteria();
        kpiCriteria.createCriteria().andKpiKeyEqualTo(_kpiName);
        if (_entity != null) {
            kpiCriteria.createCriteria().andEntityIDEqualTo(_entity.getId());
            kpiCriteria.createCriteria().andEntityTypeEqualTo(_entity.getType());
        }
        return CollectionUtil.singleOrNull(kpiDAO.selectByExampleWithBLOBs(kpiCriteria));
        
    }
    
    
    @Override
    public <TEntity extends IEntity> IKPIFacade<TEntity> findKPIFacade(
            final TEntity _entity,
            final String _kpiName) throws KPINotFoundException {
    
    
        LOGGER.debug("Returning KPI facade from entity {} and kpi {}", _entity, _kpiName);
        final KpiCriteria criteria = new KpiCriteria();
        criteria.createCriteria().andKpiKeyEqualTo(_kpiName);
        final Kpi requestedKpi =
                CollectionUtil.singleOrNull(kpiDAO.selectByExampleWithBLOBs(criteria));
        
        if (requestedKpi == null) { throw new KPINotFoundException(_entity, _kpiName); }
        final IEPMetric metricWrapperOverEsperQuery =
                measureService.findMeasure(requestedKpi.computeKPIEsperKey(_entity));
        return new KPIFacade<TEntity>(metricWrapperOverEsperQuery, _entity, requestedKpi,
                measureService);
        
    }
    
    
    @Override
    public Kpi findKPIOrFail(final IEntity _entity, final String _kpiName) {
    
    
        final Kpi findKPI = findKPI(_entity, _kpiName);
        if (findKPI == null) { throw new KPINotFoundRuntimeException(_entity, _kpiName); }
        return findKPI;
    }
    
    
    public ICronRegistryService getCronRegistry() {
    
    
        return cronRegistry;
    }
    
    
    /**
     * @return the esperEngine
     */
    public final IEsperEngine getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public KpiDao getKpiDAO() {
    
    
        return kpiDAO;
    }
    
    
    @Override
    public List<Kpi> getListOfKpisOfEntity(final IEntity _entity) {
    
    
        final List<Kpi> kpis = new ArrayList<Kpi>();
        final KpiCriteria allKpisFromEntityType = new KpiCriteria();
        allKpisFromEntityType.createCriteria().andEntityTypeEqualTo(_entity.getType())
                .andEntityIDIsNull();
        
        kpis.addAll(kpiDAO.selectByExampleWithBLOBs(allKpisFromEntityType));
        final KpiCriteria allKpisOnlyEntity = new KpiCriteria();
        allKpisOnlyEntity.createCriteria().andEntityTypeEqualTo(_entity.getType())
                .andEntityIDEqualTo(_entity.getId());
        kpis.addAll(kpiDAO.selectByExampleWithBLOBs(allKpisOnlyEntity));
        return kpis;
    }
    
    
    /**
     * @return the measureService
     */
    public final IMeasureService getMeasureService() {
    
    
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
            kpiDAO.insert(_kpi);
        } else {
            kpiDAO.updateByPrimaryKey(_kpi);
        }
    }
    
    
    public void setCronRegistry(final ICronRegistryService _cronRegistry) {
    
    
        cronRegistry = _cronRegistry;
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
    public final void setMeasureService(final IMeasureService _measureService) {
    
    
        measureService = _measureService;
    }
    
    
    @Transactional
    @Override
    public void storeValueInHistory(final IEntity _entity, final Kpi _kpiName) {
    
    
        final Measure measure = new Measure();
        switch (_entity.getType()) {
            case PERSON:
                measure.setIdPerson(_entity.getId());
                break;
            case PERSONG_GROUP:
                measure.setIdPersonGroup(_entity.getId());
                break;
            case PROJECT:
                measure.setIdProject(_entity.getId());
                break;
        
        }
        measure.setDate(new Date());
        measure.setIdKpi(_kpiName.getId());
        measure.setValue(measureService.findMeasure(_kpiName.computeKPIEsperKey(_entity))
                .getDoubleValue());
        measureService.storeMeasure(measure);
        
    }
    
    
    @Override
    public void synchronizeEntityWithKomea(final IEntity _entity) {
    
    
        LOGGER.info("Updating / Refreshing Kpi statements of entity {}", _entity);
        final List<Kpi> listOfKpisOfEntity = getListOfKpisOfEntity(_entity);
        
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
    
    
}
