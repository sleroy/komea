/**
 * 
 */

package org.komea.product.backend.exceptions;



/**
 * @author sleroy
 */
public class KpiAlreadyExistingException extends RuntimeException
{
    
    
    /**
     * @param _kpiName
     */
    public KpiAlreadyExistingException(final String _kpiName) {
    
    
        super("KPI cannot be added, already existing : name= " + _kpiName);
        
    }
    
    
}
