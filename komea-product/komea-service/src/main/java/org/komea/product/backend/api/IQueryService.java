/**
 *
 */

package org.komea.product.backend.api;



import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;



/**
 * This interface provides two main functionalities :
 * <ul>
 * <li>register a new query from a kpi and its formula</li>
 * <li>obtain the result from a query whatever the implementation is (ICEPQuery or IDynamicQuery)</li>
 * </ul>
 *
 * @author sleroy
 */
public interface IQueryService
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
     * Returns the esper statement of a KPI from the esper engine.
     *
     * @param _kpi
     *            the kpi
     * @return the statement or null.
     */
    public KpiResult evaluateRealTimeValues(Kpi _kpi);


    /**
     * Tests if this kpi has a dynamic query associated.
     *
     * @param _kpiChoice
     *            the kpi
     * @return true if the kpi formula implements a dynamic query.
     */
    public boolean isDynamicQuery(Kpi _kpiChoice);


    /**
     * Tests if this kpi has a event query associated.
     *
     * @param _kpiChoice
     *            the kpi
     * @return true if the kpi formula implements a event query.
     */
    public boolean isEventQuery(Kpi _kpiChoice);


    /**
     * Tests if the kpi has its query registered.
     *
     * @param _build
     *            the kpi
     * @return true if the query is registered.
     */
    public boolean isQueryOfKpiRegistered(Kpi _build);


    /**
     * Removes a query associated to a kpi.
     *
     * @param _kpi
     *            the kpi
     */
    public void removeQuery(Kpi _kpi);


    /**
     * Returns the query values from the kpi (by id.)
     */
    KpiResult evaluateRealTimeValues(Integer _kpiID);


    /**
     * Returns the query values of a kpi.
     */
    KpiResult evaluateRealTimeValues(String _kpiName);


}
