
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.service.business.IEPMetric;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiKey;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;



public interface IKPIService
{
    
    
    /**
     * Finds a KPI if existing.
     * 
     * @return the kpi./
     */
    public Kpi findKPI(KpiKey _kpiKey);
    
    
    /**
     * Finds a KPI or throws an exception
     * 
     * @return the KPI or an exception.
     */
    public Kpi findKPIOrFail(KpiKey _kpiKey);
    
    
    /**
     * Returns the list of measures
     * 
     * @param _kpiKey
     *            the kpiKey
     * @return the list measures
     */
    public List<Measure> getHistory(KpiKey _kpiKey);
    
    
    /**
     * Returns the list of measures
     * 
     * @param _kpiKey
     *            the kpiKey
     * @return the list measures
     */
    public List<Measure> getHistory(KpiKey _kpiKey, MeasureCriteria _criteria);
    
    
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
    public void storeValueInHistory(KpiKey _kpiKey);
    
    
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
    
    
    /**
     * Returns the kPI double value.
     * 
     * @param _kpiName
     *            the kpi name
     * @param _entityID
     *            the entity id
     * @param _entityType
     *            the entity type.
     * @return the kpi double value.
     * @throws KPINotFoundException
     */
    
    double getKpiDoubleValue(KpiKey _kpiKey) throws KPINotFoundException;
    
    
    /**
     * Returns the KPI value
     * 
     * @param _entity
     *            the entity
     * @param _kpi
     *            the kpi.
     * @return
     */
    IEPMetric getKPIValue(KpiKey _measureKey);
    
    
}
