
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.KpiKey;



/**
 */
public interface IKPIService extends IGenericService<Kpi, Integer, KpiCriteria>
{
    
    
    /**
     * Finds a KPI if existing.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return the kpi./
     */
    public Kpi findKPI(KpiKey _kpiKey);
    
    
    /**
     * Finds a KPI or throws an exception
     * 
     * @param _kpiKey
     *            KpiKey
     * @return the KPI or an exception.
     */
    public Kpi findKPIOrFail(KpiKey _kpiKey);
    
    
    /**
     * Returns a list of measures from a list of kpi and a list of entities.
     * 
     * @param _kpis
     *            the list of kpis
     * @param _entities
     *            the entities.
     * @return the list of measures.
     */
    public List<Measure> getRealTimeMeasuresFromEntities(
            List<Kpi> _kpis,
            List<BaseEntityDto> _entities);
    
    
    /**
     * Method that returns a single value for the *special* KPI that does not
     * render a table.
     * 
     * @param _kpiKey
     *            the kpi key.
     * @return the kpi single value;
     */
    public Number getSingleValue(KpiKey _kpiKey);
    
    
    /**
     * This method return the complete list of KPIs
     * 
     * @return the kpi list
     */
    public List<Kpi> listAllKpis();
    
    
    /**
     * Creates a new KPI.
     * 
     * @param _kpi
     *            a new KPI.
     */
    @Override
    public void saveOrUpdate(Kpi _kpi);
    
    
    /**
     * Store the real-time value in History
     * 
     * @param _kpiKey
     *            KpiKey
     * @throws KPINotFoundException
     */
    public void storeValueInHistory(KpiKey _kpiKey) throws KPINotFoundException;
    
    
    /**
     * Method getKpis.
     * 
     * @param entityType
     *            EntityType
     * @param entityKeys
     *            List<String>
     * @return List<Kpi>
     */
    List<Kpi> getKpis(final EntityType entityType, final List<String> entityKeys);
    
    
    /**
     * Get last Measure of a Kpi for an entity from Esper
     * 
     * @param _kpi
     *            kpi
     * @param entity
     *            entity
     * @return measure or null if value does not exist
     */
    Measure getRealTimeMeasure(KpiKey _kpi);
    
    
    /**
     * Refresh esper with a KPI. The esper statement will be either created or updated and the cron job updated as well.
     * 
     * @param _kpi
     *            the kpi.
     */
    void refreshEsper(Kpi _kpi);
    
    
    /**
     * Stores a new value for a kpi in its history. The measure is created at the current date.
     * 
     * @param _kpiKey
     *            the kpi key
     * @param _kpiValue
     *            the value.
     */
    void storeMeasureOfAKpiInDatabase(KpiKey _kpiKey, Number _kpiValue);
    
}
