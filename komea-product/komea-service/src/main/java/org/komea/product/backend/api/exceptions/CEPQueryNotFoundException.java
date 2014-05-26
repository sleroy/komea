
package org.komea.product.backend.api.exceptions;



import org.komea.product.backend.service.kpi.FormulaID;



/**
 */
public class CEPQueryNotFoundException extends RuntimeException
{
    
    
    /**
     * @param _formulaID
     */
    public CEPQueryNotFoundException(final FormulaID _formulaID) {
    
    
        super("CEP Query statement was not found :" + _formulaID);
    }
    
    
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
