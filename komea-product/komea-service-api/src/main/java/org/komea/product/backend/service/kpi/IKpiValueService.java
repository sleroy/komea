/**
 * 
 */

package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.KpiKey;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 *
 */
public interface IKpiValueService
{
    
    
    /**
     * Retuens the last measure of a kpi
     * 
     * @param _measureKey
     *            the kpi key
     * @param findKPIOrFail
     *            the kpi
     * @param entity
     *            the entity.
     * @return the measure
     */
    public abstract Measure getLastMeasureOfKpi(Kpi findKPIOrFail, IEntity entity);
    
    
    /**
     * @return the measureService
     */
    public abstract IHistoryService getMeasureService();
    
    
    public abstract Measure getRealTimeMeasure(KpiKey _key);
    
    
    public abstract List<Measure> getRealTimeMeasuresFromEntities(
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
    
    public abstract Number getSingleValue(KpiKey _kpiKey);
    
    
    /**
     * Stores the measure of a kpi in the database
     * 
     * @param _kpiKey
     *            the kpi key (with reference to the entity)
     * @param _kpiValue
     *            the value.
     */
    
    public abstract void storeMeasureOfAKpiInDatabase(KpiKey _kpiKey, Number _kpiValue);
    
    
    /**
     * Method storeValueInHistory.
     * 
     * @param _kpiKey
     *            KpiKey
     * @throws KPINotFoundException
     * @see org.komea.product.cep.tester.IKPIService#storeValueInHistory(KpiKey)
     */
    public abstract void storeValueInHistory(KpiKey _kpiKey) throws KPINotFoundException;
    
}
