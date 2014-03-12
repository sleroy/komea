/**
 * 
 */

package org.komea.product.cep.api;



import java.util.Map;



/**
 * This interface provides methods to acccess to the result of a CEP Query.
 * 
 * @author sleroy
 */
public interface ICEPResult
{
    
    
    /**
     * Converts the results into a map. If the results is a numerical value, an exception is returned.
     * 
     * @return the results into a map.
     */
    Map<Object, Object> asMap();
    
    
    /**
     * Converts the result into a number. If the result is a map, an exception is thrown.
     * 
     * @return the number.
     */
    Number asNumber();
    
    
    /**
     * Returns a number when a map result has been computed, with a number in parameter.
     * 
     * @param _key
     *            the key
     * @return the number
     */
    Number asNumber(Object _key);
    
    
    /**
     * @return
     */
    <T> T asType();
    
    
    /**
     * @param _key
     * @return
     */
    <T> T asType(Object _key);
    
    
    /**
     * Tests if this result is a map.
     * 
     * @return true if this result is a map.
     */
    boolean isMap();
    
    
    /**
     * Tests if this results ia numerical value;
     */
    boolean isNumericalValue();
    
    
    /**
     * @return
     */
    boolean isSingleValue();
}
