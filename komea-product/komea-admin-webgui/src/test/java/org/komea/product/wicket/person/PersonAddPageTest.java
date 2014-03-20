/**
 * 
 */

package org.komea.product.wicket.person;



import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.utils.WicketTesterMethodRule;



/**
 * @author sleroy
 */
public class PersonAddPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.person.PersonAddPage#PersonAddPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testPersonAddPagePageParameters() throws Exception {
    
    
        wicketRule.testStart(PersonAddPage.class);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.person.PersonAddPage#PersonAddPage(org.apache.wicket.request.mapper.parameter.PageParameters, org.komea.product.database.model.Person)}
     * .
     */
    @Test
    public final void testPersonAddPagePageParametersPerson() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        try {
            newWicketTester.startPage(new PersonAddPage(new PageParameters(), new Person()));
            newWicketTester.assertNoErrorMessage();
        } finally {
            newWicketTester.destroy();
        }
    }
}
