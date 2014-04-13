
package org.komea.product.cep.api;



import org.junit.Assert;
import org.junit.Test;



public class CEPInvalidTransformationTest
{
    
    
    @Test 
    public void testGetData() throws Exception {
    
    
        Assert.assertNull(CEPInvalidTransformation.INVALID_TRANSFORMATION.getData());
        
    }
    
    
    @Test 
    public void testIsValid() throws Exception {
    
    
        Assert.assertFalse(CEPInvalidTransformation.INVALID_TRANSFORMATION.isValid());
    }
    
}
