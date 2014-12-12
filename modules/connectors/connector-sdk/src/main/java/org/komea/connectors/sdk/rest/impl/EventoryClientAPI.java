package org.komea.connectors.sdk.rest.impl;

import java.net.ConnectException;
import java.rmi.ServerException;

import javax.ws.rs.client.WebTarget;

public class EventoryClientAPI extends AbstractClientAPI {

	public EventoryClientAPI() {
		super();
	}

	public void purgeEvents(final String _eventType) throws ConnectException, ServerException {
		this.get("/storage/clear", _eventType);

	}

	@Override
	protected WebTarget prefixPath(final WebTarget _target) {

		return _target;
	}

}
