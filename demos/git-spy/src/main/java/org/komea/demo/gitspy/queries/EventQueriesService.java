/**
 *
 */
package org.komea.demo.gitspy.queries;

import java.util.List;

import org.joda.time.DateTime;
import org.komea.demo.gitspy.eventory.api.IEventoryConnectorService;
import org.komea.demo.gitspy.queries.api.IEventQueries;
import org.komea.demo.gitspy.widgets.toppanel.TopRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class defines the service that implements the event queries.
 *
 * @author sleroy
 *
 */
@Service
public class EventQueriesService implements IEventQueries {
	@Autowired
	private IEventoryConnectorService	eventConnectoryService;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.demo.gitspy.queries.api.IEventQueries#getTopCommiters(int,
	 * org.joda.time.DateTime)
	 */
	@Override
	public List<TopRecord> getTopCommiters(final int _maxEntries,
	                                       final DateTime _sinceTime) {
		// TODO Auto-generated method stub
		return null;
	}

}
