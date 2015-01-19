package org.komea.connectors.sdk.rest.impl;

import java.net.ConnectException;
import java.rmi.ServerException;

import javax.ws.rs.client.WebTarget;

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
