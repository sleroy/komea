
package org.komea.product.cep.formula.tuple;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.formula.tuple.ITuple;

import com.google.common.collect.Lists;



public class TupleFactoryTest
{
    
    
    @Test
    public final void testNewTupleArrayList() throws Exception {
    
    
        final ITuple newTuple = TupleFactory.newTuple(Collections.singletonList("truc"));
        Assert.assertEquals("truc", newTuple.getFirst());
        Assert.assertTrue(newTuple.isSingleton());
    }
    
    
    @Test
    public final void testNewTupleArrayListTwoELements() throws Exception {
    
    
        final ITuple newTuple = TupleFactory.newTuple(Lists.newArrayList("truc", "truc2"));
        Assert.assertEquals("truc", newTuple.getFirst());
        Assert.assertFalse(newTuple.isSingleton());
        Assert.assertEquals(2, newTuple.size());
    }
    
    
    @Test
    public final void testNewTupleObjectObjectArray() throws Exception {
    
    
        final ITuple newTuple = TupleFactory.newTuple("truc");
        Assert.assertEquals("truc", newTuple.getFirst());
        Assert.assertTrue(newTuple.isSingleton());
    }
    
}
