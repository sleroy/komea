
package org.komea.product.backend.service.demodata;



import javax.annotation.PostConstruct;

import org.komea.product.backend.service.esper.IAlertPushService;
import org.komea.product.database.alert.AlertBuilder;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class DemoDataBean
{
    
    
    @Autowired
    private IAlertPushService alertPushService;
    
    
    @Autowired
    private PersonDao         personDAO;
    
    
    @Autowired
    private PersonRoleDao     personRoleDao;
    
    
    
    public DemoDataBean() {
    
    
        super();
    }
    
    
    public PersonRoleDao getPersonRoleDao() {
    
    
        return personRoleDao;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        personRoleDao.insert(new PersonRole(null, "Administrator"));
        final PersonRole userRole = new PersonRole(null, "Standard user");
        personRoleDao.insert(userRole);
        
        
        final Person record =
                new Person(null, null, null, "Obiwan", "Kenobi", "obiwan@lightforce.net", "obiwan");
        record.setIdPersonRole(userRole.getId());
        personDAO.insert(record);
        
        final Person record2 =
                new Person(null, null, null, "Dark", "Maul", "darkmaul@darkforce.net", "dmaul");
        record2.setIdPersonRole(userRole.getId());
        personDAO.insert(record2);
        
        final Person record3 =
                new Person(null, null, null, "Luke", "Skywalker", "lskywalker@lightforce.net",
                        "lskywalker");
        record3.setIdPersonRole(userRole.getId());
        personDAO.insert(record3);
        
        
    }
    
    
    @Scheduled(fixedRate = 10000)
    public void sendAlert() {
    
    
        for (int i = 0; i < 10; ++i) {
            alertPushService.sendEvent(AlertBuilder.newAlert().category("SYSTEM")
                    .criticity(Criticity.values()[i % Criticity.values().length])
                    .fullMessage("Message of alert").message("Message of alert").project("SYSTEM")
                    .provided("SYSTEM").type("DEMO_ALERT").build());
        }
    }
    
    
    public void setPersonRoleDao(final PersonRoleDao _personRoleDao) {
    
    
        personRoleDao = _personRoleDao;
    }
    
}
