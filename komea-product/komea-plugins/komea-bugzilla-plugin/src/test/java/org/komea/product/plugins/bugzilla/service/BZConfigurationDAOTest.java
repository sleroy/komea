/**
 * 
 */

package org.komea.product.plugins.bugzilla.service;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BZConfigurationDAOTest
{
    
    
    @Mock
    private IPluginStorageService        pluginStorage;
    
    
    @Mock
    private IBZServerProxyFactory        serverProxyFactory;
    @InjectMocks
    private BZConfigurationDAO bZConfigurationDAO;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZConfigurationDAO#delete(org.komea.product.plugins.bugzilla.model.BZServerConfiguration)}.
     */
    @Test
    public final void testDelete() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZConfigurationDAO#getPluginStorage()}.
     */
    @Test
    public final void testGetPluginStorage() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZConfigurationDAO#getServerProxyFactory()}.
     */
    @Test
    public final void testGetServerProxyFactory() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZConfigurationDAO#saveOrUpdate(org.komea.product.plugins.bugzilla.model.BZServerConfiguration, java.lang.String)}.
     */
    @Test
    public final void testSaveOrUpdate() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZConfigurationDAO#selectAll()}.
     */
    @Test
    public final void testSelectAll() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
