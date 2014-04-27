/**
 * 
 */

package org.komea.eventory.query;



import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.api.formula.IResultMap;
import org.komea.eventory.api.formula.ITupleResultMap;



/**
 * This class returns the result expected from a query
 * 
 * @author sleroy
 */
public class CEPMapResult implements ICEPResult
{
    
    
    private IResultMap value = null;
    
    
    
    public CEPMapResult(final ITupleResultMap _iResultMap) {
    
    
        super();
        value = _iResultMap;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#asMap()
     */
    @Override
    public <TKey, Tres> IResultMap<TKey, Tres> asMap() {
    
    
        return value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#asNumber()
     */
    @Override
    public Number asNumber() {
    
    
        throw new IllegalArgumentException("Invalid type requested");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#asNumber()
     */
    @Override
    public <T> T asType() {
    
    
        throw new IllegalArgumentException("Invalid type requested");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#isMap()
     */
    @Override
    public boolean isMap() {
    
    
        return true;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#isNumericalValue()
     */
    @Override
    public boolean isNumericalValue() {
    
    
        return false;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPResult#isNumericalValue()
     */
    @Override
    public boolean isSingleValue() {
    
    
        return false;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "CEPMapResult [value=" + value + "]";
    }
    
    
}
