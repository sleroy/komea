
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.KpiTendancyDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
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
     * Method that returns a single value for the *special* KPI that does not
     * render a table.
     * 
     * @param _kpiKey
     *            the kpi key.
     * @return the kpi single value;
     */
    public Double getKpiSingleValue(KpiKey _kpiKey);
    
    
    /**
     * Method that returns a single value for the *special* KPI that does not
     * render a table.
     * 
     * @param _kpiKey
     *            the kpi key.
     * @param _columnName
     *            the column name.
     * @return the kpi single value;
     */
    public Double getKpiSingleValue(KpiKey _kpiKey, String _columnName);
    
    
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
     */
    public void storeValueInHistory(KpiKey _kpiKey);
    
    
    /**
     * Returns the kPI double value.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return the kpi double value. * @throws KPINotFoundException
     */
    <T> KPIValueTable<T> getKpiRealTimeValues(KpiKey _kpiKey) throws KPINotFoundException;
    
    
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
     * Returns the tendancy for a KPI.
     * 
     * @param _measureKey
     *            the measure key.
     * @return the kpi tendancy.
     */
    KpiTendancyDto getKpiTendancy(KpiKey _measureKey);
    
}
