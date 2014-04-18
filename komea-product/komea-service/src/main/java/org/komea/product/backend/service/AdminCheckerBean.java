
package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.komea.product.backend.auth.IPasswordEncoder;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



/**
 */
@Component
@Transactional
public class AdminCheckerBean
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("admin-controller");
    
    
    @Value("#{authProperties.defaultAdminPassword}")
    private String              defaultAdminPassword;
    
    @Autowired
    private IPasswordEncoder    encoder;
    
    @Autowired
    private IPersonService      personDAO;
    
    @Autowired
    private IPersonGroupService personGroupDao;
    
    @Autowired
    private IPersonRoleService  personRoleDao;
    
    
    
    public IPasswordEncoder getEncoder() {
    
    
        return encoder;
    }
    
    
    /**
     * Method getIPersonGroupService.
     * 
     * @return IPersonGroupService
     */
    public IPersonGroupService getIPersonGroupService() {
    
    
        return personGroupDao;
    }
    
    
    public IPersonService getPersonDAO() {
    
    
        return personDAO;
    }
    
    
    public IPersonGroupService getPersonGroupDao() {
    
    
        return personGroupDao;
    }
    
    
    public IPersonRoleService getPersonRoleDao() {
    
    
        return personRoleDao;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        if (personDAO.getAdministrators().isEmpty()) {
            LOGGER.info("------- ALERT");
            LOGGER.info("------- No admin has been found, auto-generation of a default admin 'admin'");
            final Person admin = new Person();
            admin.setEmail("admin@admin");
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setLogin("admin");
            admin.setPassword(encoder.encodePassword(defaultAdminPassword));
            admin.setUserBdd(UserBdd.KOMEA);
            admin.setIdPersonRoleOrNull(personRoleDao.getAdminRole());
            personDAO.saveOrUpdate(admin);
            LOGGER.info("------- ALERT");
        }
        
    }
    
    
    public void setEncoder(final IPasswordEncoder _encoder) {
    
    
        encoder = _encoder;
    }
    
    
    /**
     * Method setIPersonGroupService.
     * 
     * @param _personGroupDao
     *            IPersonGroupService
     */
    public void setIPersonGroupService(final IPersonGroupService _personGroupDao) {
    
    
        personGroupDao = _personGroupDao;
    }
    
    
    public void setPersonDAO(final IPersonService _personDAO) {
    
    
        personDAO = _personDAO;
    }
    
    
    public void setPersonGroupDao(final IPersonGroupService _personGroupDao) {
    
    
        personGroupDao = _personGroupDao;
    }
    
    
    public void setPersonRoleDao(final IPersonRoleService _personRoleDao) {
    
    
        personRoleDao = _personRoleDao;
    }
    
    
}
