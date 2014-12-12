package org.komea.connectors.sdk.rest.impl;

import java.net.ConnectException;
import java.rmi.ServerException;

import javax.ws.rs.client.WebTarget;

public class EventoryClientAPI extends AbstractClientAPI {

	public EventoryClientAPI() {
		super();
	}

	public Integer countEvents(final String _eventType) throws ConnectException, ServerException {

		return this.get("/database/count", Integer.class, _eventType);
	}

	public void purgeEvents(final String _eventType) throws ConnectException, ServerException {
		this.get("/storage/clear", _eventType);

	}

	public void testConnexion() throws ConnectException, ServerException {
		this.get("hello");

	}

	@Override
	protected WebTarget prefixPath(final WebTarget _target) {

		return _target;
	}
}
