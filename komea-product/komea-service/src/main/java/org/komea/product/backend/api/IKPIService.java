
package org.komea.product.backend.api;


import java.util.List;

import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.SuccessFactor;
import org.komea.product.service.dto.KpiKey;

/**
 */
public interface IKPIService extends IGenericService<Kpi, Integer, KpiCriteria> {
    
    /**
     * Finds a KPI if existing.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return the kpi./
     */
    public Kpi findKPI(KpiKey _kpiKey);
    
    /**
     * Finds a KPI if existing.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return the kpi./
     */
    public Kpi findKPI(String _kpiKey);
    
    /**
     * Finds a KPI or throws an exception
     * 
     * @param _kpiKey
     *            KpiKey
     * @return the KPI or an exception.
     */
    public Kpi findKPIOrFail(KpiKey _kpiKey);
    
    /**
     * Finds a KPI or throws an exception
     * 
     * @param _kpiKey
     *            KpiKey
     * @return the KPI or an exception.
     */
    public Kpi findKPIOrFail(String _kpiKey);
    
    /**
     * @param _entityType
     * @return
     */
    public List<Kpi> getAllKpisOfEntityType(EntityType _entityType);
    
    /**
     * Creates a new KPI.
     * 
     * @param _kpi
     *            a new KPI.
     */
    @Override
    public void saveOrUpdate(Kpi _kpi);
    
    /**
     * @param kpi
     */
    void deleteKpi(Kpi kpi);
    
    List<Kpi> getBaseKpisOfGroupKpiKeys(List<String> groupKpiKeys);
    
    List<Kpi> getKpisForGroups(List<Kpi> simpleKpis);
    
    List<Kpi> getKpisOfGroupKpiKeys(List<String> groupKpiKeys, List<Kpi> kpis);
    
    /**
     * @param kpi
     * @param alertTypes
     * @param successFactors
     */
    void saveOrUpdateKpi(Kpi kpi, List<KpiAlertType> alertTypes, List<SuccessFactor> successFactors);
    
}
