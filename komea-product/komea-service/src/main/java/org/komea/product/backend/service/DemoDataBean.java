
package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.komea.product.backend.service.esper.IAlertPushService;
import org.komea.product.database.alert.AlertBuilder;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.model.Person;
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
    
    
    
    public DemoDataBean() {
    
    
        super();
    }
    
    
    @PostConstruct
    public void init() {
    
    
        final Person record =
                new Person(null, null, null, "Obiwan", "Kenobi", "obiwan@lightforce.net", "obiwan");
        personDAO.insert(record);
        
        final Person record2 =
                new Person(null, null, null, "Dark", "Maul", "darkmaul@darkforce.net", "dmaul");
        personDAO.insert(record2);
        
        final Person record3 =
                new Person(null, null, null, "Luke", "Skywalker", "lskywalker@lightforce.net",
                        "lskywalker");
        personDAO.insert(record3);
    }
    
    
    @Scheduled(fixedRate = 100)
    public void sendAlert() {
    
    
        for (int i = 0; i < 10; ++i) {
            alertPushService.sendEvent(AlertBuilder.newAlert().category("SYSTEM")
                    .criticity(Criticity.values()[i % Criticity.values().length])
                    .fullMessage("Message of alert").message("Message of alert").project("SYSTEM")
                    .provided("SYSTEM").type("DEMO_ALERT").getAlert());
        }
    }
    
}
