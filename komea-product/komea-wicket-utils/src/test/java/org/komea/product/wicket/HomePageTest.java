
package org.komea.product.wicket;



import org.junit.Rule;
import org.junit.Test;



public class HomePageTest
{
    
    
    @Rule
    public final TesterMethodRule wicketRule = new TesterMethodRule();
    
    
    
    @Test 
    public void testHomePage() throws Exception {
    
    
        wicketRule.testStart(HomePage.class);
    }
    
}
