
package org.komea.product.backend.kpi;



import java.util.List;

import org.komea.product.backend.service.business.IEPMetric;
import org.komea.product.backend.service.business.IKPIFacade;
import org.komea.product.backend.service.kpi.IMeasureHistoryService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;



/**
 * This class defines a facade to manipulate a KPI.
 * 
 * @author sleroy
 * @param <T>
 */
public class KPIFacade<TEntity extends IEntity> implements IKPIFacade<TEntity>
{
    
    
    private final IEPMetric       metric;
    private final TEntity         entity;
    private final Kpi             requestedKPI;
    private final IMeasureHistoryService measureService;
    
    
    
    /**
     * Builds a KPI Facade.
     * 
     * @param _statement
     *            the metric
     * @param _entity
     *            the entity
     * @param _requestedKPI
     *            the requested KPI.
     */
    public KPIFacade(
            final IEPMetric _statement,
            final TEntity _entity,
            final Kpi _requestedKPI,
            final IMeasureHistoryService _measureService) {
    
    
        super();
        metric = _statement;
        entity = _entity;
        requestedKPI = _requestedKPI;
        measureService = _measureService;
        
    }
    
    
    @Override
    public TEntity getEntity() {
    
    
        return entity;
    }
    
    
    @Override
    public List<Measure> getHistory() {
    
    
        return measureService.getMeasures(entity, getKPI());
    }
    
    
    @Override
    public Kpi getKPI() {
    
    
        return requestedKPI;
    }
    
    
    @Override
    public IEPMetric getMetric() {
    
    
        return metric;
    }
    
    
}
