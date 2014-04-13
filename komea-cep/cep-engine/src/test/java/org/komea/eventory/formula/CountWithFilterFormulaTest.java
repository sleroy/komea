
package org.komea.eventory.formula;



import java.io.Serializable;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.formula.CountWithFilterFormula;
import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ICEPStatement;
import org.komea.product.cep.api.IEventFilter;
import org.mockito.Mockito;

import com.google.common.collect.Lists;



public class CountWithFilterFormulaTest
{
    
    
    @Test 
    public final void testCountWithFilterFormula() throws Exception {
    
    
        final CountWithFilterFormula countFormula = new CountWithFilterFormula(new IEventFilter()
        {
            
            
            @Override
            public boolean isFiltered(final Serializable _event) {
            
            
                return _event.equals("truc");
            }
        });
        final ICEPStatement mock = Mockito.mock(ICEPStatement.class);
        Mockito.when(mock.getDefaultStorage()).thenReturn(Lists.newArrayList("truc", "truc2"));
        
        
        final ICEPResult result = countFormula.compute(mock, Collections.EMPTY_MAP);
        
        
        Assert.assertEquals(1, result.asNumber().intValue());
    }
}
