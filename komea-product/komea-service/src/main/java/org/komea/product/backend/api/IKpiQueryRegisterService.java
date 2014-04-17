/**
 * 
 */

package org.komea.product.backend.api;



import org.komea.eventory.api.formula.ICEPResult;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 */
public interface IKpiQueryRegisterService
{
    
    
    /**
     * This methods registers in the CEP Engine a new query from a kpi.
     * 
     * @param _kpi
     *            the kpi
     * @return the query definition.
     */
    public IQueryDefinition createEsperQueryFromKPI(Kpi _kpi);
    
    
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
    
    
    /**
     * Refresh esper with a KPI. The esper statement will be either created or
     * updated and the cron job updated as well.
     * 
     * @param _kpi
     *            the kpi.
     */
    public void registerOrUpdateQueryFromKpi(Kpi _kpi);
    
}
