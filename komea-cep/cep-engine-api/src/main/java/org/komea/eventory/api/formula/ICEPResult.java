/**
 * 
 */

package org.komea.eventory.api.formula;



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
    <Tres> ITupleResultMap<Tres> asMap();
    
    
    /**
     * Converts the result into a number. If the result is a map, an exception is thrown.
     * 
     * @return the number.
     */
    Number asNumber();
    
    
    /**
     * Returns the single value as an object for the given type;
     * 
     * @return converts the single value as a type.
     */
    <T> T asType();
    
    
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
