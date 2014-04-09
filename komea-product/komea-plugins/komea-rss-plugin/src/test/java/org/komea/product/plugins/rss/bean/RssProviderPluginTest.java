/**
 * 
 */

package org.komea.product.plugins.rss.bean;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.rss.repositories.api.IRssRepositories;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class RssProviderPluginTest
{
    
    
    @Mock
    private ICronRegistryService cronRegistryService;
    
    
    @Mock
    private IEventPushService    esperEngine;
    
    
    @Mock
    private IWicketAdminService  pluginAdminService;
    
    
    @Mock
    private ISettingService      registry;
    
    
    @InjectMocks
    private RssProviderPlugin    rssProviderBean;
    @Mock
    private IRssRepositories     rssRepository;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.rss.bean.RssProviderPlugin#notifyPropertyChanged(org.komea.product.database.model.Setting)}.
     */
    @Test
    public final void testInitializePluginWithProperties() throws Exception {
    
    
        // TODO
        // throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.rss.bean.RssProviderPlugin#initializeProvider()}.
     */
    @Test
    public final void testInitializeProvider() throws Exception {
    
    
        // TODO
        // throw new RuntimeException("not yet implemented");
    }
    
    
}
