
package org.komea.product.backend.service.kpi;



import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.service.business.IEntityWithKPI;
import org.komea.product.backend.service.business.IKPIFacade;
import org.komea.product.database.model.Kpi;



public interface IKPIService
{
    
    
    /**
     * Finds a KPI if existing.
     * 
     * @param _kpiName
     *            the kpi name
     * @return the kpi./
     */
    public Kpi findKPI(String _kpiName);
    
    
    /**
     * Finds the metric with the given name.
     * 
     * @return the metric with the given name.
     * @throws KPINotFoundException
     */
    public <T> IKPIFacade<T> findKPIFacade(IEntityWithKPI<T> _entity, String _kpiName)
            throws KPINotFoundException;
    
    
    /**
     * Creates a new JKPI.
     * 
     * @param _kpi
     *            a new KPI.
     */
    public void newKPI(Kpi _kpi);
    
    
    /**
     * Creates a KPI, stores it and instantiate it Esper. Updates the cache with the query
     * 
     * @param _kpi
     *            kpi
     */
    public void synchronizeInEsper(IEntityWithKPI<?> _entity);
}
