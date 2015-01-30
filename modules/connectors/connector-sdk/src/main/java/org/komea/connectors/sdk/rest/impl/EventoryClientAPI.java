package org.komea.connectors.sdk.rest.impl;

import java.net.ConnectException;
import java.rmi.ServerException;
import java.util.Date;

import javax.ws.rs.client.WebTarget;

import org.joda.time.DateTime;
import org.komea.event.storage.IEventStorage;

public class EventoryClientAPI extends AbstractClientAPI implements
		IEventoryClientAPI {

	public EventoryClientAPI() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI#countEvents(java
	 * .lang.String)
	 */
	@Override
	public Integer countEvents(final String _eventType)
			throws ConnectException, ServerException {

		return this.get("/database/count", Integer.class, _eventType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI#getEventStorage()
	 */
	@Override
	public IEventStorage getEventStorage() {

		return new EventStorageRestAPI(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI#getLastEvent(java
	 * .lang.String)
	 */
	@Override
	public DateTime getLastEvent(final String _eventTypeName) {
		Date date = null;
		try {
			date = this.get("/storage/last", Date.class, _eventTypeName);

		} catch (ConnectException | ServerException e) {
			LOGGER.error(e.getMessage(), e);
		}
		if (date == null) {
			return null;
		}
		return new DateTime(date);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI#testConnexion()
	 */
	@Override
	public void testConnexion() throws ConnectException, ServerException {
		LOGGER.info(this.get("/hello", String.class));

	}

	@Override
	protected WebTarget prefixPath(final WebTarget _target) {

		return _target;
	}
}
