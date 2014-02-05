
package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class DemoDataBean
{
    
    
    @Autowired
    private PersonDao personDAO;
    
    
    
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
    
}
