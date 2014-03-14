
package org.komea.product.cep.filter;



import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.cep.api.IEventFilter;
import org.mockito.runners.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class JoinFilterTest
{
    
    
    @Test
    public final void testJoinFilter() throws Exception {
    
    
        final IEventFilter<Serializable> iEventFilter = new IEventFilter<Serializable>()
        {
            
            
            @Override
            public boolean isFiltered(final Serializable _event) {
            
            
                return _event.toString().charAt(0) == 'T';
            }
        };
        final IEventFilter<Serializable> iEventFilter2 = new IEventFilter<Serializable>()
        {
            
            
            @Override
            public boolean isFiltered(final Serializable _event) {
            
            
                return 4 == _event.toString().length();
            }
        };
        
        final JoinFilter joinFilter = new JoinFilter(iEventFilter, iEventFilter2);
        Assert.assertTrue(joinFilter.isFiltered("TXXI"));
        Assert.assertFalse(joinFilter.isFiltered("TXXIZ"));
        Assert.assertFalse(joinFilter.isFiltered("ZZXY"));
        
        
    }
}
