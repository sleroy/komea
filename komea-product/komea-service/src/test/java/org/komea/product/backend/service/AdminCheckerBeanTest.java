/**
 * 
 */

package org.komea.product.backend.service;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminCheckerBeanTest
{
    
    
    @Mock
    private IPersonGroupService iPersonGroupService;
    @InjectMocks
    private AdminCheckerBean    adminCheckerBean;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.AdminCheckerBean#init()}.
     */
    @Test
    public final void testInit() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
}
