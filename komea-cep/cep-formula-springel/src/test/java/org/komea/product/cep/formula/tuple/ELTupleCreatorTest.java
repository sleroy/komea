/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import org.junit.Test;
import org.komea.eventory.api.formula.tuple.ITuple;
import org.komea.product.cep.filter.Event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class ELTupleCreatorTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ELTupleCreator#ELTupleCreator(java.lang.String)}.
     */
    @Test
    public final void testELTupleCreator() throws Exception {
    
    
        final ELTupleCreator elTupleCreator = new ELTupleCreator("message");
        
        
        final Event event = new Event();
        event.setMessage("a");
        final ITuple tuple = elTupleCreator.create(event);
        assertEquals("a", tuple.getFirst());
        assertTrue(tuple.isSingleton());
        assertTrue(tuple.hasSingletonValue("a"));
    }
}
