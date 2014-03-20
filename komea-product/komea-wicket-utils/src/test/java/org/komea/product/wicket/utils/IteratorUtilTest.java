/**
 * 
 */

package org.komea.product.wicket.utils;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class IteratorUtilTest
{
    
    
    /**
     * Test method for {@link org.komea.product.wicket.utils.IteratorUtil#IteratorUtil()}.
     */
    @Test
    public final void testIteratorUtilEmpty() throws Exception {
    
    
        final ArrayList<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
        final List<String> buildList = buildList(IteratorUtil.buildIterator(list, 0, 0));
        assertTrue(buildList.isEmpty());
    }
    
    
    /**
     * Test method for {@link org.komea.product.wicket.utils.IteratorUtil#IteratorUtil()}.
     */
    @Test
    public final void testIteratorUtilInsideBounds1() throws Exception {
    
    
        final ArrayList<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
        final List<String> subList = buildList(IteratorUtil.buildIterator(list, 0, 1));
        Assert.assertEquals(Lists.newArrayList("A"), subList);
    }
    
    
    /**
     * Test method for {@link org.komea.product.wicket.utils.IteratorUtil#IteratorUtil()}.
     */
    @Test
    public final void testIteratorUtilInsideBounds1And1() throws Exception {
    
    
        final ArrayList<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
        final List<String> subList = buildList(IteratorUtil.buildIterator(list, 1, 1));
        Assert.assertEquals(Lists.newArrayList("B"), subList);
    }
    
    
    /**
     * Test method for {@link org.komea.product.wicket.utils.IteratorUtil#IteratorUtil()}.
     */
    @Test
    public final void testIteratorUtilInsideBounds1And2() throws Exception {
    
    
        final ArrayList<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
        final List<String> subList = buildList(IteratorUtil.buildIterator(list, 1, 2));
        Assert.assertEquals(Lists.newArrayList("B", "C"), subList);
    }
    
    
    /**
     * Test method for {@link org.komea.product.wicket.utils.IteratorUtil#IteratorUtil()}.
     */
    @Test
    public final void testIteratorUtilInsideBounds1AndEnd() throws Exception {
    
    
        final ArrayList<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
        final List<String> subList = buildList(IteratorUtil.buildIterator(list, 1, 4));
        Assert.assertEquals(Lists.newArrayList("B", "C", "D", "E"), subList);
    }
    
    
    /**
     * Test method for {@link org.komea.product.wicket.utils.IteratorUtil#IteratorUtil()}.
     */
    @Test
    public final void testIteratorUtilInsideBounds2() throws Exception {
    
    
        final ArrayList<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
        final List<String> subList = buildList(IteratorUtil.buildIterator(list, 0, 2));
        Assert.assertEquals(Lists.newArrayList("A", "B"), subList);
    }
    
    
    /**
     * Test method for {@link org.komea.product.wicket.utils.IteratorUtil#IteratorUtil()}.
     */
    @Test
    public final void testIteratorUtilInsideBoundsEnd() throws Exception {
    
    
        final ArrayList<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
        final List<String> subList = buildList(IteratorUtil.buildIterator(list, 0, 5));
        Assert.assertEquals(Lists.newArrayList("A", "B", "C", "D", "E"), subList);
    }
    
    
    /**
     * Test method for {@link org.komea.product.wicket.utils.IteratorUtil#IteratorUtil()}.
     */
    @Test
    public final void testIteratorUtilInsideBoundsLastAnd1() throws Exception {
    
    
        final ArrayList<String> list = Lists.newArrayList("A", "B", "C", "D", "E");
        final List<String> subList = buildList(IteratorUtil.buildIterator(list, 5, 1));
        Assert.assertEquals(Lists.newArrayList(), subList);
    }
    
    
    private List<String> buildList(final Iterator<String> buildIterator) {
    
    
        final ArrayList<String> addTo = new ArrayList<String>();
        Iterators.addAll(addTo, buildIterator);
        return addTo;
    }
    
}
