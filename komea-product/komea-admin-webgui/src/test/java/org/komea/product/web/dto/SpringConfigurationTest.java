/**
 * 
 */

package org.komea.product.web.dto;



import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;



/**
 * @author sleroy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
    locations =
        {
                "classpath:/spring/application-context.xml",
                    "classpath:/spring/rest-servlet.xml",
                    "classpath:/spring/dispatcher-servlet.xml" })
@TransactionConfiguration(
    defaultRollback = true)
public class SpringConfigurationTest
{
    
    
    /**
     * 
     */
    public SpringConfigurationTest() {
    
    
        // TODO Auto-generated constructor stub
    }
    
    
    @Ignore(
        value = "To move")
    @Test
    public void test() {
    
    
        //
        
    }
}
