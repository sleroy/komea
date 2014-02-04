
package org.komea.product.backend.service.it;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.IPersonService;
import org.komea.product.database.model.Person;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class PersonServiceTest extends AbstractSpringIntegrationTestCase
{
    
    @Autowired
    private IPersonService personService;
    
    //
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetPersonList() {
    
        List<Person> personList = personService.getPersonList();
        Assert.assertEquals(2, personList.size());
    }
}
