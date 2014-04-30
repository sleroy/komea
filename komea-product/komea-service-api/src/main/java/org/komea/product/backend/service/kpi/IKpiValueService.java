/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
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
    public Measure getLastMeasureOfKpi(Kpi findKPIOrFail, IEntity entity);
    
    
    /**
     * Get the real time value of a kpi.
     * 
     * @param _key
     *            the kpi key.
     * @return
     */
    public Measure getRealTimeMeasure(KpiKey _key);
    
    
    /**
     * Get the real times measures of some entities;
     * 
     * @param kpis
     * @param entities
     * @return
     */
    public List<MeasureDto> getRealTimeMeasuresFromEntities(
            List<Kpi> kpis,
            List<BaseEntityDto> entities);
    
    
    /**
     * Method getKpiSingleValue.
     * 
     * @param _kpiKey
     *            KpiKey
     * @return Number
     * @see org.komea.product.cep.tester.IKPIService#getSingleValue(KpiKey)
     */
    public Number getSingleValue(KpiKey _kpiKey);
    
    
    /**
     * This method stores the real times values of a kpi (ex table of entity,
     * double values) into the history.
     * 
     * @param _kpiKey
     *            KpiKey
     * @throws KPINotFoundException
     * @see org.komea.product.cep.tester.IKPIService#storeValueInHistory(KpiKey)
     */
    public void storeValueInHistory(KpiKey _kpiKey) throws KPINotFoundException;
    
    
}
