/**
 *
 */

package org.komea.product.backend.groovy;



import org.komea.product.backend.exceptions.KomeaRuntimeException;



/**
 * @author sleroy
 */
public class QueryExecutionFailed extends KomeaRuntimeException
{
    
    
    public QueryExecutionFailed(final Throwable _throwable) {
    
    
        super("A query has failed and could not provide any result", _throwable);
        
    }
    
}
