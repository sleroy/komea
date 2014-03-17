/**
 * 
 */

package org.komea.product.plugins.rss.bean;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.rss.repositories.api.IRssRepositories;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;



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
    private IRssExampleFeedBean  feed;
    
    
    @Mock
    private IWicketAdminService  pluginAdminService;
    
    
    @Mock
    private ISettingService      registry;
    
    
    @InjectMocks
    private RssProviderPlugin      rssProviderBean;
    @Mock
    private IRssRepositories     rssRepository;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.rss.bean.RssProviderPlugin#initializeProvider()}.
     */
    @Test
    public final void testInitializeProvider() throws Exception {
    
    
        // TODO
        // throw new RuntimeException("not yet implemented");
    }
    
    
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
     * Test method for {@link org.komea.product.plugins.rss.bean.RssProviderPlugin#prepareJobMapForCron()}.
     */
    @Test
    public final void testPrepareJobMapForCron() throws Exception {
    
    
        final RssCronJob rssCronJob = new RssCronJob();
        
        final RssProviderPlugin providerBean = new RssProviderPlugin();
        providerBean.setRssRepository(rssRepository);
        
        final JobDataMap prepareJobMapForCron = providerBean.prepareJobMapForCron();
        
        final JobExecutionContext context = Mockito.mock(JobExecutionContext.class);
        Mockito.when(context.getMergedJobDataMap()).thenReturn(prepareJobMapForCron);
        
        final IRssRepositories value = (IRssRepositories) prepareJobMapForCron.get("repository");
        
        Mockito.when(value.getDAO()).thenReturn(Mockito.mock(IDAOObjectStorage.class));
        //
        
        rssCronJob.execute(context);
    }
    
}
