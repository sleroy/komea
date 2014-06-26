
package org.komea.product.test.spring;



import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;



@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("/dbunit/database_complete.xml")
public class AbstractSpringDBunitIntegrationTest extends AbstractSpringIntegrationTestCase
{
    
    
    //

    @BeforeClass
    public static void beforeClass() {
    
    
        System.setProperty("komea.home", "target/komea-" + new DateTime().getMillis());
    }
}
