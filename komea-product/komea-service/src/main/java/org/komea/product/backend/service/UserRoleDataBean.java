
package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.database.model.PersonRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class UserRoleDataBean
{
    
    
    @Autowired
    private IPersonRoleService personRoleDao;
    
    
    
    /**
     * Method getPersonRoleDao.
     * 
     * @return PersonRoleDao
     */
    public IPersonRoleService getPersonRoleDao() {
    
    
        return personRoleDao;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        final PersonRole administrator = new PersonRole(null, "ADMIN", "Administrator");
        if (getPersonRoleDao().selectByKey("ADMIN") == null) {
            getPersonRoleDao().insert(administrator);
        }
        
        final PersonRole userRole = new PersonRole(null, "USER", "Standard user");
        if (getPersonRoleDao().selectByKey("USER") == null) {
            getPersonRoleDao().insert(userRole);
        }
        
        
    }
    
    
    public void setPersonRoleDao(final IPersonRoleService _personRoleDao) {
    
    
        personRoleDao = _personRoleDao;
    }
    
    
}
