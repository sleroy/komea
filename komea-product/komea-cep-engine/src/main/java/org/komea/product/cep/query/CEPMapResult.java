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
public class CEPMapResult implements ICEPResult
{
    
    
    private ITupleResultMap value = null;
    
    
    
    public CEPMapResult(final ITupleResultMap _iResultMap) {
    
    
        super();
        value = _iResultMap;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#asMap()
     */
    @Override
    public <TRes> ITupleResultMap<TRes> asMap() {
    
    
        return value;
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
    public <T> T asType() {
    
    
        throw new IllegalArgumentException("Invalid type requested");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#isMap()
     */
    @Override
    public boolean isMap() {
    
    
        return true;
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
