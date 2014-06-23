/**
 *
 */

package org.komea.product.wicket;



import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.wicket.utils.IKomeaSecurityController;
import org.springframework.security.core.userdetails.UserDetails;

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
    
    
        wicketRule.testStart(A.class);
    }
    
}
