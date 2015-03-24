/**
 *
 */
package org.komea.demo.gitspy.queries.api;

import java.util.List;

import org.joda.time.DateTime;
import org.komea.demo.gitspy.widgets.toppanel.TopRecord;

/**
 * @author sleroy
 *
 */
public interface IEventQueries {
	/**
	 * Returns the list of the best commiers
	 * @param _maxEntries the max entries
	 * @param _sinceTime the period to include in the analysis
	 * @return the best commiters
	 */
	List<TopRecord> getTopCommiters(int _maxEntries, DateTime _sinceTime);
}
