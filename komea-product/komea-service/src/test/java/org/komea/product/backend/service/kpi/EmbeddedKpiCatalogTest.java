/**
 *
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class EmbeddedKpiCatalogTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IEmbeddedKpiCatalog embeddedKpiCatalog;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.EmbeddedKpiCatalog#init()}.
     */
    @Test
    public final void testInit() throws Exception {
    
    
        assertEquals(1, embeddedKpiCatalog.getKpiDefinitions().size());
        
    }
    
}
