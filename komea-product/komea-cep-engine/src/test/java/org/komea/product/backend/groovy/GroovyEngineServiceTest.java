/**
 * 
 */

package org.komea.product.backend.groovy;



import java.util.Comparator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;



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
        groovyEngineService.init();
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.groovy.GroovyEngineService#parseGroovyScript(java.io.File)}.
     */
    @Test
    public final void testParseGroovyScript() throws Exception {
    
    
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
        final Comparator<Integer> comparator = groovyEngineService.parseGroovyScript(scriptExample);
        assertEquals("Test functionality ", comparator.compare(1, 2),
                Integer.valueOf(1).compareTo(2));
        
    }
}
