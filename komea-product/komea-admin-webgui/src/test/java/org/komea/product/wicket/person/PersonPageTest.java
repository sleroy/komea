/**
 * 
 */

package org.komea.product.wicket.person;



import org.junit.Rule;
import org.junit.Test;
import org.komea.product.wicket.utils.WicketTesterMethodRule;



/**
 * @author sleroy
 */
public class PersonPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.person.PersonPage#PersonPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testPersonPage() throws Exception {
    
    
        wicketRule.testStart(PersonPage.class);
    }
    
}
