/**
 *
 */

package org.komea.product.backend.api;



import java.util.List;

import org.joda.time.DateTime;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.KpiKey;



/**
 * @author sleroy
 */
public interface IKpiValueService
{
    
    
    /**
     * Backup the kpi values into the history.
     */
    public void backupKpiValuesIntoHistory();
    
    
    /**
     * Returns a list of measures from a list of kpi and a list of entities.
     * 
     * @param _kpis
     *            the list of kpis
     * @param _subEntitiesDto
     *            the entities.
     * @return the list of measures.
     */
    public List<MeasureDto> getAllRealTimeMeasuresPerEntityAndPerKpi(
            List<Kpi> _kpis,
            List<? extends IEntity> _subEntitiesDto);
    
    
    /**
     * Returns the last measure of a kpi
     * 
     * @param _measureKey
     *            the kpi key
     * @param findKPIOrFail
     *            the kpi
     * @param entity
     *            the entity.
     * @return the measure
     */
    public Measure getLastMeasureInHistoryOfAKpi(HistoryKey _key);
    
    
    /**
     * Returns the minimal value for a kpi.
     * 
     * @param _kpiID
     *            the kpi ID.
     * @return
     */
    public Double getMinimalValueForAKpi(Integer _kpiID);
    
    
    /**
     * @param _kpiName
     * @return
     */
    public KpiResult getRealTimeValue(String _kpiName);
    
    
    /**
     * Method getKpiSingleValue.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return Number
     * @see org.komea.product.backend.api.IKPIService#getSingleValue(KpiKey)
     */
    public Number getSingleValue(KpiKey _kpiKey);
    
    
    /**
     * This method stores the real times values of a kpi (ex table of entity,
     * double values) into the history.
     * 
     * @param _kpiKey
     *            KpiKey
     * @throws KPINotFoundException
     * @see org.komea.product.backend.api.IKPIService#storeValueInHistory(KpiKey)
     */
    public void storeValueInHistory(KpiKey _kpiKey) throws KPINotFoundException;
    
    
    /**
     * Stores a value directly into the kpi history.
     * 
     * @param _kpiKey
     *            the kpi key
     * @param _value
     *            the value.
     */
    public void storeValueInKpiHistory(KpiKey _kpiKey, Number _value);
    
    
    /**
     * Stores a value into the history for an entity and a given time.
     * 
     * @param _kpiKey
     * @param _value
     * @param _dateTime
     */
    public void storeValueInKpiHistory(KpiKey _kpiKey, Number _value, DateTime _dateTime);
    
    
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
    
}
