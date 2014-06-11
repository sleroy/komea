
package org.komea.product.test.spring;



import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;



/**
 * This class defines the abstract class to perform integration tests on Spring
 * / Tomcat 7
 *
 * @author sleroy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath:/spring/application-context-test.xml",
        "classpath*:/spring/dispatcher-servlet-test.xml" })
@TransactionConfiguration(defaultRollback = true)
public abstract class AbstractSpringWebIntegrationTestCase
{
    
    
    @BeforeClass
    public static void beforeClass() {
    
    
        System.setProperty("komea.home", "target/komea-" + new DateTime().getMillis());
    }
    
    
    
    protected final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(getClass());
}
