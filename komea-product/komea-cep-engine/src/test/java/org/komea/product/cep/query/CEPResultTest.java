
package org.komea.product.cep.query;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.CEPResultType;
import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ITupleResultMap;
import org.mockito.Mockito;



public class CEPResultTest
{
    
    
    @Test @Ignore
    public final void testBuildFromCustomType() throws Exception {
    
    
        final ICEPResult buildFromCustomType = CEPResult.buildFromCustomType("truc");
        Assert.assertFalse(buildFromCustomType.isMap());
        Assert.assertFalse(buildFromCustomType.isNumericalValue());
        Assert.assertTrue(buildFromCustomType.isSingleValue());
        Assert.assertEquals("truc", buildFromCustomType.asType());
    }
    
    
    @Test @Ignore
    public final void testBuildFromDefinition() throws Exception {
    
    
        Assert.assertTrue(CEPResult.buildFromDefinition(CEPResultType.NUMERICAL, 12)
                .isNumericalValue());
        Assert.assertTrue(CEPResult.buildFromDefinition(CEPResultType.TABLE,
                Mockito.mock(ITupleResultMap.class)).isMap());
        Assert.assertTrue(CEPResult.buildFromDefinition(CEPResultType.CUSTOM, 12).isSingleValue());
        
    }
    
    
    @Test @Ignore
    public final void testBuildFromMap() throws Exception {
    
    
        final ITupleResultMap mock = Mockito.mock(ITupleResultMap.class);
        final ICEPResult buildFromCustomType = CEPResult.buildFromMap(mock);
        Assert.assertTrue(buildFromCustomType.isMap());
        Assert.assertFalse(buildFromCustomType.isNumericalValue());
        Assert.assertFalse(buildFromCustomType.isSingleValue());
        Assert.assertEquals(mock, buildFromCustomType.asMap());
    }
    
    
    @Test @Ignore
    public final void testBuildFromNumber() throws Exception {
    
    
        final ICEPResult buildFromCustomType = CEPResult.buildFromNumber(12);
        Assert.assertFalse(buildFromCustomType.isMap());
        Assert.assertTrue(buildFromCustomType.isNumericalValue());
        Assert.assertTrue(buildFromCustomType.isSingleValue());
        Assert.assertEquals(12, buildFromCustomType.asNumber());
        Assert.assertEquals(Integer.valueOf(12), buildFromCustomType.asType());
    }
    
}
