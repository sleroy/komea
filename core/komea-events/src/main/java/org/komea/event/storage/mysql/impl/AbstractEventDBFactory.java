package org.komea.event.storage.mysql.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;

public abstract class AbstractEventDBFactory implements IEventDBFactory {

	private final ThreadLocal<Map<String, IEventDB>>	eventsDB	= new ThreadLocal<Map<String, IEventDB>>() {
		@Override
		protected java.util.Map<String, IEventDB> initialValue() {
			return new HashMap<String, IEventDB>();
		}
	};

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	 @Override
	 public final void close() throws IOException {

		final Map<String, IEventDB> map = eventsDB.get();
		for (final IEventDB db : map.values()) {
			db.close();
		}

		eventsDB.remove();

		closeStorage();

	}

	@Override
	public final IEventDB getEventDB(final String _eventType) {
		final Map<String, IEventDB> map = eventsDB.get();
		IEventDB iEventDB = map.get(_eventType);
		if (iEventDB == null) {
			final IEventDB newEventDB = newEventDB(_eventType);
			map.put(_eventType, newEventDB);
			iEventDB = newEventDB;
		}

		return iEventDB;
	}

	/**
	 * Invokes to close the storage
	 *
	 * @throws IOException
	 */
	protected abstract void closeStorage() throws IOException;

	/**
	 * Creates a new event db
	 *
	 * @param _eventType
	 *            the event type
	 * @return the new event db.
	 */
	protected abstract IEventDB newEventDB(final String _eventType);

}
