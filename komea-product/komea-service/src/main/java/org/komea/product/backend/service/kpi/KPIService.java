
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.kpi.KPIFacade;
import org.komea.product.backend.service.business.IEntityWithKPI;
import org.komea.product.backend.service.business.IKPIFacade;
import org.komea.product.backend.utils.CollectionUtil;
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
    
    
    /**
     * Returns a facade to manipulate a KPI.
     */
    @Override
    public <T> IKPIFacade<T> findKPIFacade(final IEntityWithKPI<T> _entity, final String _kpiName)
            throws KPINotFoundException {
    
    
        LOGGER.debug("Returning KPI facade from entity {} and kpi {}", _entity, _kpiName);
        
        final Kpi requestedKpi = _entity.getKpi(_kpiName);
        
        if (requestedKpi == null) { throw new KPINotFoundException(_entity, _kpiName); }
        final IEPMetric metricWrapperOverEsperQuery =
                metricService.findMeasure(requestedKpi.computeKPIEsperKey(_entity.getId()));
        return new KPIFacade<T>(metricWrapperOverEsperQuery, _entity, requestedKpi.getKpiKey(),
                measureService);
        
    }
    
    
    /**
     * @return the esperEngine
     */
    public final IEsperEngine getEsperEngine() {
    
    
        return esperEngine;
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
    public void newKPI(final Kpi _kpi) {
    
    
        kpiDAO.insert(_kpi);
    }
    
    
    /**
     * @param _esperEngine
     *            the esperEngine to set
     */
    public final void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
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
    public void synchronizeInEsper(final IEntityWithKPI<?> _entity) {
    
    
        LOGGER.info("Updating / Refreshing Kpi statements of entity {}", _entity);
        
        final List<Kpi> kpiOfEntity = _entity.getListofKpis();
        LOGGER.info("EntityWithKPI {} has {} kpi", _entity, kpiOfEntity.size());
        for (final Kpi kpi : kpiOfEntity) {
            final String computeKPIEsperKey = kpi.computeKPIEsperKey(_entity.getId());
            esperEngine.createOrUpdateEPL(kpi.getEsperRequest(), computeKPIEsperKey);
        }
        
    }
}
