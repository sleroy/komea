
package org.komea.product.cep.query;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.CEPResultType;
import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ITupleResultMap;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;



public class CEPResultTest
{
    
    
    @Test
    public final void testBuildFromCustomType() throws Exception {
    
    
        final ICEPResult buildFromCustomType = CEPResult.buildFromCustomType("truc");
        Assert.assertFalse(buildFromCustomType.isMap());
        Assert.assertFalse(buildFromCustomType.isNumericalValue());
        Assert.assertTrue(buildFromCustomType.isSingleValue());
        Assert.assertEquals("truc", buildFromCustomType.asType());
    }
    
    
    @Test
    public final void testBuildFromDefinition() throws Exception {
    
    
        Assert.assertTrue(CEPResult.buildFromDefinition(CEPResultType.NUMERICAL, 12)
                .isNumericalValue());
        Assert.assertTrue(CEPResult.buildFromDefinition(CEPResultType.TABLE,
                Mockito.mock(ITupleResultMap.class)).isMap());
        Assert.assertTrue(CEPResult.buildFromDefinition(CEPResultType.CUSTOM, 12).isSingleValue());
        
    }
    
    
    @Test
    public final void testBuildFromMap() throws Exception {
    
    
        final ITupleResultMap mock = Mockito.mock(ITupleResultMap.class);
        final ICEPResult buildFromCustomType = CEPResult.buildFromMap(mock);
        Assert.assertTrue(buildFromCustomType.isMap());
        Assert.assertFalse(buildFromCustomType.isNumericalValue());
        Assert.assertFalse(buildFromCustomType.isSingleValue());
        Assert.assertEquals(mock, buildFromCustomType.asMap());
    }
    
    
    @Test
    public final void testBuildFromNumber() throws Exception {
    
    
        final ICEPResult buildFromCustomType = CEPResult.buildFromNumber(12);
        Assert.assertFalse(buildFromCustomType.isMap());
        Assert.assertTrue(buildFromCustomType.isNumericalValue());
        Assert.assertTrue(buildFromCustomType.isSingleValue());
        Assert.assertEquals(12, buildFromCustomType.asNumber());
        Assert.assertEquals(Integer.valueOf(12), buildFromCustomType.asType());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.query.CEPResult#guessResultType(java.lang.Object)}.
     */
    @Test
    public void testGuessResultType() throws Exception {
    
    
        assertTrue(CEPResult.guessResultType("12").isSingleValue());
        assertFalse(CEPResult.guessResultType("12").isNumericalValue());
        assertTrue(CEPResult.guessResultType(12).isNumericalValue());
        assertTrue(CEPResult.guessResultType(Integer.valueOf(12)).isNumericalValue());
        assertTrue(CEPResult.guessResultType(mock(ITupleResultMap.class)).isMap());
    }
    
}
