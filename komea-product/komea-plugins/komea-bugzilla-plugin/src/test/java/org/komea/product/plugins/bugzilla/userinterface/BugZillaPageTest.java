/**
 * 
 */

package org.komea.product.plugins.bugzilla.userinterface;



import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZEventService;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class BugZillaPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IBZEventService.class));
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IBZConfigurationDAO.class));
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.bugzilla.userinterface.BugZillaPage#BugZillaPage(org.apache.wicket.request.mapper.parameter.PageParameters)}
     * .
     */
    @Test @Ignore
    public final void testBugZillaPage() throws Exception {
    
    
        wicketRule.testStart(BugZillaPage.class);
    }
    
    
}
