/**
 * 
 */

package org.komea.product.cep.filter;



import org.junit.Test;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.database.alert.IEvent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;



/**
 * @author sleroy
 */
public class EventFilterBuilderTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.filter.EventFilterBuilder#build()}.
     */
    @Test
    public final void testBuild() throws Exception {
    
    
        final IEventFilter<?> build = EventFilterBuilder.create().build();
        assertNotNull(build);
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.filter.EventFilterBuilder#chain(org.komea.product.cep.api.IEventFilter)}.
     */
    @Test
    public final void testChain() throws Exception {
    
    
        final EventFilterBuilder create = EventFilterBuilder.create();
        assertNotNull(create.build());
        assertNotNull("chain must produce a filter", create.chain(new NoEventFilter()));
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.filter.EventFilterBuilder#create()}.
     */
    @Test
    public final void testCreate() throws Exception {
    
    
        assertNotNull(EventFilterBuilder.create());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.filter.EventFilterBuilder#onlyIEvents()}.
     */
    @Test
    public final void testOnlyIEvents() throws Exception {
    
    
        final IEventFilter build = EventFilterBuilder.create().onlyIEvents().build();
        assertFalse(build.isFiltered("truc"));
        assertTrue(build.isFiltered(mock(IEvent.class)));
    }
}
