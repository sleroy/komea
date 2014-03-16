/**
 * 
 */

package org.komea.product.cep.query;



import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ITupleResultMap;



/**
 * This class returns the result expected from a query
 * 
 * @author sleroy
 */
public class CEPCustomTypeResult<T> implements ICEPResult
{
    
    
    private T value = null;
    
    
    
    public CEPCustomTypeResult(final T _value) {
    
    
        super();
        value = _value;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#asMap()
     */
    @Override
    public <Tres> ITupleResultMap<Tres> asMap() {
    
    
        throw new IllegalArgumentException("Invalid type requested");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#asNumber()
     */
    @Override
    public Number asNumber() {
    
    
        throw new IllegalArgumentException("Invalid type requested");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#asNumber()
     */
    @Override
    public <T2> T2 asType() {
    
    
        return (T2) value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#isMap()
     */
    @Override
    public boolean isMap() {
    
    
        return false;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#isNumericalValue()
     */
    @Override
    public boolean isNumericalValue() {
    
    
        return false;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#isNumericalValue()
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
    
    
        return "CEPCustomTypeResult [value=" + value + "]";
    }
    
    
}
