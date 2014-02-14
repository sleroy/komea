
package org.komea.product.backend.service.kpi;



import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.esper.reactor.KPINotFoundRuntimeException;
import org.komea.product.backend.service.business.IEPMetric;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.esper.EPMetric;
import org.komea.product.backend.service.esper.EPStatementResult;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dto.KpiTendancyDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.KpiKey;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.espertech.esper.client.EPStatement;



@Service
@Transactional
public final class KPIService implements IKPIService
{
    
    
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
    public Kpi findKPI(final KpiKey _kpiKey) {
    
    
        final KpiCriteria kpiCriteria = new KpiCriteria();
        kpiCriteria.createCriteria().andKpiKeyEqualTo(_kpiKey.getKpiName());
        if (_kpiKey.verifiyIfIsAssociateToEntity()) {
            kpiCriteria.createCriteria().andEntityIDEqualTo(_kpiKey.getEntityID());
            kpiCriteria.createCriteria().andEntityTypeEqualTo(_kpiKey.getEntityType());
        }
        return CollectionUtil.singleOrNull(kpiDAO.selectByExampleWithBLOBs(kpiCriteria));
        
    }
    
    
    @Override
    public Kpi findKPIOrFail(final KpiKey _kpiKey) {
    
    
        final Kpi findKPI = findKPI(_kpiKey);
        if (findKPI == null) { throw new KPINotFoundRuntimeException(_kpiKey.getKpiName()); }
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
    
    
    public KpiDao getKpiDAO() {
    
    
        return kpiDAO;
    }
    
    
    @Override
    public <TEntity> KPIValueTable<TEntity> getKpiRealTimeValues(final KpiKey _kpiKey)
            throws KPINotFoundException {
    
    
        final Kpi kpiOrFail = findKPIOrFail(_kpiKey);
        final EPStatement epStatement =
                esperEngine.getStatementOrFail(kpiOrFail.computeKPIEsperKey());
        final List<KpiLineValue> kpiLinesValues =
                EPStatementResult.build(epStatement).mapAPojoPerResultLine(KpiLineValue.class);
        return new KPIValueTable(kpiOrFail, kpiLinesValues);
        
        
    }
    
    
    @Override
    public Double getKpiSingleValue(final KpiKey _kpiKey) {
    
    
        final Kpi kpiOrFail = findKPIOrFail(_kpiKey);
        final EPStatement epStatement =
                esperEngine.getStatementOrFail(kpiOrFail.computeKPIEsperKey());
        
        
        return EPStatementResult.build(epStatement).singleResult();
    }
    
    
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
    
    
    @PostConstruct
    public void init() {
    
    
        //
        
    }
    
    
    @Override
    public List<Kpi> listAllKpis() {
    
    
        final List<Kpi> kpiList = kpiDAO.selectByCriteria(new KpiCriteria());
        return kpiList;
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
        
        final QueryDefinition queryDefinition = new QueryDefinition(_kpi);
        LOGGER.info("Updating Esper with the query [}", queryDefinition);
        esperEngine.createOrUpdateEPLQuery(queryDefinition);
        IEntity entity = null;
        if (_kpi.isIndividualKPI()) {
            
            entity = entityService.getEntityAssociatedToKpi(KpiKey.ofKpi(_kpi));
        }
        createOrUpdateHistoryCronJob(_kpi, entity);
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
    
    
        final Measure measure = initializeMeasureFromKPI(_kpiKey);
        final Kpi findKPI = findKPIOrFail(_kpiKey);
        measure.setValue(findMeasure(findKPI.computeKPIEsperKey()).getDoubleValue());
        measureService.storeMeasure(measure);
        final int purgeHistory = measureService.buildHistoryPurgeAction(findKPI).purgeHistory();
        LOGGER.debug("Purge history : {} items", purgeHistory);
        
    }
    
    
    private Measure initializeMeasureFromKPI(final KpiKey _kpiKey) {
    
    
        final Measure measure = new Measure();
        measure.setDate(new Date());
        measure.setEntityId(_kpiKey.getEntityType(), _kpiKey.getEntityID());
        
        measure.setIdKpi(findKPIOrFail(_kpiKey).getId());
        
        return measure;
    }
    
}
