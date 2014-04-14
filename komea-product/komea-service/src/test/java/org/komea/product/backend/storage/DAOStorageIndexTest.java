
package org.komea.product.backend.storage;



import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;



public class DAOStorageIndexTest
{
    
    
    @Test 
    public final void testGetObjectIndex() throws Exception {
    
    
        final DAOStorageIndex<MockID> daoStorageIndex = new DAOStorageIndex<MockID>();
        assertTrue(daoStorageIndex.getObjectIndex().isEmpty());
        final MockID object = new MockID("SR");
        object.setId(0);
        daoStorageIndex.put(object);
        Assert.assertEquals(1, daoStorageIndex.getObjectIndex().size());
        Assert.assertEquals("SR", daoStorageIndex.getObjectIndex().get(0).getStr());
        
    }
}
