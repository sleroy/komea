
package org.komea.product.cep.formula;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ICEPStatement;
import org.mockito.Mockito;



public class CountFormulaTest
{
    
    
    @Test 
    public final void testCompute() throws Exception {
    
    
        final CountFormula countFormula = new CountFormula();
        final ICEPStatement mock = Mockito.mock(ICEPStatement.class);
        Mockito.when(mock.getDefaultStorage()).thenReturn(Collections.singletonList("truc"));
        
        
        final ICEPResult result = countFormula.compute(mock, Collections.EMPTY_MAP);
        
        
        Assert.assertEquals(1, result.asNumber().intValue());
    }
}
