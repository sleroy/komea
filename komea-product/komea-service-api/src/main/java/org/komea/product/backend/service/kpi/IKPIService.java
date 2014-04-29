package org.komea.product.backend.service.kpi;

import java.util.List;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
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
     * Finds a KPI or throws an exception
     *
     * @param _kpiKey KpiKey
     * @return the KPI or an exception.
     */
    public Kpi findKPIOrFail(KpiKey _kpiKey);

    /**
     * Returns a list of measures from a list of kpi and a list of entities.
     *
     * @param _kpis the list of kpis
     * @param _entities the entities.
     * @return the list of measures.
     */
    public List<Measure> getRealTimeMeasuresFromEntities(
            List<Kpi> _kpis,
            List<BaseEntityDto> _entities);

    /**
     * Method that returns a single value for the *special* KPI that does not
     * render a table.
     *
     * @param _kpiKey the kpi key.
     * @return the kpi single value;
     */
    public Number getSingleValue(KpiKey _kpiKey);

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

    /**
     * Method getKpisForGroups.
     *
     * @param entityType EntityType
     * @param entityKeys List<String>
     * @return List<Kpi>
     */
    List<Kpi> getKpis(EntityType entityType, List<String> entityKeys);

    List<Kpi> getKpisForGroups(List<Kpi> simpleKpis);

    /**
     * Get last Measure of a Kpi for an entity from Esper
     *
     * @param _kpi kpi
     * @param entity entity
     * @return measure or null if value does not exist
     */
    Measure getRealTimeMeasure(KpiKey _kpi);

    /**
     * @param kpi
     * @param alertTypes
     * @param successFactors
     */
    void saveOrUpdateKpi(Kpi kpi, List<KpiAlertType> alertTypes, List<SuccessFactor> successFactors);

    List<Kpi> getBaseKpisOfGroupKpiKeys(EntityType entityType, List<String> groupKpiKeys);

    List<Kpi> getKpisOfGroupKpiKeys(List<String> groupKpiKeys, List<Kpi> kpis);

}
