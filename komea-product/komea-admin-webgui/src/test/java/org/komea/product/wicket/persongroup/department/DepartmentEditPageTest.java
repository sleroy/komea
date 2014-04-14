/**
 * 
 */

package org.komea.product.wicket.persongroup.department;



import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class DepartmentEditPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IPersonGroupService.class));
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.persongroup.department.DepartmentEditPage#DepartmentEditPage(org.apache.wicket.request.mapper.parameter.PageParameters)}
     * .
     */
    @Test 
    public final void testDepartmentEditPagePageParameters() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        final PageParameters parameters = new PageParameters();
        newWicketTester.startPage(new DepartmentEditPage(parameters));
        newWicketTester.assertNoErrorMessage();
        newWicketTester.destroy();
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.persongroup.department.DepartmentEditPage#DepartmentEditPage(org.apache.wicket.request.mapper.parameter.PageParameters, org.komea.product.database.model.PersonGroup)}
     * .
     */
    @Test 
    public void testDepartmentEditPagePageParametersPersonGroup() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        final PageParameters parameters = new PageParameters();
        newWicketTester.startPage(new DepartmentEditPage(parameters, new PersonGroup()));
        newWicketTester.assertNoErrorMessage();
        newWicketTester.destroy();
    }
    
}
