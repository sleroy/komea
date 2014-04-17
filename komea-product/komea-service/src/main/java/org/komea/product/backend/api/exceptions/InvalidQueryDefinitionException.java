/**
 * 
 */

package org.komea.product.backend.api.exceptions;



import org.komea.eventory.api.engine.ICEPQueryImplementation;



;


/**
 * This class is thrown when a query definition cannot be registered as a queyr inside Esper.
 * 
 * @author sleroy
 */
public class InvalidQueryDefinitionException extends RuntimeException
{
    
    
    public InvalidQueryDefinitionException(final ICEPQueryImplementation _query, final Exception _e) {
    
    
        super("The query definition " + _query + " could not be registered.", _e);
    }
    
}
