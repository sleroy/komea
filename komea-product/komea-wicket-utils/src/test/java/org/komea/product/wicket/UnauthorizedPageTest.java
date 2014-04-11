
package org.komea.product.wicket;



import org.junit.Rule;
import org.junit.Test;



public class UnauthorizedPageTest
{
    
    
    @Rule
    public final TesterMethodRule wicketRule = new TesterMethodRule();
    
    
    
    @Test 
    public void testPage() throws Exception {
    
    
        wicketRule.testStart(UnauthorizedPage.class);
    }
    
    
}
