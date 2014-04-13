/**
 * 
 */

package org.komea.eventory.filter;



import org.junit.Test;
import org.komea.product.cep.api.IEventFilter;

import static org.junit.Assert.assertNotNull;



/**
 * @author sleroy
 */
public class EventFilterBuilderTest
{
    
    
    /**
     * Test method for {@link org.komea.eventory.filter.EventFilterBuilder#build()}.
     */
    @Test
    public final void testBuild() throws Exception {
    
    
        final IEventFilter<?> build = EventFilterBuilder.create().build();
        assertNotNull(build);
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.filter.EventFilterBuilder#chain(org.komea.eventory.api.IEventFilter)}.
     */
    @Test
    public final void testChain() throws Exception {
    
    
        final EventFilterBuilder create = EventFilterBuilder.create();
        assertNotNull(create.build());
        assertNotNull("chain must produce a filter", create.chain(new NoEventFilter()));
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.filter.EventFilterBuilder#create()}.
     */
    @Test
    public final void testCreate() throws Exception {
    
    
        assertNotNull(EventFilterBuilder.create());
    }
    
    
}
