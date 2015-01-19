/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import java.net.ConnectException;
import java.rmi.ServerException;

import org.komea.connectors.sdk.rest.IRestClientAPI;
import org.komea.event.storage.IEventStorage;

/**
 * @author sleroy
 *
 */
public interface IEventoryClientAPI extends IRestClientAPI {

	public Integer countEvents(String _eventType) throws ConnectException,
	ServerException;

	/**
	 * @return
	 */
	public IEventStorage getEventStorage();

	public void testConnexion() throws ConnectException, ServerException;

}