package org.komea.connectors.jira;

import java.io.IOException;
import java.util.Map;

import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;

import com.google.common.collect.Maps;

public class CountingStorage implements IEventStorage {

	public Map<String, Integer>	counters;

	public CountingStorage() {

		this.counters = Maps.newHashMap();
	}

	@Override
	public void clearEventsOfType(final String _eventType) {

		// TODO Auto-generated method stub
	}

	@Override
	public void close() throws IOException {

		// TODO Auto-generated method stub
	}

	@Override
	public void declareEventType(final String type) {

		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.event.storage.IEventStorage#existStorage(java.lang.String)
	 */
	@Override
	public boolean existStorage(final String _eventType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IEventDB getEventDB(final String eventType) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void storeEvent(final KomeaEvent _event) {
		this.increment(_event.getEventType());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.event.storage.IEventStorage#storeEvent(java.lang.Object)
	 */
	@Override
	public void storeEvent(final Object _object) {
		// TODO Auto-generated method stub

	}

	private void increment(final String type) {

		Integer current = this.counters.get(type);
		if (current == null) {
			current = 0;
		}
		this.counters.put(type, current + 1);
	}

}
