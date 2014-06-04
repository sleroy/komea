/**
 * 
 */

package org.komea.product.backend.groovy;



import groovy.lang.Script;

import java.util.Comparator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.eventory.utils.ClassUtils;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.database.model.Kpi;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;



/**
 * @author sleroy
 */
public class GroovyEngineServiceTest
{
    
    
    private GroovyEngineService groovyEngineService;
    
    
    
    @After
    public void after() {
    
    
        groovyEngineService.destroy();
    }
    
    
    @Before
    public void before() {
    
    
        groovyEngineService = new GroovyEngineService();
        groovyEngineService.setSpringService(mock(ISpringService.class));
        groovyEngineService.init();
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.groovy.GroovyEngineService#parseClass(java.io.File)} .
     */
    @Test
    public final void testParseClass() throws Exception {
    
    
        final String scriptExample =
                "  \n"
                        + "    public  class GroovyEngineScript implements Comparator<Integer>\n"
                        + "    {\n"
                        + "        \n"
                        + "        \n"
                        + "        /*\n"
                        + "         * (non-Javadoc)\n"
                        + "         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)\n"
                        + "         */\n"
                        + "        @Override\n"
                        + "        public int compare(final Integer _arg0, final Integer _arg1) {\n"
                        + "        \n" + "        \n"
                        + "            return _arg0.compareTo(_arg1);\n" + "        }\n"
                        + "    }\n" + "    ";
        final Comparator<Integer> comparator =
                (Comparator<Integer>) ClassUtils.instantiate(groovyEngineService
                        .parseClass(scriptExample));
        assertEquals("Test functionality ", comparator.compare(1, 2),
                Integer.valueOf(1).compareTo(2));
        
    }
    
    
    @Test
    public void testParseScript() {
    
    
        final Kpi kpi = new Kpi();
        
        kpi.setEsperRequest("query_from_implementation(new org.komea.eventory.query.predefined.EmptyQueryDefinition())");
        kpi.setId(1);
        final Script parseGroovyScript = groovyEngineService.parseScript(kpi);
        System.out.println(parseGroovyScript.run());
    }
}
