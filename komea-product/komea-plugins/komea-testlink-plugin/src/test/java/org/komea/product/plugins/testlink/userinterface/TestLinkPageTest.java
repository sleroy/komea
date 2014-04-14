/**
 * 
 */

package org.komea.product.plugins.testlink.userinterface;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class TestLinkPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(ITestLinkServerDAO.class));
        
    }
    
    
    @Test 
    public void testHomePage() throws Exception {
    
    
        wicketRule.testStart(TestLinkPage.class);
    }
}
