/**
 *
 */

package org.komea.product.backend.utils;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class CollectionUtilTest
{
    
    
    final ArrayList<String> list = Lists.newArrayList("s1", "s2", "s3");
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.utils.CollectionUtil#filter(java.util.List, org.komea.product.backend.utils.IFilter)}.
     */
    @Test
    public void testFilter() throws Exception {
    
    
        final List<String> filter = CollectionUtil.filter(list, new IFilter<String>()
        {
            
            
            @Override
            public boolean matches(final String _task) {
            
            
                return true;
            }
            
        });
        assertEquals(list.size(), filter.size());
        
        final List<String> filter2 = CollectionUtil.filter(list, new IFilter<String>()
        {
            
            
            @Override
            public boolean matches(final String _task) {
            
            
                return false;
            }
            
        });
        assertEquals(0, filter2.size());
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.utils.CollectionUtil#iterate(java.lang.Iterable, org.komea.product.backend.utils.Treatment)}.
     */
    @Test
    public void testIterate() throws Exception {
    
    
        final ArrayList<String> arrayList = Lists.newArrayList();

        CollectionUtil.iterate(list, new Treatment<String>()
        {
            
            
            @Override
            public void apply(final String _value) {
            
            
                arrayList.add(_value);
            }
            
        });
        assertEquals(arrayList, list);
    }
    
}
