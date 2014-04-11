
package org.komea.product.cep.formula;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ICEPStatement;
import org.komea.product.cep.formula.ElNumericalFormula.Context;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;



@RunWith(MockitoJUnitRunner.class)
public class ElNumericalFormulaTest
{
    
    
    @Test @Ignore
    public final void testElNumericalFormulaString() throws Exception {
    
    
        final ICEPStatement mock = Mockito.mock(ICEPStatement.class);
        Mockito.when(mock.getDefaultStorage()).thenReturn(Lists.newArrayList("truc", "truc2"));
        Mockito.when(mock.getAggregateView()).thenReturn(Lists.newArrayList("truc", "truc2"));
        
        
        final ICEPResult result =
                new ElNumericalFormula("previous  + 1").compute(mock, Collections.EMPTY_MAP);
        
        Assert.assertEquals(2, result.asNumber().intValue());
        
        
    }
    
    
    @Test @Ignore
    public final void testGetFormula() throws Exception {
    
    
        final ICEPStatement mock = Mockito.mock(ICEPStatement.class);
        Mockito.when(mock.getDefaultStorage()).thenReturn(Lists.newArrayList("truc", "truc2"));
        Mockito.when(mock.getAggregateView()).thenReturn(Lists.newArrayList("truc", "truc2"));
        
        
        final Context context = new Context();
        context.setPrevious(2);
        context.setEvent("truc");
        
        final int result =
                new ElNumericalFormula("previous  + event.length()").computeValue(context)
                        .intValue();
        
        Assert.assertEquals(6, result);
        
    }
    
}
