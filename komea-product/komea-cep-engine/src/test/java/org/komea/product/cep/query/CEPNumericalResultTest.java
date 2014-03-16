
package org.komea.product.cep.query;



import org.junit.Assert;
import org.junit.Test;



public class CEPNumericalResultTest
{
    
    
    public void testCEPNumericalResult() throws Exception {
    
    
        final CEPNumericalResult cepNumericalResult = new CEPNumericalResult(12);
        Assert.assertEquals(12, cepNumericalResult.asNumber());
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testCEPNumericalResulttoMap() throws Exception {
    
    
        final CEPNumericalResult cepNumericalResult = new CEPNumericalResult(12);
        cepNumericalResult.asMap();
    }
}
