package org.komea.connectors.git;

import java.io.IOException;
import java.util.List;

import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;

import com.google.common.collect.Lists;

public class BasicEventsListStorage implements IEventStorage {

	List<KomeaEvent>	events	= Lists.newArrayList();

	@Override
	public void clearEventsOfType(final String _eventType) {

	}

	@Override
	public void close() throws IOException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.event.storage.IEventStorage#declareEventType(java.lang.String)
	 */
	@Override
	public void declareEventType(final String _type) {
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

	public List<KomeaEvent> getEvents() {

		return this.events;
	}

	@Override
	public void storeEvent(final KomeaEvent _event) {
		this.events.add(_event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.event.storage.IEventStorage#storeEvent(java.lang.Object)
	 */
	@Override
	public void storeEvent(final Object _object) {
		this.storeEvent(new KomeaEvent(_object));

	}

}
