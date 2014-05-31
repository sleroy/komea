/**
 * 
 */

package org.komea.product.plugins.mantis.service;



import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.mantis.api.IMantisServerProxyFactory;
import org.komea.product.plugins.mantis.service.MantisConfigurationDAO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class MantisConfigurationDAOTest
{
    
    
    @InjectMocks
    private MantisConfigurationDAO    mantisConfigurationDAO;
    
    
    @Mock
    private IPluginStorageService pluginStorage;
    @Mock
    private IMantisServerProxyFactory serverProxyFactory;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.mantis.service.MantisConfigurationDAO#delete(org.komea.product.plugins.mantis.model.MantisServerConfiguration)}
     * .
     */
    @Test
    @Ignore
    public final void testDelete() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.mantis.service.MantisConfigurationDAO#getPluginStorage()}.
     */
    @Test
    @Ignore
    public final void testGetPluginStorage() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.mantis.service.MantisConfigurationDAO#getServerProxyFactory()}.
     */
    @Test
    @Ignore
    public final void testGetServerProxyFactory() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.mantis.service.MantisConfigurationDAO#saveOrUpdate(org.komea.product.plugins.mantis.model.MantisServerConfiguration, java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testSaveOrUpdate() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.mantis.service.MantisConfigurationDAO#selectAll()}.
     */
    @Test
    @Ignore
    public final void testSelectAll() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
}
