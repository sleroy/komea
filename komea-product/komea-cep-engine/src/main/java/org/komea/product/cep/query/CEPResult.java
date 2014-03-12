/**
 * 
 */

package org.komea.product.cep.query;



import java.util.Collections;
import java.util.Map;

import org.komea.product.cep.api.CEPResultType;
import org.komea.product.cep.api.ICEPResult;



/**
 * This class returns the result expected from a query
 * 
 * @author sleroy
 */
public class CEPResult
{
    
    
    /**
     * Returns a CEP Result as a number.
     * 
     * @param _value
     *            the number
     * @return the result
     */
    public static ICEPResult buildFromCustomType(final Object _value) {
    
    
        final CEPCustomTypeResult cepResult = new CEPCustomTypeResult(_value);
        
        return cepResult;
    }
    
    
    /**
     * Creates the result wrapper from its definition.
     */
    public static ICEPResult buildFromDefinition(
            final CEPResultType _resultType,
            final Object _value) {
    
    
        switch (_resultType) {
            case NUMERICAL:
                return buildFromNumber((Number) _value);
            case TABLE:
                return buildFromMap((Map<Object, Object>) _value);
            case CUSTOM:
                return buildFromCustomType(_value);
        }
        return null;
    }
    
    
    /**
     * Returns a CEP Result as a number.
     * 
     * @return
     */
    public static ICEPResult buildFromMap(final Map<Object, Object> _map) {
    
    
        final CEPMapResult cepResult = new CEPMapResult(Collections.unmodifiableMap(_map));
        return cepResult;
    }
    
    
    /**
     * Returns a CEP Result as a number.
     * 
     * @param _value
     *            the number
     * @return
     */
    public static ICEPResult buildFromNumber(final Number _value) {
    
    
        final CEPNumericalResult cepResult = new CEPNumericalResult(_value);
        
        return cepResult;
    }
    
    
    private CEPResult() {
    
    
    }
    
}
