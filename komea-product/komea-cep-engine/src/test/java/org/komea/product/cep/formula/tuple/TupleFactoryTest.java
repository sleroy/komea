
package org.komea.product.cep.formula.tuple;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.formula.tuple.ITuple;



public class TupleFactoryTest
{
    
    
    @Test @Ignore
    public final void testNewTupleArrayList() throws Exception {
    
    
        final ITuple newTuple = TupleFactory.newTuple(Collections.singletonList("truc"));
        Assert.assertEquals("truc", newTuple.getFirst());
        Assert.assertTrue(newTuple.isSingleton());
    }
    
    
    @Test @Ignore
    public final void testNewTupleObjectObjectArray() throws Exception {
    
    
        final ITuple newTuple = TupleFactory.newTuple("truc");
        Assert.assertEquals("truc", newTuple.getFirst());
        Assert.assertTrue(newTuple.isSingleton());
    }
    
}
