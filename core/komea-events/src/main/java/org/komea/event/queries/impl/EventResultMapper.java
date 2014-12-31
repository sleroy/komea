/**
 *
 */
package org.komea.event.queries.impl;

import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.ResultMapper;
import org.komea.event.queries.rows.IRow;
import org.komea.event.queries.rows.impl.PojoRow;

/**
 * This result mapper returns the events matching the clause as rows.
 *
 * @author sleroy
 */
public class EventResultMapper implements ResultMapper<FlatEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.ResultMapper#begin()
	 */
	@Override
	public void begin() {
		//
	}

	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.ResultMapper#end()
	 */
	@Override
	public IRow end() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.ResultMapper#process(java.lang.Object)
	 */
	@Override
	public IRow process(final FlatEvent _event) {
		return new PojoRow(_event);
	}

}
