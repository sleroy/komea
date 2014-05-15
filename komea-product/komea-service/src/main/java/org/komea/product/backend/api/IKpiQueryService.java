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
 * <li>obtain the result from a query whatever the implementation is (ICEPQuery
 * or IDynamicQuery)</li>
 * </ul>
 * 
 * @author sleroy
 */
public interface IKpiQueryService {

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
	public KpiResult getQueryValueFromKpi(Kpi _kpi);

	/**
	 * Removes a query associated to a kpi.
	 * 
	 * @param _kpi
	 *            the kpi
	 */
	public void removeQuery(Kpi _kpi);

}
