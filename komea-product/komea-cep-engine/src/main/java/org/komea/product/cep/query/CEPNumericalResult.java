/**
 * 
 */

package org.komea.product.cep.query;



import java.util.Map;

import org.komea.product.cep.api.ICEPResult;



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
     * @see org.komea.product.cep.api.ICEPResult#asMap()
     */
    @Override
    public Map<Object, Object> asMap() {
    
    
        throw new IllegalArgumentException("Invalid type requested");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#asNumber()
     */
    @Override
    public Number asNumber() {
    
    
        return value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#asNumber(java.lang.Object)
     */
    @Override
    public Number asNumber(final Object _key) {
    
    
        throw new IllegalArgumentException("Invalid type requested");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#asNumber()
     */
    @Override
    public <T> T asType() {
    
    
        return (T) value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#asNumber()
     */
    @Override
    public <T> T asType(final Object _key) {
    
    
        throw new IllegalArgumentException("Invalid type requested");
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
    
    
        return true;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#isNumericalValue()
     */
    @Override
    public boolean isSingleValue() {
    
    
        return true;
    }
    
    
}
