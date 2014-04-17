/**
 * 
 */

package org.komea.product.backend.api;



import org.komea.eventory.api.formula.ICEPResult;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;



/**
 * This interface the registry of query.
 * 
 * @author sleroy
 */
public interface IKpiQueryRegisterService
{
    
    
    /**
     * Creates of update the history job of a KPI
     * 
     * @param _kpi
     *            the kpi
     * @param _entity
     *            its entity.
     */
    public void createOrUpdateHistoryCronJob(Kpi _kpi, IEntity _entity);
    
    
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
    public ICEPResult getQueryValueFromKpi(Kpi _kpi);
    
    
    /**
     * Prepare Kpi History Job
     * 
     * @param _kpi
     *            the kpi
     * @param _entity
     *            the entity
     * @param kpiCronName
     *            the kpi cron name;
     */
    public void prepareKpiHistoryJob(Kpi _kpi, IEntity _entity, String kpiCronName);
    
}
