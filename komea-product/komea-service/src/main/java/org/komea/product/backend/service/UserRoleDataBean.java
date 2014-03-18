
package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class UserRoleDataBean
{
    
    
    @Autowired
    private PersonRoleDao personRoleDao;
    
    
    
    /**
     * Method getPersonRoleDao.
     * 
     * @return PersonRoleDao
     */
    public PersonRoleDao getPersonRoleDao() {
    
    
        return personRoleDao;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        final PersonRole administrator = new PersonRole(null, "ADMIN", "Administrator");
        PersonRoleCriteria prCriteria = new PersonRoleCriteria();
        prCriteria.createCriteria().andNameEqualTo("Administrator");
        if (getPersonRoleDao().countByCriteria(prCriteria) == 0) {
            getPersonRoleDao().insert(administrator);
        }
        
        final PersonRole userRole = new PersonRole(null, "USER", "Standard user");
        prCriteria = new PersonRoleCriteria();
        prCriteria.createCriteria().andNameEqualTo("Standard user");
        if (getPersonRoleDao().countByCriteria(prCriteria) == 0) {
            getPersonRoleDao().insert(userRole);
        }
        
        
    }
    
    
    public void setPersonRoleDao(final PersonRoleDao _personRoleDao) {
    
    
        personRoleDao = _personRoleDao;
    }
    
    
}
