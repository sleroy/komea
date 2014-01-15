
package org.komea.product.web.admin.views;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        locations = {
                "classpath:/spring/*-context.xml",
                "file:///home/sleroy/git/komea/komea-product/komea-admin-webgui/src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
public class SpringConfigurationLoadingIT
{
    
    
    /**
     * Run the spring configuration files to detect eventual problems
     */
    @Test
    public void testLoading() {
    
    
    }
}
