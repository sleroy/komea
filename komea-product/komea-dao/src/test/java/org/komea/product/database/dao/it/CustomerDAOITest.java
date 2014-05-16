
package org.komea.product.database.dao.it;


import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.CustomerCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class CustomerDAOITest extends AbstractSpringIntegrationTestCase {
    
    @Autowired
    private CustomerDao customerDAO;
    
    @Test
    @Transactional
    public void test() {
    
        final Customer record = new Customer();
        record.setName("");
        
        customerDAO.insert(record);
        
        Assert.assertEquals(1, customerDAO.selectByCriteria(new CustomerCriteria()).size());
        
    }
    
}
