/**
 * 
 */

package org.komea.product.plugins.testlink.core;



import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class TestLinkServerServiceTest
{
    
    
    @Mock
    private IDAOObjectStorage<TestLinkServer> daoStorage;
    
    
    @Mock
    private IPluginStorageService             pluginStorageService;
    @InjectMocks
    private TestLinkServerService             testLinkServerService;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.core.TestLinkServerService#saveOrUpdate(org.komea.product.plugins.testlink.model.TestLinkServer)}
     * .
     */
    @Test
    @Ignore
    public final void testSaveOrUpdateTestLinkServer() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.core.TestLinkServerService#saveOrUpdate(org.komea.product.plugins.testlink.model.TestLinkServer, java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testSaveOrUpdateTestLinkServerString() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkServerService#selectAll()}.
     */
    @Ignore
    @Test
    public final void testSelectAll() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
