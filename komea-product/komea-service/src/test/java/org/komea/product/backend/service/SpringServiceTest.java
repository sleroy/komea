/**
 * 
 */

package org.komea.product.backend.service;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class SpringServiceTest
{
    
    
    @Mock
    private ApplicationContext applicationContext;
    @InjectMocks
    private SpringService      springService;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.SpringService#autowirePojo(java.lang.Object)}.
     */
    @Test
    public final void testAutowirePojo() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
}
