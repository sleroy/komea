/**
 *
 */
package org.komea.event.kpi.impl;

import org.joda.time.DateTime;
import org.komea.event.generator.KpiRange;
import org.komea.event.storage.IEventDBFactory;

/**
 * This class defines a query.
 *
 * @author sleroy
 */
public interface IKpi {
	
	/**
	 * Computes the begin period, end period.
	 *
	 * @param _beginPeriod
	 *            the begin period
	 * @param _endPeriod
	 *            the end period
	 * @param _eventDBFactory
	 *            the event db factory
	 */
	public QueryResult compute(DateTime _beginPeriod, DateTime _endPeriod,
			IEventDBFactory _eventDBFactory);
	
	/**
	 * Returns the range the query is computed.
	 */
	KpiRange getRange();
}
