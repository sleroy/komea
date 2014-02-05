
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.service.business.IKPIFacade;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;



public interface IKPIService
{
    
    
    /**
     * Creates a new KPI.
     * 
     * @param _kpi
     *            a new KPI.
     */
    public void saveOrUpdate(Kpi _kpi);
    
    
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
    public <TEntity extends IEntity> IKPIFacade<TEntity> findKPIFacade(
            TEntity _entity,
            String _kpiName) throws KPINotFoundException;
    
    
    public <TEntity extends IEntity> List<Kpi> getListOfKpisOfEntity(TEntity _entity);
    
    
    /**
     * Creates a KPI, stores it and instantiate it Esper. Updates the cache with the query
     * 
     * @param _kpi
     *            kpi
     */
    public <TEntity extends IEntity> void synchronizeInEsper(TEntity _project);
    
    
    public <TEntity extends IEntity> void updateKPIOfEntity(TEntity _entity, List<Kpi> _singleton);
}
