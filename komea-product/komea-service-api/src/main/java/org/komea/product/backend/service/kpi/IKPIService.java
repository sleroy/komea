
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.service.business.IKPIFacade;
import org.komea.product.database.api.IEntity;
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
    public Kpi findKPI(IEntity _entity, String _kpiName);
    
    
    /**
     * Finds the metric with the given name.
     * 
     * @return the metric with the given name.
     * @throws KPINotFoundException
     */
    public <TEntity extends IEntity> IKPIFacade<TEntity> findKPIFacade(
            TEntity _entity,
            String _kpiName) throws KPINotFoundException;
    
    
    /**
     * Finds a KPI or throws an exception
     * 
     * @param _alertReceivedInOneDay
     *            the KPI name
     * @return the KPI or an exception.
     */
    public Kpi findKPIOrFail(IEntity _entity, String _alertReceivedInOneDay);
    
    
    /**
     * Returns the list of kpifs for an entity.
     * 
     * @param _entity
     *            the entity
     * @return the list of KPI.
     */
    public List<Kpi> getListOfKpisForEntity(IEntity _entity);
    
    
    /**
     * Creates a new KPI.
     * 
     * @param _kpi
     *            a new KPI.
     */
    public void saveOrUpdate(Kpi _kpi);
    
    
    /**
     * Store the real-time value in History
     * 
     * @param _entity
     *            the entity.
     * @param _kpiName
     *            the kpi name.
     */
    public void storeValueInHistory(IEntity _entity, Kpi _kpiName);
    
    
    /**
     * Creates a KPI, stores it and instantiate it Esper. Updates the cache with the query
     * 
     * @param _kpi
     *            kpi
     */
    public void synchronizeEntityWithKomea(IEntity _project);
    
    
    /**
     * Updates all the KPI related to an entioty.
     * 
     * @param _entity
     *            the entity
     * @param _kpis
     *            the new list of KPI
     */
    public void updateKPIOfEntity(IEntity _entity, List<Kpi> _kpis);
}
