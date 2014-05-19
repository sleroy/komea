/**
 * 
 */

package org.komea.product.backend.service.customer;



import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest
{
    
    
    /**
     * 
     */
    private static final int ID_CUSTOMER = 1;
    
    @InjectMocks
    private CustomerService  customerService;
    
    @Mock
    private IProjectService  projectService;
    
    @Mock
    private CustomerDao      requiredDAO;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.customer.CustomerService#deleteCustomer(org.komea.product.database.model.Customer)}.
     */
    @Test
    public final void testDeleteCustomer() throws Exception {
    
    
        final ArgumentCaptor<ProjectCriteria> projectCapture =
                ArgumentCaptor.forClass(ProjectCriteria.class);
        final Project project = new Project();
        project.setIdCustomer(ID_CUSTOMER);
        final List<Project> projectList = Collections.singletonList(project);
        when(projectService.selectByCriteria(projectCapture.capture())).thenReturn(projectList);
        
        
        // When I delete a customer
        final Customer customer = new Customer();
        customer.setId(ID_CUSTOMER);
        customerService.deleteCustomer(customer);
        
        // It updates the related projects with customerID = null
        assertNull(projectList.get(0).getIdCustomer());
        // And projects are saved
        verify(projectService, times(1)).saveOrUpdate(project);
        
    }
    
}
