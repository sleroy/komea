/**
 * 
 */

package org.komea.eventory.filter;



import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.filter.LambdaJEventFilter;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class LambdaJEventFilterTest
{
    
    
    /**
     * Test method for {@link org.komea.eventory.filter.LambdaJEventFilter#LambdaJEventFilter(org.komea.eventory.api.filters.IEventFilter)}.
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
