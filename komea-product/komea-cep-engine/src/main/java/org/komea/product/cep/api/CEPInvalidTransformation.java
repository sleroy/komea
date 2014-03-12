/**
 * 
 */

package org.komea.product.cep.api;



/**
 * @author sleroy
 */
public enum CEPInvalidTransformation implements TransformedEvent {
    ;
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.TransformedEvent#getData()
     */
    @Override
    public Object getData() {
    
    
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.TransformedEvent#isValid()
     */
    @Override
    public boolean isValid() {
    
    
        return false;
    }
    
}
