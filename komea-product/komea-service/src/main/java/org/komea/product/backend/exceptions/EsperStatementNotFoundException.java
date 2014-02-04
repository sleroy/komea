
package org.komea.product.backend.exceptions;



public class EsperStatementNotFoundException extends RuntimeException
{
    
    
    public EsperStatementNotFoundException(final String _statementName) {
    
    
        super("Esper statement was not found :" + _statementName);
    }
}
