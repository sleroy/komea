/**
 * 
 */

package org.komea.product.cep.filter;



import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.cep.api.IEventFilter;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class LambdaJEventFilterTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.filter.LambdaJEventFilter#LambdaJEventFilter(org.komea.product.cep.api.IEventFilter)}.
     */
    @Test 
    public final void testLambdaJEventFilter() throws Exception {
    
    
        final LambdaJEventFilter lambdaJEventFilter2 = new LambdaJEventFilter(new IEventFilter()
        {
            
            
            @Override
            public boolean isFiltered(final Serializable _event) {
            
            
                return "truc2".equals(_event);
            }
        });
        Assert.assertFalse(lambdaJEventFilter2.matches("truc"));
        Assert.assertTrue(lambdaJEventFilter2.matches("truc2"));
        
    }
    
    
}
