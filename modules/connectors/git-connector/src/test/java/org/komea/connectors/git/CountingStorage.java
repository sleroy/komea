package org.komea.connectors.git;

import java.io.IOException;

import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;

public class CountingStorage implements IEventStorage {

	public int	count;

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

		this.count++;
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

}
