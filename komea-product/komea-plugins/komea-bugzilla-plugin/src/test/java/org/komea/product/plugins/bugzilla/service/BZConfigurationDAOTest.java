/**
 * 
 */

package org.komea.product.plugins.bugzilla.service;



import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class BZConfigurationDAOTest
{
    
    
    @InjectMocks
    private BZConfigurationDAO    bZConfigurationDAO;
    
    
    @Mock
    private IPluginStorageService pluginStorage;
    @Mock
    private IBZServerProxyFactory serverProxyFactory;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.bugzilla.service.BZConfigurationDAO#delete(org.komea.product.plugins.bugzilla.model.BZServerConfiguration)}
     * .
     */
    @Test
    @Ignore
    public final void testDelete() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZConfigurationDAO#getPluginStorage()}.
     */
    @Test
    @Ignore
    public final void testGetPluginStorage() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZConfigurationDAO#getServerProxyFactory()}.
     */
    @Test
    @Ignore
    public final void testGetServerProxyFactory() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.bugzilla.service.BZConfigurationDAO#saveOrUpdate(org.komea.product.plugins.bugzilla.model.BZServerConfiguration, java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testSaveOrUpdate() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZConfigurationDAO#selectAll()}.
     */
    @Test
    @Ignore
    public final void testSelectAll() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
