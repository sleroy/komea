/**
 * 
 */

package org.komea.product.plugins.bugzilla.core;



import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZEventService;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class BZCheckerCronTest
{
    
    
    @Mock
    private IEventPushService     alertService;
    
    
    @Mock
    private IBZConfigurationDAO   bugZillaConfiguration;
    
    
    @InjectMocks
    private BZCheckerCron         bZCheckerCron;
    
    
    @Mock
    private IBZEventService       eventService;
    @Mock
    private IBZServerProxyFactory proxyFactory;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.bugzilla.core.BZCheckerCron#buildBugZillaStatistics(java.util.List, java.lang.String, org.komea.product.plugins.bugzilla.model.BZServerConfiguration)}
     * .
     */
    @Test
    @Ignore
    public final void testBuildBugZillaStatistics() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.bugzilla.core.BZCheckerCron#buildEventTypes(org.komea.product.plugins.bugzilla.model.BZServerConfiguration, org.komea.product.plugins.bugzilla.api.IBZServerProxy, java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testBuildEventTypes() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.bugzilla.core.BZCheckerCron#buildStatisticsPerProject(org.komea.product.plugins.bugzilla.model.BZServerConfiguration, org.komea.product.plugins.bugzilla.api.IBZServerProxy, java.lang.String)}
     * .
     */
    @Test
    @Ignore
    public final void testBuildStatisticsPerProject() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.core.BZCheckerCron#checkServers()}.
     */
    @Test
    @Ignore
    public final void testCheckServers() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
