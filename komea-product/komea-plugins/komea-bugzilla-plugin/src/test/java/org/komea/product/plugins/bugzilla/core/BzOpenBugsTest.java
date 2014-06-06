/**
 * 
 */

package org.komea.product.plugins.bugzilla.core;



import org.junit.Test;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.service.kpi.GroovyScriptLoader;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.bugtracker.kpis.IssueProjectKPI;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class BzOpenBugsTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IGroovyEngineService groovyEngineService;
    
    
    
    @Test
    public void test() {
    
    
        groovyEngineService.registerClassImport(IssueProjectKPI.class);
        final GroovyScriptLoader groovyScriptLoader =
                new GroovyScriptLoader("scripts/BzOpenBugScript.groovy");
        final String scriptGroovyAsString = groovyScriptLoader.load();
        final Kpi kpi = new Kpi();
        kpi.setEsperRequest(scriptGroovyAsString);
        
        final IQuery<KpiResult> parseQuery = groovyEngineService.parseQuery(kpi);
        parseQuery.getResult();
        
        
    }
}
