/**
 * 
 */

package org.komea.product.backend.service.standardkpi;



import org.junit.BeforeClass;
import org.junit.Test;
import org.komea.product.backend.groovy.GroovyEngineService;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class StandardKpiServiceTest
{
    
    
    private static GroovyEngineService groovyEngineService = new GroovyEngineService();
    
    
    
    @BeforeClass
    public static void bfc() {
    
    
        // TODO Auto-generated method stub
        
    }
    
    
    
    @Autowired
    private StandardKpiService standardKpiService;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.StandardKpiService#numberOfProjectPerUser()}.
     */
    @Test
    public void testNumberOfProjectPerUser() throws Exception {
    
    
        final StandardKpiService standardKpiServiceTest = new StandardKpiService();
        standardKpiServiceTest.numberOfProjectPerUser();
        
        
    }
    
}
