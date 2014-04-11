
package org.komea.product.cep.api;



import org.junit.Assert;
import org.junit.Test;



public class CEPInvalidTransformationTest
{
    
    
    @Test @Ignore
    public void testGetData() throws Exception {
    
    
        Assert.assertNull(CEPInvalidTransformation.INVALID_TRANSFORMATION.getData());
        
    }
    
    
    @Test @Ignore
    public void testIsValid() throws Exception {
    
    
        Assert.assertFalse(CEPInvalidTransformation.INVALID_TRANSFORMATION.isValid());
    }
    
}
