/**
 * 
 */

package org.komea.product.backend.api;



import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;



/**
 * This interface the registry of query.
 * 
 * @author sleroy
 */
public interface IKpiQueryRegisterService
{
    
    
    /**
     * Refresh esper with a KPI. The esper statement will be either created or
     * updated and the cron job updated as well.
     * 
     * @param _kpi
     *            the kpi.
     */
    public void createOrUpdateQueryFromKpi(Kpi _kpi);
    
    
    /**
     * This methods registers in the CEP Engine a new query from a kpi.
     * 
     * @param _kpi
     *            the kpi
     * @return the query definition.
     */
    public void evaluateFormulaAndRegisterQuery(Kpi _kpi);
    
    
    /**
     * Returns the esper statement of a KPI from the esper engine.
     * 
     * @param _kpi
     *            the kpi
     * @return the statement or null.
     */
    public KpiResult getQueryValueFromKpi(Kpi _kpi);
    
    
}
