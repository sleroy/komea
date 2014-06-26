/**
 *
 */

package org.komea.product.plugins.datasource;



import java.io.Serializable;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class DataCustomFieldsTest
{


    public static class A
    {


        private String p1;
        private String p2;



        public String getP1() {


            return p1;
        }


        public String getP2() {


            return p2;
        }


        public void setP1(final String _p1) {


            p1 = _p1;
        }


        public void setP2(final String _p2) {


            p2 = _p2;
        }
    }
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.datasource.DataCustomFields#containsField(java.lang.String)}.
     */
    @Test
    public final void testContainsField() throws Exception {
    
    
        final DataCustomFields dataCustomFields = new DataCustomFields();
        assertFalse(dataCustomFields.containsField("p1"));

        dataCustomFields.put("p1", "v");
        assertTrue(dataCustomFields.containsField("p1"));
    }


    /**
     * Test method for {@link org.komea.product.plugins.datasource.DataCustomFields#getField(java.lang.String)}.
     */
    @Test
    public final void testGetField() throws Exception {
    
    
        final DataCustomFields dataCustomFields = new DataCustomFields();
        
        assertNull(dataCustomFields.getField("p2"));
        dataCustomFields.put("p2", "v");
        assertEquals("v", dataCustomFields.getField("p2"));
    }


    /**
     * Test method for {@link org.komea.product.plugins.datasource.DataCustomFields#getFieldsAsMap()}.
     */
    @Test
    public final void testGetFieldsAsMap() throws Exception {
    
    
        final DataCustomFields dataCustomFields = new DataCustomFields();
        
        dataCustomFields.put("p1", "v");
        dataCustomFields.put("p2", "v2");
        final Map<String, Serializable> asMap = dataCustomFields.getFieldsAsMap();
        assertEquals("v", asMap.get("p1"));
        assertEquals("v2", asMap.get("p2"));
    }


    /**
     * Test method for {@link org.komea.product.plugins.datasource.DataCustomFields#getPojo(java.lang.Class)}.
     */
    @Test
    public final void testGetPojo() throws Exception {
    
    
        final DataCustomFields dataCustomFields = new DataCustomFields();
        
        dataCustomFields.put("p1", "v");
        dataCustomFields.put("p2", "v2");
        final A a = dataCustomFields.getPojo(A.class);
        assertEquals("v", a.getP1());
        assertEquals("v2", a.getP2());

    }


}
