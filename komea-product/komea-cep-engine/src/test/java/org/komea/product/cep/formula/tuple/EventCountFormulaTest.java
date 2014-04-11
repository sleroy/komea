
package org.komea.product.cep.formula.tuple;



import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.ITupleResultMap;
import org.komea.product.cep.api.formula.tuple.IEventGroup;
import org.komea.product.cep.api.formula.tuple.IEventTable;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.mockito.Mockito;



public class EventCountFormulaTest
{
    
    
    @Test 
    public void testProcessMapWithoutParameters() throws Exception {
    
    
        final EventCountFormula tupleCountFormula = new EventCountFormula();
        final IEventTable eventTable = Mockito.mock(IEventTable.class);
        // Map
        final Map<ITuple, IEventGroup> map = new HashMap<ITuple, IEventGroup>();
        final ITuple tupleMock = Mockito.mock(ITuple.class);
        map.put(tupleMock, Mockito.mock(IEventGroup.class));
        
        
        Mockito.when(eventTable.iterator()).thenReturn(map.entrySet());
        
        
        final ITupleResultMap<Integer> processMap =
                tupleCountFormula.processMap(eventTable, Collections.<String, Object> emptyMap());
        final Map<ITuple, Integer> asTupleMap = processMap.getTable();
        Assert.assertEquals(1, asTupleMap.size());
        Assert.assertEquals(Integer.valueOf(0), asTupleMap.get(tupleMock));
        
    }
}
