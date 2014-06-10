/**
 *
 */

package org.komea.product.backend.groovy;



import groovy.lang.Binding;
import groovy.lang.Script;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.junit.Test;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.service.kpi.GroovyScriptLoader;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class KpiDslTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IGroovyEngineService groovyEngineService;
    
    
    
    @Test
    public void test() {
    
    
        final CompilerConfiguration config = new CompilerConfiguration();
        
        final Binding binding = new Binding();
        final Script script =
                groovyEngineService.parseScript(
                        new GroovyScriptLoader("scripts/dsl.groovy").load(), config, binding);
        script.run();
    }
}
