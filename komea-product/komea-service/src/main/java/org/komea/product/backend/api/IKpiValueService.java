/**
 *
 */

package org.komea.product.backend.api;



import org.komea.product.database.dto.KpiResult;
import org.komea.product.service.dto.KpiKey;



/**
 * @author sleroy
 */
public interface IKpiValueService
{
    
    
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
    
    
}
