
package org.komea.eventory.formula;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.formula.ElNumericalFormula;
import org.komea.eventory.formula.ElNumericalFormula.Context;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;



@RunWith(MockitoJUnitRunner.class)
public class ElNumericalFormulaTest
{
    
    
    @Test 
    public final void testElNumericalFormulaString() throws Exception {
    
    
        final ICEPStatement mock = Mockito.mock(ICEPStatement.class);
        Mockito.when(mock.getDefaultStorage()).thenReturn(Lists.newArrayList("truc", "truc2"));
        Mockito.when(mock.getAggregateView()).thenReturn(Lists.newArrayList("truc", "truc2"));
        
        
        final ICEPResult result =
                new ElNumericalFormula("previous  + 1").compute(mock, Collections.EMPTY_MAP);
        
        Assert.assertEquals(2, result.asNumber().intValue());
        
        
    }
    
    
    @Test 
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
