/**
 * 
 */

package org.komea.product.plugins.bugzilla.service;



import org.junit.Test;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */

public class BZProviderPluginTest extends AbstractSpringIntegrationTestCase
{
    
    
    private final BZProviderPlugin bZProviderPlugin = new BZProviderPlugin();
    
    @Autowired
    private IGroovyEngineService   groovyEngineService;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZProviderPlugin#bzOpenBugs()}.
     */
    @Test
    public void testBzClosedBugs() throws Exception {
    
    
        assertTrue(groovyEngineService.isValidFormula(bZProviderPlugin.bzClosedBugs()
                .getEsperRequest()));
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZProviderPlugin#bzOpenBugs()}.
     */
    @Test
    public void testBzOpenBugs() throws Exception {
    
    
        assertTrue(groovyEngineService.isValidFormula(bZProviderPlugin.bzOpenBugs()
                .getEsperRequest()));
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZProviderPlugin#bzOpenBySeverityBugs(java.lang.String)}.
     */
    @Test
    public void testBzOpenBySeverityBugs() throws Exception {
    
    
        assertTrue(groovyEngineService.isValidFormula(bZProviderPlugin.bzOpenBySeverityBugs(
                "critical").getEsperRequest()));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZProviderPlugin#bzOpenNotFixedBugs()}.
     */
    @Test
    public void testBzOpenNotFixedBugs() throws Exception {
    
    
        assertTrue(groovyEngineService.isValidFormula(bZProviderPlugin.bzOpenNotFixedBugs()
                .getEsperRequest()));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.service.BZProviderPlugin#bzTotalBugs()}.
     */
    @Test
    public void testBzTotalBugs() throws Exception {
    
    
        assertTrue(groovyEngineService.isValidFormula(bZProviderPlugin.bzTotalBugs()
                .getEsperRequest()));
    }
    
}
