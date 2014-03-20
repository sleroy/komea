/**
 * 
 */

package org.komea.product.wicket.project;



import org.junit.Rule;
import org.junit.Test;
import org.komea.product.wicket.utils.WicketTesterMethodRule;



/**
 * @author sleroy
 */
public class ProjectPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.project.ProjectPage#ProjectPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testProjectPage() throws Exception {
    
    
        wicketRule.testStart(ProjectPage.class);
    }
    
}
