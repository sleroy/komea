/**
 * 
 */

package org.komea.product.plugins.mantis.service;



import org.junit.Test;
import org.komea.product.backend.groovy.IGroovyEngineService;
import org.komea.product.plugins.mantis.service.MantisProviderPlugin;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */

public class MantisProviderPluginTest extends AbstractSpringIntegrationTestCase
{
    
    
    private final MantisProviderPlugin mantisProviderPlugin = new MantisProviderPlugin();
    
    @Autowired
    private IGroovyEngineService   groovyEngineService;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.mantis.service.MantisProviderPlugin#bzOpenBugs()}.
     */
    @Test
    public void testBzClosedBugs() throws Exception {
    
    
        assertTrue(groovyEngineService.isValidFormula(mantisProviderPlugin.bzClosedBugs()
                .getEsperRequest()));
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.mantis.service.MantisProviderPlugin#bzOpenBugs()}.
     */
    @Test
    public void testBzOpenBugs() throws Exception {
    
    
        assertTrue(groovyEngineService.isValidFormula(mantisProviderPlugin.bzOpenBugs()
                .getEsperRequest()));
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.mantis.service.MantisProviderPlugin#bzOpenBySeverityBugs(java.lang.String)}.
     */
    @Test
    public void testBzOpenBySeverityBugs() throws Exception {
    
    
        assertTrue(groovyEngineService.isValidFormula(mantisProviderPlugin.bzOpenBySeverityBugs(
                "critical").getEsperRequest()));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.mantis.service.MantisProviderPlugin#bzOpenNotFixedBugs()}.
     */
    @Test
    public void testBzOpenNotFixedBugs() throws Exception {
    
    
        assertTrue(groovyEngineService.isValidFormula(mantisProviderPlugin.bzOpenNotFixedBugs()
                .getEsperRequest()));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.mantis.service.MantisProviderPlugin#bzTotalBugs()}.
     */
    @Test
    public void testBzTotalBugs() throws Exception {
    
    
        assertTrue(groovyEngineService.isValidFormula(mantisProviderPlugin.bzTotalBugs()
                .getEsperRequest()));
    }
    
}
