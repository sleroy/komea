/**
 * 
 */

package org.komea.product.cep.api;



/**
 * @author sleroy
 */
public enum CEPInvalidTransformation implements ITransformedEvent {
    INVALID_TRANSFORMATION;
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ITransformedEvent#getData()
     */
    @Override
    public Object getData() {
    
    
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ITransformedEvent#isValid()
     */
    @Override
    public boolean isValid() {
    
    
        return false;
    }
    
}
