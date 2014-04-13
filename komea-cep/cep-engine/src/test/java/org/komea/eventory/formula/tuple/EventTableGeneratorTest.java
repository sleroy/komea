/** 
 * 
 */

package org.komea.eventory.formula.tuple;



import org.junit.Test;
import org.komea.eventory.formula.tuple.EventTableGenerator;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.mockito.Mockito;

import com.google.common.collect.Lists;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class EventTableGeneratorTest
{
    
    
    /**
     * Test method for {@link org.komea.eventory.formula.tuple.EventTableGenerator#generateTable(java.util.List)}.
     */
    @Test 
    public final void testGenerateTable() throws Exception {
    
    
        final ITupleCreator tupleCreator = Mockito.mock(ITupleCreator.class);
        final EventTableGenerator eventTableGenerator = new EventTableGenerator(tupleCreator);
        when(tupleCreator.create("truc")).thenReturn(Mockito.mock(ITuple.class));
        eventTableGenerator.generateTable(Lists.newArrayList("truc"));
        Mockito.verify(tupleCreator, times(1)).create("truc");
    }
    
}
