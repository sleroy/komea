/**
 * 
 */

package org.komea.product.plugins.bugzilla.service;



import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class BZProviderPluginTest
{
    
    
    @Mock
    private IBZConfigurationDAO  bugZillaConfiguration;
    
    
    @InjectMocks
    private BZProviderPlugin     bZProviderPlugin;
    
    
    @Mock
    private IEventTypeService    evenTypeService;
    
    
    @Mock
    private Logger               logger;
    @Mock
    private ICronRegistryService registryService;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZProviderPlugin#init()}.
     */
    @Test
    @Ignore
    public final void testInit() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
