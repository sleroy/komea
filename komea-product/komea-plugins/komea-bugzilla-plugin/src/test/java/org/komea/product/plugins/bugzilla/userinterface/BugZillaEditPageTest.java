/**
 * 
 */

package org.komea.product.plugins.bugzilla.userinterface;



import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZEventService;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class BugZillaEditPageTest
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
     * {@link org.komea.product.plugins.bugzilla.userinterface.BugZillaEditPage#BugZillaEditPage(org.apache.wicket.request.mapper.parameter.PageParameters)}
     * .
     */
    @Test 
    public final void testBugZillaEditPagePageParameters() throws Exception {
    
    
        wicketRule.testStart(BugZillaEditPage.class);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.bugzilla.userinterface.BugZillaEditPage#BugZillaEditPage(org.apache.wicket.request.mapper.parameter.PageParameters, org.komea.product.plugins.bugzilla.model.BZServerConfiguration)}
     * .
     */
    @Test 
    public final void testBugZillaEditPagePageParametersBZServerConfiguration() throws Exception {
    
    
        final WicketTester wicketTester = wicketRule.newWicketTester();
        try {
            wicketTester.startPage(new BugZillaEditPage(new PageParameters(),
                    new BZServerConfiguration()));
            wicketTester.assertNoErrorMessage();
        } finally {
            wicketTester.destroy();
        }
    }
    
}
