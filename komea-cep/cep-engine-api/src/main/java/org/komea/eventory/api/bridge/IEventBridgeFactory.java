/**
 *
 */

package org.komea.eventory.api.bridge;






/**
 * This class is a factory to build a bridge that will dispatch events to queries.
 *
 * @author sleroy
 */
public interface IEventBridgeFactory
{


    /**
     * Builds a new bridge for the cep engine.
     *
     * @return the cep engine.
     */
    IEventBridge newBridge();
}
