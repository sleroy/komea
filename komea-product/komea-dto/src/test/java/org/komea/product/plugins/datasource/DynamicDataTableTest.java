/**
 *
 */

package org.komea.product.plugins.datasource;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.komea.product.backend.utils.IFilter;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class DynamicDataTableTest
{


    final ArrayList<String> arrayList = Lists.newArrayList("S1", "S2");



    /**
     * Test method for {@link org.komea.product.plugins.datasource.DynamicDataTable#getData()}.
     */
    @Test
    public final void testGetData() throws Exception {


        final DynamicDataTable<String> dynamicDataTable = new DynamicDataTable<String>(arrayList);
        assertEquals(arrayList, dynamicDataTable.getData());
    }


    /**
     * Test method for {@link org.komea.product.plugins.datasource.DynamicDataTable#searchData(org.komea.product.backend.utils.IFilter)}.
     */
    @Test
    public final void testSearchData() throws Exception {
    
    
        final DynamicDataTable<String> dynamicDataTable = new DynamicDataTable<String>(arrayList);
        final List<String> data = dynamicDataTable.searchData(new IFilter<String>()
                {


            @Override
            public boolean matches(final String _task) {
            
            
                return "S1".equals(_task);
            }
                });
        assertEquals(1, data.size());
    }

}
