
package org.komea.product.cep.formula;



import java.io.Serializable;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.formula.CountWithFilterFormula;
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
        
        
        final Integer result = countFormula.compute(mock, Collections.EMPTY_MAP);
        
        
        Assert.assertEquals(Integer.valueOf(1), result);
    }
}
