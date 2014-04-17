/**
 * 
 */

package org.komea.product.backend.service.entities;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.generic.IDAOEventRegistry;
import org.komea.product.database.dao.ProviderDao;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class ProviderServiceTest
{
    
    
    @Mock
    private IDAOEventRegistry daoEventRegistry;
    
    
    @InjectMocks
    private ProviderService   providerService;
    @Mock
    private ProviderDao       requiredDao;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.ProviderService#removeProvider(org.komea.product.database.model.Provider)}.
     */
    @Test
    public final void testRemoveProvider() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.ProviderService#setRequiredDao(org.komea.product.database.dao.ProviderDao)}.
     */
    @Test
    public final void testSetRequiredDao() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
