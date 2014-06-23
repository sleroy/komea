/**
 *
 */

package org.komea.product.wicket;



import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.wicket.utils.IKomeaSecurityController;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.assertFalse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class LayoutPageTest
{
    
    
    public static class A extends LayoutPage
    {
        
        
        public A() {
        
        
            super(new PageParameters());
        }
        
        
        /**
         * @param _parameters
         */
        public A(final PageParameters _parameters) {
        
        
            super(_parameters);
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.product.wicket.LayoutPage#getSecurityController()
         */
        @Override
        protected IKomeaSecurityController getSecurityController() {
        
        
            final IKomeaSecurityController mock2 = mock(IKomeaSecurityController.class);
            when(mock2.isUserInRole("ADMIN")).thenReturn(true);
            return mock2;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.product.wicket.LayoutPage#obtainSecurityDetails()
         */
        @Override
        protected UserDetails obtainSecurityDetails() {
        
        
            return mock(UserDetails.class);
        }
    }



    @Rule
    public final TesterMethodRule wicketRule = new TesterMethodRule();
    
    
    
    @Test
    public void testLayoutWithAuth() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        newWicketTester.startPage(A.class);
        assertFalse(newWicketTester.hasNoErrorMessage().wasFailed());
        newWicketTester.getComponentFromLastRenderedPage("hellopanel:avatar");
        
        newWicketTester.destroy();
        
    }
    
    
    @Test
    public void testLayoutWithoutAuth() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        newWicketTester.startPage(HomePage.class);
        assertFalse(newWicketTester.hasNoErrorMessage().wasFailed());
        
        newWicketTester.destroy();
        
    }
}
