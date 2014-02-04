
package org.komea.product.backend.kpi;



import java.util.List;

import org.komea.product.backend.service.business.IEntityWithKPI;
import org.komea.product.backend.service.business.IKPIFacade;
import org.komea.product.backend.service.kpi.IEPMetric;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;



/**
 * This class defines a facade to manipulate a KPI.
 * 
 * @author sleroy
 * @param <T>
 */
public class KPIFacade<T> implements IKPIFacade<T>
{
    
    
    private final IEPMetric         metric;
    private final IEntityWithKPI<T> entity;
    private final String            requestedKPI;
    private final IMeasureService   measureService;
    
    
    
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
            final IEntityWithKPI<T> _entity,
            final String _requestedKPI,
            final IMeasureService _measureService) {
    
    
        super();
        metric = _statement;
        entity = _entity;
        requestedKPI = _requestedKPI;
        measureService = _measureService;
        
    }
    
    
    @Override
    public IEntityWithKPI<T> getEntity() {
    
    
        return entity;
    }
    
    
    @Override
    public List<Measure> getHistory() {
    
    
        return measureService.getMeasures(entity, getKPI());
    }
    
    
    @Override
    public Kpi getKPI() {
    
    
        return entity.getKpi(requestedKPI);
    }
    
    
    @Override
    public IEPMetric getMetric() {
    
    
        return metric;
    }
    
    
}
