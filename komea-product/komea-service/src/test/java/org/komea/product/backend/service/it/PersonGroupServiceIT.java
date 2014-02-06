
package org.komea.product.backend.service.it;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.IPersonGroupService;
import org.komea.product.database.model.PersonGroup;
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
public class PersonGroupServiceIT extends AbstractSpringIntegrationTestCase
{
    
    @Autowired
    private IPersonGroupService groupService;
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetAllDepartments() {
    
        List<PersonGroup> departments = groupService.getAllDepartments();
        Assert.assertEquals(1, departments.size());
    }
}
