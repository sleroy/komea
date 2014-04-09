
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 */
@Component
public class DemoDataBean
{
    
    
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
    
    
        if (personDAO.selectAll().isEmpty()) {
            
            final Person admin =
                    new Person(null, null, null, "admin", "admin", "admin@admin", "admin",
                            encoder.encodePassword("admin"), UserBdd.KOMEA);
            admin.setIdPersonRoleOrNull(personRoleDao.getAdminRole());
            personDAO.saveOrUpdate(admin);
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
