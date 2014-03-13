/**
 * 
 */

package org.komea.product.backend.exceptions;



import org.komea.product.cep.api.IQueryDefinition;



;


/**
 * This class is thrown when a query definition cannot be registered as a queyr inside Esper.
 * 
 * @author sleroy
 */
public class InvalidQueryDefinitionException extends RuntimeException
{
    
    
    public InvalidQueryDefinitionException(final IQueryDefinition _query, final Exception _e) {
    
    
        super("The query definition " + _query + " could not be registered.", _e);
    }
    
}
