/**
 * 
 */

package org.komea.product.wicket.persongroup.department;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class DepartmentPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IPersonGroupService.class));
    }
    
    
    /**
     * v
     * Test method for
     * {@link org.komea.product.wicket.persongroup.department.DepartmentPage#DepartmentPage(org.apache.wicket.request.mapper.parameter.PageParameters)}
     * .
     */
    @Test @Ignore
    public final void testDepartmentPage() throws Exception {
    
    
        wicketRule.testStart(DepartmentPage.class);
    }
    
}
