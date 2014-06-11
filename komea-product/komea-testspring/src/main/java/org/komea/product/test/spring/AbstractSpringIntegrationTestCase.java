
package org.komea.product.test.spring;



import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;



/**
 * This class defines the abstract class to perform integration tests on Spring
 * / Tomcat 7
 *
 * @author sleroy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
"classpath:/spring/application-context-test.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public abstract class AbstractSpringIntegrationTestCase
{


    protected static final Logger LOGGER =
            LoggerFactory
            .getLogger(AbstractSpringIntegrationTestCase.class);



    @BeforeClass
    public static void beforeClass() {


        System.setProperty("komea.home", "target/komea-" + new DateTime().getMillis());
    }
}
