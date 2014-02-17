package org.komea.product.backend.service.kpi;

import java.util.List;
import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.database.dto.KpiTendancyDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;

public interface IKPIService {

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
     * Method that returns a single value for the *special* KPI that does not
     * render a table.
     *
     * @param _kpiKey the kpi key.
     * @param _alertValue
     * @return the kpi single value;
     */
    public Double getKpiSingleValue(KpiKey _kpiKey);

    /**
     * Method that returns a single value for the *special* KPI that does not
     * render a table.
     *
     * @param _kpiKey the kpi key.
     * @param _columnName the column name.
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
     * @param _kpi a new KPI.
     */
    public void saveOrUpdate(Kpi _kpi);

    /**
     * Store the real-time value in History
     *
     * @param _entity the entity.
     * @param _kpiName the kpi name.
     */
    public void storeValueInHistory(KpiKey _kpiKey);

    /**
     * Returns the kPI double value.
     *
     * @param _kpiName the kpi name
     * @param _entityID the entity id
     * @param _entityType the entity type.
     * @return the kpi double value.
     * @throws KPINotFoundException
     */
    <T> KPIValueTable<T> getKpiRealTimeValues(KpiKey _kpiKey) throws KPINotFoundException;

    /**
     * Returns the tendancy for a KPI.
     *
     * @param _measureKey the measure key.
     * @return the kpi tendancy.
     */
    KpiTendancyDto getKpiTendancy(KpiKey _measureKey);

    List<Kpi> getKpis(final EntityType entityType, final List<String> entityKeys);

}
