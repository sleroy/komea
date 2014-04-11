/**
 * 
 */

package org.komea.product.wicket.person;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class PersonPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IPersonService.class));
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IPersonRoleService.class));
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.person.PersonPage#PersonPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test 
    public final void testPersonPage() throws Exception {
    
    
        wicketRule.testStart(PersonPage.class);
    }
    
}
