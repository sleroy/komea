
package org.komea.product.cep.formula;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;



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
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.EventTable#fill(java.util.List)}.
     */
    @Test
    public void testFill() throws Exception {
    
    
        when(tupleCreator.create("S1")).thenReturn(1);
        when(tupleCreator.create("S2")).thenReturn(2);
        final List<String> strings = Lists.newArrayList("S1", "S2");
        eventTable.fill(strings);
        assertEquals(2, eventTable.size());
    }
    
    
    @Test
    public void testGroupEvent() throws Exception {
    
    
        when(tupleCreator.create("truc")).thenReturn(1);
        eventTable.groupEvent("truc");
        Mockito.verify(tupleCreator, Mockito.times(1)).create("truc");
        Assert.assertTrue("1", eventTable.iterator().iterator().hasNext());
    }
    
    
    @Test
    public void testIterator() throws Exception {
    
    
        Assert.assertNotNull(eventTable.iterator());
    }
    
}
