package org.komea.product.backend.api;

import java.util.List;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
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
     * @param _kpiKey KpiKey
     * @return the kpi./
     */
    public Kpi findKPI(KpiKey _kpiKey);

    /**
     * Finds a KPI if existing.
     *
     * @param _kpiKey KpiKey
     * @return the kpi./
     */
    public Kpi findKPI(String _kpiKey);

    /**
     * Finds a KPI or throws an exception
     *
     * @param _kpiKey KpiKey // * @return the KPI or an exception.
     */
    public Kpi findKPIOrFail(KpiKey _kpiKey);

    /**
     * Finds a KPI or throws an exception
     *
     * @param _kpiKey KpiKey
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
     * @param _kpi a new KPI.
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

    List<Kpi> getKpisOfProviderType(ProviderType providerType);

    /**
     * Delete all measures of a kpi. Warn : measures can be shared for multiple
     * kpis, be sure you want delete these measures. Use method
     * isKpiFormulaShared(kpi) to know if this kpi shared its formula with
     * others kpis.
     *
     * @param _kpi kpi
     * @return number of deleted measures
     */
    int purgeHistoryOfKpi(Kpi _kpi);

    /**
     *
     * @param _kpi kpi
     * @return true if the formula of this kpi is shared with another kpi, false
     * otherwise
     */
    boolean isKpiFormulaShared(Kpi _kpi);

    /**
     *
     * @param _kpi kpi
     * @return number of measures of a kpi
     */
    int countMeasuresOfKpi(Kpi _kpi);

    /**
     * @param kpi
     * @param alertTypes
     * @param successFactors
     */
    void saveOrUpdateKpi(Kpi kpi, List<KpiAlertType> alertTypes, List<SuccessFactor> successFactors);

}
