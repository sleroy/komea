/**
 * 
 */

package org.komea.product.wicket.persongroup.team;



import org.junit.Rule;
import org.junit.Test;
import org.komea.product.wicket.utils.WicketTesterMethodRule;



/**
 * @author sleroy
 */
public class TeamPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.persongroup.team.TeamPage#TeamPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testTeamPage() throws Exception {
    
    
        wicketRule.testStart(TeamPage.class);
    }
    
}
