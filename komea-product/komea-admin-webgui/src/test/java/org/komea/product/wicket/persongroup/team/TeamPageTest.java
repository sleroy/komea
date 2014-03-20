/**
 * 
 */

package org.komea.product.wicket.persongroup.team;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class TeamPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IPersonGroupService.class));
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.persongroup.team.TeamPage#TeamPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testTeamPage() throws Exception {
    
    
        wicketRule.testStart(TeamPage.class);
    }
    
}
