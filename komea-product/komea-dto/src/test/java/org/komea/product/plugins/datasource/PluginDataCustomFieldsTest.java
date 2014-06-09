/**
 *
 */

package org.komea.product.plugins.datasource;



import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class PluginDataCustomFieldsTest
{
    
    
    public static class A
    {
        
        
        private String p1;
        private String p2;
        public String getP1() {
        
        
            return p1;
        }
        public void setP1(String _p1) {
        
        
            p1 = _p1;
        }
        public String getP2() {
        
        
            return p2;
        }
        public void setP2(String _p2) {
        
        
            p2 = _p2;
        }
    }
    
    
    
    private PluginDataCustomFields dataCustomFields;
    
    
    
    @Before
    public void before() {


        dataCustomFields = new PluginDataCustomFields();
    }


    /**
     * Test method for {@link org.komea.product.plugins.datasource.PluginDataCustomFields#containsField(java.lang.String)}.
     */
    @Test
    public final void testContainsField() throws Exception {
    
    
        assertFalse(dataCustomFields.containsField("p1"));
        
        dataCustomFields.put("p1", "v");
        assertTrue(dataCustomFields.containsField("p1"));
    }


    /**
     * Test method for {@link org.komea.product.plugins.datasource.PluginDataCustomFields#getField(java.lang.String)}.
     */
    @Test
    public final void testGetField() throws Exception {
    
    
        assertNotNull(dataCustomFields.getField("p1"));
        dataCustomFields.put("p1", "v");
        assertEquals("v", dataCustomFields.getField("p1"));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.datasource.PluginDataCustomFields#getFieldsAsMap()}.
     */
    @Test
    public final void testGetFieldsAsMap() throws Exception {


        dataCustomFields.put("p1", "v");
        dataCustomFields.put("p2", "v2");
        final Map<String, String> asMap = dataCustomFields.getFieldsAsMap();
        assertEquals("v", asMap.get("p1"));
        assertEquals("v2", asMap.get("p2"));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.datasource.PluginDataCustomFields#getPojo(java.lang.Class)}.
     */
    @Test
    public final void testGetPojo() throws Exception {
    
    
        dataCustomFields.put("p1", "v");
        dataCustomFields.put("p2", "v2");
        final A a = dataCustomFields.getPojo(A.class);
        assertEquals("v", a.getP1());
        assertEquals("v2", a.getP2());
        
    }
    
    
}
