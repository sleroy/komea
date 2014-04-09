
package org.komea.product.backend.storage;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;



public class DAOStorageIndexTest
{
    
    
    @Test
    public final void testGetObjectIndex() throws Exception {
    
    
        final DAOStorageIndex<String> daoStorageIndex = new DAOStorageIndex<String>();
        assertTrue(daoStorageIndex.getObjectIndex().isEmpty());
        daoStorageIndex.setObjectIndex(Collections.singletonList("SR"));
        Assert.assertEquals(1, daoStorageIndex.getObjectIndex().size());
        Assert.assertEquals("SR", daoStorageIndex.getObjectIndex().get(0));
        
    }
    
    
}
