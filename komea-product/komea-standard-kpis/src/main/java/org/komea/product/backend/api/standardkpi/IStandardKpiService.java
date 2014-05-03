/**
 * 
 */

package org.komea.product.backend.api.standardkpi;



import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 *
 */
public interface IStandardKpiService
{
    
    
    /**
     * @param _numberSuccessBuildPerDay
     */
    public abstract void saveOrUpdate(Kpi _kpi);
    
}
