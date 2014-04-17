/**
 * 
 */

package org.komea.product.backend.service.customer;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.CustomerDao;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest
{
    
    
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerDao     requiredDAO;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.customer.CustomerService#deleteCustomer(org.komea.product.database.model.Customer)}.
     */
    @Test
    public final void testDeleteCustomer() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
}
