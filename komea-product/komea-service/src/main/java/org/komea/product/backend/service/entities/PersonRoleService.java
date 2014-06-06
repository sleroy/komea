/**
 * 
 */

package org.komea.product.backend.service.entities;


import org.apache.commons.lang3.Validate;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 */
@Transactional
@Service
public class PersonRoleService extends AbstractService<PersonRole, Integer, PersonRoleCriteria> implements IPersonRoleService {
    
    private static final Logger LOGGER     = LoggerFactory.getLogger("personrole-service");
    
    private static final String ADMIN_NAME = "admin";
    
    private static final String ADMIN_ROLE = "ADMIN";
    
    @Autowired
    private PersonRoleDao       requiredDAO;
    
    /**
     * Returns the default user role
     * 
     * @return the default user role.
     */
    @Override
    public PersonRole getAdminRole() {
    
        PersonRole admin = selectByKey(ADMIN_ROLE);
        if (admin == null) {
            admin = new PersonRole();
            admin.setName(ADMIN_NAME);
            admin.setRoleKey(ADMIN_ROLE);
            insert(admin);
            return admin;
        }
        return admin;
    }
    
    /**
     * Returns the default user role
     * 
     * @return the default user role.
     */
    @Override
    public PersonRole getDefaultUserRole() {
    
        final PersonRole selectByKey = selectByKey("USER");
        Validate.notNull(selectByKey);
        return selectByKey;
    }
    
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
    
    @Override
    protected PersonRoleCriteria createKeyCriteria(final String key) {
    
        final PersonRoleCriteria criteria = new PersonRoleCriteria();
        criteria.createCriteria().andRoleKeyEqualTo(key);
        return criteria;
    }
    
}
