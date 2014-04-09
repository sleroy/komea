
package org.komea.product.cep.formula.tuple;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.class)
public class EventTableTest
{
    
    
    @InjectMocks
    private EventTable    eventTable;
    @Mock
    private ITupleCreator tupleCreator;
    
    
    
    @Test
    public void testEventTable() throws Exception {
    
    
        Assert.assertFalse(eventTable.iterator().iterator().hasNext());
    }
    
    
    @Test
    public void testGroupEvent() throws Exception {
    
    
        final ITuple mock = Mockito.mock(ITuple.class);
        Mockito.when(tupleCreator.create("truc")).thenReturn(mock);
        eventTable.groupEvent("truc");
        Mockito.verify(tupleCreator, Mockito.times(1)).create("truc");
        Assert.assertTrue("One entry", eventTable.iterator().iterator().hasNext());
    }
    
    
    @Test
    public void testIterator() throws Exception {
    
    
        Assert.assertNotNull(eventTable.iterator());
    }
    
}
