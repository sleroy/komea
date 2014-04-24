
package org.komea.product.backend.service.customer;



import java.util.List;

import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.CustomerCriteria;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 */
@Service
@Transactional
public class CustomerService extends AbstractService<Customer, Integer, CustomerCriteria> implements
        ICustomerService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    
    @Autowired
    private IProjectService     projectService;
    
    @Autowired
    private CustomerDao         requiredDAO;
    
    
    
    /**
     *
     */
    public CustomerService() {
    
    
        super();
    }
    
    
    @Override
    public void deleteCustomer(final Customer _customer) {
    
    
        final ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.createCriteria().andIdCustomerEqualTo(_customer.getId());
        final List<Project> projects = projectService.selectByCriteria(projectCriteria);
        for (final Project project : projects) {
            project.setIdCustomer(null);
            projectService.saveOrUpdate(project);
        }
        delete(_customer);
    }
    
    
    public IProjectService getProjectService() {
    
    
        return projectService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.genericservice.AbstractService#getRequiredDAO()
     */
    @Override
    public IGenericDAO<Customer, Integer, CustomerCriteria> getRequiredDAO() {
    
    
        return requiredDAO;
    }
    
    
    public void setProjectService(final IProjectService _projectService) {
    
    
        projectService = _projectService;
    }
    
    
    public void setRequiredDAO(final CustomerDao _requiredDAO) {
    
    
        requiredDAO = _requiredDAO;
    }
    
    
    @Override
    protected CustomerCriteria createKeyCriteria(final String key) {
    
    
        final CustomerCriteria criteria = new CustomerCriteria();
        criteria.createCriteria().andNameEqualTo(key);
        return criteria;
    }
    
}
