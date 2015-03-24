/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import java.net.ConnectException;
import java.rmi.ServerException;

import org.joda.time.DateTime;
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
	 * Returns the event storage.
	 *
	 * @return
	 */
	public IEventStorage getEventStorage();

	/**
	 * Returns the last event of a given type.
	 *
	 * @param _eventTypeName
	 * @return
	 */
	public DateTime getLastEvent(String _eventTypeName);

	public void testConnexion() throws ConnectException, ServerException;

}