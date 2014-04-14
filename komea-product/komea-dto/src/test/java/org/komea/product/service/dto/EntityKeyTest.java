/**
 * 
 */

package org.komea.product.service.dto;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.enums.EntityType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class EntityKeyTest
{
    
    
    /**
     * Test method for {@link org.komea.product.service.dto.EntityKey#compareTo(org.komea.product.service.dto.EntityKey)}.
     */
    @SuppressWarnings("boxing")
    @Test 
    public final void testCompareTo() throws Exception {
    
    
        final EntityKey e1 = EntityKey.of(EntityType.DEPARTMENT, 1);
        final EntityKey e2 = EntityKey.of(EntityType.DEPARTMENT, 2);
        assertTrue(e1.compareTo(e2) < 0);
        assertTrue(e2.compareTo(e1) > 0);
        assertTrue(e1.compareTo(e1) == 0);
        assertTrue(e2.compareTo(e2) == 0);
    }
    
    
    /**
     * Test method for {@link org.komea.product.service.dto.EntityKey#EntityKey()}.
     */
    @Test 
    public final void testEntityKey() throws Exception {
    
    
        final EntityKey entityKey = new EntityKey();
        assertNull(entityKey.getEntityType());
        assertNull(entityKey.getId());
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.service.dto.EntityKey#EntityKey(org.komea.product.database.enums.EntityType, java.lang.Integer)}.
     */
    @Test 
    public final void testEntityKeyEntityTypeInteger() throws Exception {
    
    
        final EntityKey entityKey = new EntityKey(EntityType.DEPARTMENT, 1);
        assertEquals(EntityType.DEPARTMENT, entityKey.getEntityType());
        assertEquals(Integer.valueOf(1), entityKey.getId());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.service.dto.EntityKey#equals(java.lang.Object)}.
     */
    @Test 
    public final void testEquals() throws Exception {
    
    
        final EntityKey e1 = EntityKey.of(EntityType.DEPARTMENT, 1);
        final EntityKey e2 = EntityKey.of(EntityType.DEPARTMENT, 2);
        final EntityKey e3 = EntityKey.of(EntityType.DEPARTMENT, 1);
        final EntityKey e4 = EntityKey.of(EntityType.PERSON, 1);
        assertNotEquals(e1, e2);
        assertNotEquals(e1, e4);
        assertEquals(e1, e3);
    }
    
    
    /**
     * Test method for {@link org.komea.product.service.dto.EntityKey#isEntityReferenceKey()}.
     */
    @Test 
    public final void testIsEntityReferenceKey() throws Exception {
    
    
        final EntityKey e1 = EntityKey.of(EntityType.DEPARTMENT, 1);
        assertTrue(e1.isEntityReferenceKey());
        final EntityKey e2 = EntityKey.of(EntityType.DEPARTMENT);
        Assert.assertFalse(e2.isEntityReferenceKey());
    }
    
    
    /**
     * Test method for {@link org.komea.product.service.dto.EntityKey#isEntityTypeKey()}.
     */
    @Test 
    public final void testIsEntityTypeKey() throws Exception {
    
    
        final EntityKey e2 = EntityKey.of(EntityType.DEPARTMENT);
        assertTrue(e2.isEntityTypeKey());
        final EntityKey e3 = EntityKey.of(EntityType.DEPARTMENT, 1);
        Assert.assertFalse(e3.isEntityTypeKey());
    }
    
    
    /**
     * Test method for {@link org.komea.product.service.dto.EntityKey#isUncompleteKey()}.
     */
    @Test 
    public void testIsUncompleteKey() throws Exception {
    
    
        final EntityKey e2 = EntityKey.of(EntityType.DEPARTMENT);
        assertFalse(e2.isUncompleteKey());
        assertTrue(new EntityKey(null, null).isUncompleteKey());
    }
    
    
    /**
     * Test method for {@link org.komea.product.service.dto.EntityKey#toString()}.
     */
    @Test 
    public final void testToString() throws Exception {
    
    
        assertNotNull(EntityKey.of(EntityType.DEPARTMENT).toString());
        Assert.assertFalse(EntityKey.of(EntityType.DEPARTMENT).toString().isEmpty());
    }
}
