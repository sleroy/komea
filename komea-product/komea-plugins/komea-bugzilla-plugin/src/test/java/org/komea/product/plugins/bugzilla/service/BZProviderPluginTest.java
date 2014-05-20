/**
 * 
 */

package org.komea.product.plugins.bugzilla.service;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.groovy.GroovyEngineService;
import org.komea.product.backend.groovy.QueryValidatorObject;
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
    
    
    private GroovyEngineService  groovyEngineService;
    @Mock
    private Logger               logger;
    
    
    @Mock
    private ICronRegistryService registryService;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZProviderPlugin#bzOpenBugs()}.
     */
    @Test
    public void testBzOpenBugs() throws Exception {
    
    
        new QueryValidatorObject().validateQuery(bZProviderPlugin.bzOpenBugs().getEsperRequest());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZProviderPlugin#bzOpenBySeverityBugs(java.lang.String)}.
     */
    @Test
    public void testBzOpenBySeverityBugs() throws Exception {
    
    
        new QueryValidatorObject().validateQuery(bZProviderPlugin.bzOpenBySeverityBugs("critical")
                .getEsperRequest());
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZProviderPlugin#bzOpenNotFixedBugs()}.
     */
    @Test
    public void testBzOpenNotFixedBugs() throws Exception {
    
    
        new QueryValidatorObject().validateQuery(bZProviderPlugin.bzOpenNotFixedBugs()
                .getEsperRequest());
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZProviderPlugin#bzTotalBugs()}.
     */
    @Test
    public void testBzTotalBugs() throws Exception {
    
    
        new QueryValidatorObject().validateQuery(bZProviderPlugin.bzTotalBugs().getEsperRequest());
    }
    
    
}
