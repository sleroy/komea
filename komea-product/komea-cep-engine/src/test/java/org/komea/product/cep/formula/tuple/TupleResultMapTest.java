
package org.komea.product.cep.formula.tuple;



import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.mockito.Mockito;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;



public class TupleResultMapTest
{
    
    
    public static class TuplePojoDemo
    {
        
        
        String f1;
        
        
        String f2;
        
        
        
        /**
         * 
         */
        public TuplePojoDemo() {
        
        
            //
        }
        
    }
    
    
    
    public static class TuplePojoDemo2
    {
        
        
        String f1;
        
        
        String f2;
        
        String value;
        
        
        
        /**
         * 
         */
        public TuplePojoDemo2() {
        
        
            //
        }
        
    }
    
    
    
    @Test @Ignore
    public final void testAsPojoMap() throws Exception {
    
    
        final TupleResultMap<String> tupleResultMap = new TupleResultMap<String>();
        final ITuple tuple = new ArrayListTuple(Lists.newArrayList("field1", "field2"));
        tupleResultMap.insertEntry(tuple, "value");
        final Map<TuplePojoDemo, String> asPojoMap = tupleResultMap.asPojoMap(new String[] {
                "f1", "f2" }, TuplePojoDemo.class);
        Assert.assertEquals("Map should have size of 1", 1, asPojoMap.size());
        Assert.assertTrue("Map should have a bean associated to value",
                asPojoMap.containsValue("value"));
        
    }
    
    
    @Test @Ignore
    public final void testAsPojoRows() throws Exception {
    
    
        final TupleResultMap<String> tupleResultMap = new TupleResultMap<String>();
        final ITuple tuple = new ArrayListTuple(Lists.newArrayList("field1", "field2"));
        tupleResultMap.insertEntry(tuple, "value");
        final List<TuplePojoDemo2> asPojoMap = tupleResultMap.asPojoRows(new String[] {
                "f1", "f2", "value" }, TuplePojoDemo2.class);
        Assert.assertEquals("Map should have size of 1", 1, asPojoMap.size());
        System.out.println(asPojoMap.get(0));
        
    }
    
    
    @Test @Ignore
    public final void testAsSimplifiedMap() throws Exception {
    
    
        // TODO :: test throw new RuntimeException("not yet implemented");
    }
    
    
    @Test @Ignore
    public final void testAsTupleRows() throws Exception {
    
    
        // TODO :: test throw new RuntimeException("not yet implemented");
    }
    
    
    @Test @Ignore
    public final void testGet() throws Exception {
    
    
        final TupleResultMap<String> tupleResultMap = new TupleResultMap<String>();
        final ArrayListTuple tuple = new ArrayListTuple(Collections.singletonList("truc"));
        tupleResultMap.insertEntry(tuple, "string");
        Assert.assertEquals("Size of table should be 1", 1, tupleResultMap.getTable().size());
        Assert.assertEquals("Should have the tuple", "string", tupleResultMap.getTable().get(tuple));
    }
    
    
    @Test @Ignore
    public final void testGetTable() throws Exception {
    
    
        final TupleResultMap<String> tupleResultMap = new TupleResultMap<String>();
        final ITuple tuple = Mockito.mock(ITuple.class);
        tupleResultMap.insertEntry(tuple, "string");
        Assert.assertEquals("Size of table should be 1", 1, tupleResultMap.getTable().size());
        Assert.assertEquals("Should have the tuple", "string", tupleResultMap.getTable().get(tuple));
        
    }
    
    
    @Test @Ignore
    public final void testGetValue() throws Exception {
    
    
        final TupleResultMap<String> tupleResultMap = new TupleResultMap<String>();
        final ITuple tuple = Mockito.mock(ITuple.class);
        
        tupleResultMap.insertEntry(tuple, "string");
        Assert.assertEquals("Should have the tuple", "string", tupleResultMap.getValue(tuple));
    }
    
    
    @Test @Ignore
    public final void testInsertEntry() throws Exception {
    
    
        final TupleResultMap<String> tupleResultMap = new TupleResultMap<String>();
        final ITuple tuple = Mockito.mock(ITuple.class);
        
        tupleResultMap.insertEntry(tuple, "string");
        tupleResultMap.insertEntry(tuple, "string");
        tupleResultMap.insertEntry(tuple, "string");
        Assert.assertEquals("Should have the tuple", "string", tupleResultMap.getValue(tuple));
        Assert.assertEquals("Should only have the tuple", 1, tupleResultMap.getTable().size());
        
    }
    
    
    @Test @Ignore
    public final void testInstantiatePojoFromTuple() throws Exception {
    
    
        final TupleResultMap<String> tupleResultMap = new TupleResultMap<String>();
        final ITuple tuple = new ArrayListTuple(Lists.newArrayList("field1", "field2"));
        final TuplePojoDemo pojoFromTuple =
                tupleResultMap.instantiatePojoFromTuple(TuplePojoDemo.class, new String[] {
                        "f1", "f2" }, tuple);
        assertEquals("field1", pojoFromTuple.f1);
        assertEquals("field2", pojoFromTuple.f2);
    }
    
}
