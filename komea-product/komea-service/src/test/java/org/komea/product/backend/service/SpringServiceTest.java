/**
 * 
 */

package org.komea.product.backend.service;



import org.junit.Test;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;



/**
 * @author sleroy
 */

public class SpringServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    public class SpringOucha
    {
        
        
        @Autowired
        private ISpringService autowiredService;
        
        
        
        public ISpringService getAutowiredService() {
        
        
            return autowiredService;
        }
        
        
        public void setAutowiredService(final ISpringService _springService) {
        
        
            autowiredService = _springService;
        }
    }
    
    
    
    @Autowired
    private ISpringService springService;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.SpringService#autowirePojo(java.lang.Object)}.
     */
    @Test
    public final void testAutowirePojo() throws Exception {
    
    
        final SpringOucha pojo = new SpringOucha();
        springService.autowirePojo(pojo);
        assertNotNull(pojo);
    }
    
}
