
package org.komea.product.backend.genericservice;



import org.junit.Test;
import org.komea.product.database.api.IHasKey;
import org.mockito.Mockito;



public class DAOEventRegistryTest
{
    
    
    @Test 
    public void testNotifyDeleted() throws Exception {
    
    
        final DAOEventRegistry daoEventRegistry = new DAOEventRegistry();
        daoEventRegistry.notifyDeleted(Mockito.mock(IHasKey.class));
    }
    
    
    @Test 
    public void testNotifyUpdated() throws Exception {
    
    
        final DAOEventRegistry daoEventRegistry = new DAOEventRegistry();
        daoEventRegistry.notifyUpdated(Mockito.mock(IHasKey.class));
    }
    
}
