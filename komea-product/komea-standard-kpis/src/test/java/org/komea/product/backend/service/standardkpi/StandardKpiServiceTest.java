/**
 * 
 */

package org.komea.product.backend.service.standardkpi;



import groovy.lang.Script;

import org.junit.Test;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.standardkpi.IStandardKpiService;
import org.komea.product.backend.groovy.IGroovyEngineService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class StandardKpiServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IGroovyEngineService groovyEngineService;
    
    
    @Autowired
    private IStandardKpiService  standardKpiService;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.StandardKpiService#numberOfProjectPerUser()}.
     */
    @Test
    public void testNumberOfProjectPerUser() throws Exception {
    
    
        final StandardKpiService standardKpiServiceTest = new StandardKpiService();
        final Script parseScript =
                groovyEngineService.parseScript(standardKpiServiceTest.numberOfProjectPerUser());
        
        
        final IQuery<KpiResult> query = (IQuery<KpiResult>) parseScript.run();
        assertNotNull(query);
        assertNotNull(query.getResult());
        
        assertEquals("Administrator should have an entry", 1, query.getResult().size());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.StandardKpiService#numberOfUsersPerProject()}.
     */
    @Test
    public void testNumberOfUsersPerProject() throws Exception {
    
    
        final StandardKpiService standardKpiServiceTest = new StandardKpiService();
        final Script parseScript =
                groovyEngineService.parseScript(standardKpiServiceTest.numberOfUsersPerProject());
        
        
        final IQuery<KpiResult> query = (IQuery<KpiResult>) parseScript.run();
        assertNotNull(query);
        assertNotNull(query.getResult());
        assertTrue(query.getResult().isEmpty());
    }
}
