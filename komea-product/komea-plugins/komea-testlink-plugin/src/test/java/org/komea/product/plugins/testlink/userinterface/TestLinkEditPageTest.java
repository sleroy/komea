/**
 * 
 */

package org.komea.product.plugins.testlink.userinterface;



import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.auth.IPasswordEncoder;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class TestLinkEditPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketTester = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        wicketTester.getApplicationContextMock().putBean(mock(IPersonService.class));
        wicketTester.getApplicationContextMock().putBean(mock(IPersonRoleService.class));
        wicketTester.getApplicationContextMock().putBean(mock(ITestLinkServerDAO.class));
        wicketTester.getApplicationContextMock().putBean(mock(IProjectService.class));
        wicketTester.getApplicationContextMock().putBean(mock(IPersonGroupService.class));
        wicketTester.getApplicationContextMock().putBean(mock(IPasswordEncoder.class));
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.userinterface.TestLinkEditPage#TestLinkEditPage(org.apache.wicket.request.mapper.parameter.PageParameters)}
     * .
     */
    @Test @Ignore
    public void testTestLinkEditPagePageParameters() throws Exception {
    
    
        wicketTester.testStart(TestLinkEditPage.class);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.userinterface.TestLinkEditPage#TestLinkEditPage(org.apache.wicket.request.mapper.parameter.PageParameters, org.komea.product.plugins.testlink.model.TestLinkServer)}
     * .
     */
    @Test @Ignore
    public void testTestLinkEditPagePageParametersTestLinkServer() throws Exception {
    
    
        final WicketTester wicketRule = wicketTester.newWicketTester();
        try {
            wicketRule.startPage(new TestLinkEditPage(new PageParameters(), new TestLinkServer()));
            wicketRule.assertNoErrorMessage();
        } finally {
            wicketRule.destroy();
        }
    }
}
