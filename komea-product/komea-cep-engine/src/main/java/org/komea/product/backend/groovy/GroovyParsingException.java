/**
 * 
 */

package org.komea.product.backend.groovy;



import org.komea.product.backend.exceptions.KomeaException;



/**
 * @author sleroy
 */
public class GroovyParsingException extends KomeaException
{
    
    
    /**
     * @param _throwable
     */
    public GroovyParsingException(final Throwable _throwable) {
    
    
        super("Script groovy could not be parsed", _throwable);
        
    }
    
}
