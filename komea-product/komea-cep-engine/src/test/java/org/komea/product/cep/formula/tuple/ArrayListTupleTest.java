/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.database.dto.KpiResult;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class ArrayListTupleTest
{
    
    
    public static class DemoPojo
    {
        
        
        String a;
        
        
        String b;
        
        
        
        public DemoPojo() {
        
        
            super();
        }
    }
    
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#append(java.lang.Object)}.
     */
    @Test
    public final void testAppend() throws Exception {
    
    
        final ArrayListTuple arrayListTuple = new ArrayListTuple(Collections.singletonList("truc"));
        final ITuple append = arrayListTuple.append("trac");
        assertEquals(append.getFirst(), "truc");
        assertEquals(1, arrayListTuple.size());
        assertEquals(2, append.size());
        assertFalse(append.isSingleton());
        assertTrue(append.values().contains("trac"));
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#ArrayListTuple(java.util.List)}.
     */
    @Test
    public final void testArrayListTuple() throws Exception {
    
    
        final ArrayListTuple arrayListTuple = new ArrayListTuple(Collections.singletonList("truc"));
        final ArrayListTuple arrayListTuple2 =
                new ArrayListTuple(Collections.singletonList("truc"));
        Assert.assertEquals(arrayListTuple, arrayListTuple2);
        Assert.assertEquals(1, arrayListTuple2.size());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#ArrayListTuple(java.util.List)}.
     */
    @Test
    public void testArrayListTupleSimple() throws Exception {
    
    
        final ArrayListTuple arrayListTuple = new ArrayListTuple();
        assertTrue(arrayListTuple.isEmpty());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#asBean(java.lang.String[], java.lang.Class)}.
     */
    @Test
    public final void testAsBean() throws Exception {
    
    
        final ArrayListTuple arrayListTuple =
                new ArrayListTuple(Lists.newArrayList("jedi", "truc"));
        final DemoPojo pojo = arrayListTuple.asBean(new String[]
            {
                    "a",
                    "b" }, DemoPojo.class);
        Assert.assertNotNull(pojo);
        assertEquals("jedi", pojo.a);
        assertEquals("truc", pojo.b);
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#asMap(java.lang.String[])}.
     */
    @Test
    public final void testAsMap() throws Exception {
    
    
        final ArrayListTuple arrayListTuple =
                new ArrayListTuple(Lists.newArrayList("jedi", "truc"));
        final Map<String, Object> map = arrayListTuple.asMap(new String[]
            {
                    "a",
                    "b" });
        assertEquals(2, map.size());
        assertEquals("jedi", map.get("a"));
        assertEquals("truc", map.get("b"));
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#compareTo(org.komea.product.cep.api.formula.tuple.ITuple)}.
     */
    @Test
    public void testCompareTo() throws Exception {
    
    
        final ArrayListTuple tuple = new ArrayListTuple(Lists.newArrayList("singleton"));
        final ArrayListTuple tuple2 = new ArrayListTuple(Lists.newArrayList("singleton"));
        final ArrayListTuple tuple3 = new ArrayListTuple(Lists.newArrayList("singleton2"));
        new ArrayListTuple(Lists.newArrayList(new KpiResult()));
        assertEquals(0, tuple.compareTo(tuple2));
        assertEquals(-1, tuple2.compareTo(tuple3));
        assertEquals(1, tuple3.compareTo(tuple2));
        assertEquals(1, tuple3.compareTo(null));
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#getFirst()}.
     */
    @Test
    public final void testGetFirst() throws Exception {
    
    
        Assert.assertNull(new ArrayListTuple(Lists.newArrayList()).getFirst());
        Assert.assertEquals("jedi",
                new ArrayListTuple(Lists.newArrayList("jedi", "sith")).getFirst());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#hashCode()}.
     */
    @Test
    public void testHashCode() throws Exception {
    
    
        final ArrayListTuple tuple = new ArrayListTuple(Lists.newArrayList("singleton"));
        final ArrayListTuple tuple2 = new ArrayListTuple(Lists.newArrayList("singleton"));
        final Set<ITuple> set = new HashSet<ITuple>();
        set.add(tuple);
        set.add(tuple2);
        assertTrue(tuple.equals(tuple2));
        assertEquals("The set should contains only one event, since they are similar", 1,
                set.size());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#isSingleton()}.
     */
    @Test
    public final void testHasSingletonValue() throws Exception {
    
    
        Assert.assertTrue(new ArrayListTuple(Lists.newArrayList("jedi")).isSingleton());
        Assert.assertFalse(new ArrayListTuple(Lists.newArrayList("jedi", "sith")).isSingleton());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#hasSingletonValue(java.lang.Object)}.
     */
    @Test
    public final void testHasSingletonValueObject() throws Exception {
    
    
        Assert.assertFalse(new ArrayListTuple(Lists.newArrayList()).hasSingletonValue("value"));
        Assert.assertTrue(new ArrayListTuple(Lists.newArrayList("jedi")).hasSingletonValue("jedi"));
        Assert.assertFalse(new ArrayListTuple(Lists.newArrayList("jedi", "sith"))
                .hasSingletonValue("sith"));
        Assert.assertFalse(new ArrayListTuple(Lists.newArrayList("jedi", "sith"))
                .hasSingletonValue("jedi"));
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#size()}.
     */
    @Test
    public final void testSize() throws Exception {
    
    
        Assert.assertEquals(0, new ArrayListTuple(Lists.newArrayList()).size());
        Assert.assertEquals(1, new ArrayListTuple(Lists.newArrayList("jedi")).size());
        Assert.assertEquals(2, new ArrayListTuple(Lists.newArrayList("jedi", "sith")).size());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#toString()}.
     */
    @Test
    public final void testToString() throws Exception {
    
    
        Assert.assertNotNull(new ArrayListTuple(Collections.singletonList("truc")).toString());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.tuple.ArrayListTuple#values()}.
     */
    @Test
    public final void testValues() throws Exception {
    
    
        Assert.assertTrue(new ArrayListTuple(Lists.newArrayList("jedi")).values().contains("jedi"));
        Assert.assertTrue(new ArrayListTuple(Lists.newArrayList("jedi", "vtx")).values()
                .containsAll(Lists.newArrayList("jedi", "vtx")));
    }
    
}
