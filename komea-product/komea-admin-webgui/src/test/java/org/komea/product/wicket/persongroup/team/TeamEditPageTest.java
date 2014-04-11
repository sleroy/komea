/**
 * 
 */

package org.komea.product.wicket.persongroup.team;



import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class TeamEditPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IPersonGroupService.class));
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IPersonService.class));
         wicketRule.getApplicationContextMock().putBean(Mockito.mock(IProjectService.class));
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.persongroup.team.TeamEditPage#TeamEditPage(org.apache.wicket.request.mapper.parameter.PageParameters)}
     * .
     */
    @Test 
    public final void testTeamEditPagePageParameters() throws Exception {
    
    
        wicketRule.testStart(TeamEditPage.class);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.persongroup.team.TeamEditPage#TeamEditPage(org.apache.wicket.request.mapper.parameter.PageParameters, org.komea.product.database.model.PersonGroup)}
     * .
     */
    @Test 
    public final void testTeamEditPagePageParametersPersonGroup() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        try {
            newWicketTester.startPage(new TeamEditPage(new PageParameters(), new PersonGroup()));
            newWicketTester.assertNoErrorMessage();
        } finally {
            newWicketTester.destroy();
        }
        
        
    }
    
}
