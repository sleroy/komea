
package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.esper.reactor.QueryDefinition;
import org.komea.product.backend.kpi.KPIFacade;
import org.komea.product.backend.service.business.IKPIFacade;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public final class KPIService implements IKPIService
{
    
    
    @Autowired
    private IMeasureService     measureService;
    
    
    @Autowired
    private IMetricService      metricService;
    
    
    @Autowired
    private IEsperEngine        esperEngine;
    
    @Autowired
    private KpiDao              kpiDAO;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KPIService.class);
    
    
    
    public KPIService() {
    
    
        super();
    }
    
    
    @Override
    public Kpi findKPI(final String _kpiName) {
    
    
        final KpiCriteria kpiCriteria = new KpiCriteria();
        kpiCriteria.createCriteria().andKpiKeyEqualTo(_kpiName);
        return CollectionUtil.singleOrNull(kpiDAO.selectByCriteria(kpiCriteria));
        
    }
    
    
    @Override
    public <TEntity extends IEntity> IKPIFacade<TEntity> findKPIFacade(
            final TEntity _entity,
            final String _kpiName) throws KPINotFoundException {
    
    
        LOGGER.debug("Returning KPI facade from entity {} and kpi {}", _entity, _kpiName);
        final KpiCriteria criteria = new KpiCriteria();
        criteria.createCriteria().andKpiKeyEqualTo(_kpiName);
        final Kpi requestedKpi = CollectionUtil.singleOrNull(kpiDAO.selectByCriteria(criteria));
        
        if (requestedKpi == null) { throw new KPINotFoundException(_entity, _kpiName); }
        final IEPMetric metricWrapperOverEsperQuery =
                metricService.findMeasure(requestedKpi.computeKPIEsperKey(_entity));
        return new KPIFacade<TEntity>(metricWrapperOverEsperQuery, _entity, requestedKpi,
                measureService);
        
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
    public <TEntity extends IEntity> List<Kpi> getListOfKpisOfEntity(final TEntity _entity) {
    
    
        final List<Kpi> kpis = new ArrayList<Kpi>();
        final KpiCriteria allKpisFromEntityType = new KpiCriteria();
        allKpisFromEntityType.createCriteria().andEntityTypeEqualTo(_entity.getType())
                .andEntityIDIsNull();
        
        kpis.addAll(kpiDAO.selectByCriteria(allKpisFromEntityType));
        final KpiCriteria allKpisOnlyEntity = new KpiCriteria();
        allKpisOnlyEntity.createCriteria().andEntityTypeEqualTo(_entity.getType())
                .andEntityIDEqualTo(_entity.getId());
        kpis.addAll(kpiDAO.selectByCriteria(allKpisOnlyEntity));
        return kpis;
    }
    
    
    /**
     * @return the measureService
     */
    public final IMeasureService getMeasureService() {
    
    
        return measureService;
    }
    
    
    /**
     * @return the metricService
     */
    public final IMetricService getMetricService() {
    
    
        return metricService;
    }
    
    
    @Transactional
    @Autowired
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
    
    
    /**
     * @param _metricService
     *            the metricService to set
     */
    public final void setMetricService(final IMetricService _metricService) {
    
    
        metricService = _metricService;
    }
    
    
    @Override
    public <TEntity extends IEntity> void synchronizeInEsper(final TEntity _entity) {
    
    
        LOGGER.info("Updating / Refreshing Kpi statements of entity {}", _entity);
        final List<Kpi> listOfKpisOfEntity = getListOfKpisOfEntity(_entity);
        
        LOGGER.info("EntityWithKPI {} has {} kpi", _entity, listOfKpisOfEntity.size());
        for (final Kpi kpi : listOfKpisOfEntity) {
            final String computeKPIEsperKey = kpi.computeKPIEsperKey(_entity);
            esperEngine.createOrUpdateEPL(new QueryDefinition(kpi, computeKPIEsperKey));
        }
        
    }
    
    
    @Transactional
    @Override
    public <TEntity extends IEntity> void updateKPIOfEntity(
            final TEntity _entity,
            final List<Kpi> listOfKpis) {
    
    
        // Ignore silently global kpi...
        for (final Kpi kpi : listOfKpis) {
            if (kpi.getEntityID() == null) {
                continue;
            }
            saveOrUpdate(kpi);
        }
        
        
    }
    
    
}
