/**
 * 
 */
package org.komea.demo.gitspy.eventory.api;

import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;

/**
 * @author sleroy
 *
 */
public interface IEventoryConnectorService {

	public abstract IEventoryClientAPI newConnection();

}