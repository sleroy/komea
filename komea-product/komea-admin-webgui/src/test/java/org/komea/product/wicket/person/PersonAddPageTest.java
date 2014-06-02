/**
 * 
 */

package org.komea.product.wicket.person;


import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.auth.IPasswordEncoder;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.utils.WicketTesterMethodRule;

import static org.mockito.Mockito.mock;

/**
 * @author sleroy
 */
public class PersonAddPageTest {
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    @Before
    public void before() {
    
        wicketRule.getApplicationContextMock().putBean(mock(IPersonService.class));
        wicketRule.getApplicationContextMock().putBean(mock(IPersonRoleService.class));
        wicketRule.getApplicationContextMock().putBean(mock(IProjectService.class));
        wicketRule.getApplicationContextMock().putBean(mock(IPersonGroupService.class));
        wicketRule.getApplicationContextMock().putBean(mock(IPasswordEncoder.class));
        
    }
    
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
