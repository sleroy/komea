
package org.komea.product.backend.exceptions;



/**
 */
public class CEPQueryNotFoundException extends RuntimeException
{
    
    
    /**
     * Constructor for CEPQueryNotFoundException.
     * 
     * @param _statementName
     *            String
     */
    public CEPQueryNotFoundException(final String _statementName) {
    
    
        super("CEP Query statement was not found :" + _statementName);
    }
}
