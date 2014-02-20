/**
 * 
 */

package org.komea.product.backend.service.entities;



import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
=======
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56



/**
 * @author sleroy
 */
<<<<<<< HEAD
=======
@Transactional
@Service
>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56
public class PersonRoleService extends AbstractService<PersonRole, Integer, PersonRoleCriteria>
        implements IPersonRoleService
{
    
    
    @Autowired
    private PersonRoleDao requiredDAO;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.genericservice.AbstractService#getRequiredDAO()
     */
    @Override
    public IGenericDAO<PersonRole, Integer, PersonRoleCriteria> getRequiredDAO() {
    
    
        return requiredDAO;
    }
    
    
    public void setRequiredDAO(final PersonRoleDao _requiredDAO) {
    
    
        requiredDAO = _requiredDAO;
    }
    
    
}
