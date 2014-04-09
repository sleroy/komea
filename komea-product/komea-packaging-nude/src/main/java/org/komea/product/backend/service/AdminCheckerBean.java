
package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.komea.product.backend.auth.IPasswordEncoder;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 */
@Component
public class AdminCheckerBean
{
    
    
    private static final Logger  LOGGER = LoggerFactory.getLogger("admin-controller");
    
    @Autowired
    private CustomerDao          customerDao;
    
    @Autowired
    private IPasswordEncoder     encoder;
    
    @Autowired
    private IEventPushService    eventPushService;
    
    @Autowired
    private IEventTypeService    eventTypeDAO;
    
    @Autowired
    private IPersonService       personDAO;
    
    @Autowired
    private IPersonGroupService  personGroupDao;
    
    @Autowired
    private IPersonRoleService   personRoleDao;
    
    @Autowired
    private IProviderService     providerDao;
    
    @Autowired
    private ICronRegistryService registry;
    
    
    @Autowired
    private UserRoleDataBean     userRoleDataBean;
    
    
    
    /**
     * Method getIPersonGroupService.
     * 
     * @return IPersonGroupService
     */
    public IPersonGroupService getIPersonGroupService() {
    
    
        return personGroupDao;
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
            admin.setPassword(encoder.encodePassword("admin"));
            admin.setUserBdd(UserBdd.KOMEA);
            admin.setIdPersonRoleOrNull(personRoleDao.getAdminRole());
            personDAO.saveOrUpdate(admin);
            LOGGER.info("------- ALERT");
        }
        
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
    
    
}
