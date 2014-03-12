/**
 * 
 */

package org.komea.product.cep.api;



import java.io.Serializable;
import java.util.Map;



/**
 * This class defines the formula that is employed by the query to compute its result.
 * 
 * @author sleroy
 */
public interface ICEPFormula<T extends Serializable>
{
    
    
    /**
     * Compute the value.
     * 
     * @param _statement
     *            the statement
     * @param the
     *            parameters
     * @return the result.
     */
    ICEPResult compute(ICEPStatement<T> _statement, Map<String, Object> _parameters);
    
    
    /**
     * Returns the default parameters
     * 
     * @return the default parameters.
     */
    Map<String, Object> getDefaultParameters();
    
    
    /**
     * Defines the type of results expected by the formula.
     */
    CEPResultType getResultType();
}
