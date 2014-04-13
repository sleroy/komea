/**
 * 
 */

package org.komea.eventory.api.formula;



import java.util.Map;



/**
 * This interface defines the methodds provided by a spring formula.
 * 
 * @author sleroy
 * @param <T>
 */
public interface IElFormula<T>
{
    
    
    /**
     * Method getFormula.
     * 
     * @return String
     */
    public String getFormula();
    
    
    /**
     * Method getValue.
     * 
     * @param _context
     *            Object
     * @param _value
     *            Class<T>
     * @return T
     */
    public T getValue(Object _context);
    
    
    /**
     * Returns the value of a formula, additional parameters may be injected.
     * 
     * @param _context
     *            the context
     * @param _parameters
     *            the extra parameters
     * @return the value.
     */
    T getValue(Object _context, Map<String, Object> _parameters);
    
}
