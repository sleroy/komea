
package org.komea.eventory.formula;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.engine.ICEPStatement;
import org.mockito.Mockito;



public class CountFormulaTest
{
    
    
    @Test
    public final void testCompute() throws Exception {
    
    
        final CountFormula countFormula = new CountFormula();
        final ICEPStatement mock = Mockito.mock(ICEPStatement.class);
        Mockito.when(mock.getDefaultStorage()).thenReturn(Collections.singletonList("truc"));
        
        
        final Integer result = countFormula.compute(mock, Collections.EMPTY_MAP);
        
        
        Assert.assertEquals(Integer.valueOf(1), result);
    }
}
