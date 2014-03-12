/**
 * 
 */

package org.komea.product.cep.query;



import java.util.Collections;
import java.util.Map;

import org.komea.product.cep.api.ICEPResult;



/**
 * @author sleroy
 */
public class CEPResult implements ICEPResult
{
    
    
    /**
     * Returns a CEP Result as a number.
     * 
     * @return
     */
    public static ICEPResult buildFromMap(final Map<Object, Object> _map) {
    
    
        final CEPResult cepResult = new CEPResult();
        cepResult.resultMap = Collections.unmodifiableMap(_map);
        return cepResult;
    }
    
    
    /**
     * Returns a CEP Result as a number.
     * 
     * @param _size
     *            the number
     * @return
     */
    public static ICEPResult buildFromNumber(final Number _size) {
    
    
        final CEPResult cepResult = new CEPResult();
        cepResult.value = _size;
        return cepResult;
    }
    
    
    
    private Map<Object, Object> resultMap = null;
    
    
    private Number              value     = null;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#asMap()
     */
    @Override
    public Map<Object, Object> asMap() {
    
    
        if (value != null) { throw new IllegalArgumentException(); }
        return resultMap;
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
    
    
        if (resultMap != null) { throw new IllegalArgumentException(); }
        return Number.class.cast(resultMap.get(_key));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#isMap()
     */
    @Override
    public boolean isMap() {
    
    
        return resultMap != null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPResult#isNumericalValue()
     */
    @Override
    public boolean isNumericalValue() {
    
    
        return value != null;
    }
    
}
