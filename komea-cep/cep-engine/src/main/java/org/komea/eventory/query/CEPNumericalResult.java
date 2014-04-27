/**
 * 
 */

package org.komea.eventory.query;



import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.api.formula.IResultMap;



/**
 * This class returns the result expected from a query
 * 
 * @author sleroy
 */
public class CEPNumericalResult implements ICEPResult
{
    
    
    private Number value = null;
    
    
    
    public CEPNumericalResult(final Number _value) {
    
    
        super();
        value = _value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#asMap()
     */
    @Override
    public <TKey, Tres> IResultMap<TKey, Tres> asMap() {
    
    
        throw new IllegalArgumentException("Invalid type requested");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#asNumber()
     */
    @Override
    public Number asNumber() {
    
    
        return value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#asNumber()
     */
    @Override
    public <T> T asType() {
    
    
        return (T) value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#isMap()
     */
    @Override
    public boolean isMap() {
    
    
        return false;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#isNumericalValue()
     */
    @Override
    public boolean isNumericalValue() {
    
    
        return true;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#isNumericalValue()
     */
    @Override
    public boolean isSingleValue() {
    
    
        return true;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "CEPNumericalResult [value=" + value + "]";
    }
    
    
}
