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
public class CEPMapResult implements ICEPResult
{
    
    
    private Map value = null;
    
    
    
    public CEPMapResult(final Map<?, ?> _value) {
    
    
        super();
        value = _value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#asMap()
     */
    @Override
    public Map<Object, Object> asMap() {
    
    
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
     * @see org.komea.product.cep.api.ICEPResult#asNumber(java.lang.Object)
     */
    @Override
    public Number asNumber(final Object _key) {
    
    
        return Number.class.cast(value.get(_key));
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
     * @see org.komea.product.cep.api.ICEPResult#asNumber()
     */
    @Override
    public <T> T asType(final Object _key) {
    
    
        return (T) value.get(_key);
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
    
    
}
